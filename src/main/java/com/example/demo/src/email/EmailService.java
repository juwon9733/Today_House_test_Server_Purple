package com.example.demo.src.email;

import com.example.demo.src.email.model.EmailAuthRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final JavaMailSender emailSender;
    @Autowired
    private final EmailDao emailDao;
    @Autowired
    private final EmailProvider emailProvider;


    private MimeMessage createMessage(String to, String code)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // 수신자
        message.setSubject("[오늘의 집] 인증코드 안내"); // 메일 제목

        String msg="";
        msg += "<div style=\"max-width: 540px; border-bottom: 1px solid #eaedef; margin: 0 22px; margin-top: 60px;\">\n" +
                "  <div style=\"padding-bottom: 40px; max-width: 473px;font-size: 16px; margin-top: 60px;\">\n" +
                "  <img src=\"https://image.ohou.se/i/bucketplace-v2-development/uploads/cards/162642310342712182.png\" style=\"width: 74px;\" alt=\"오늘의집\">\n" +
                "  <div style=\"font-size: 18px; font-weight: 700; margin-bottom: 10px; margin-top: 60px;\">\n" +
                "  인증코드를 확인해주세요.\n" +
                "  </div>\n" +
                "  <span style=\"\n" +
                "  font-size: 32px; \n" +
                "  line-height: 42px; \n" +
                "  font-weight: 700; \n" +
                "  display: block; \n" +
                "  margin-top: 6px;\">";
        msg += code;
        msg += "</span>\n" +
                "  <div style=\"margin-top: 60px; margin-bottom: 40px; line-height: 28px;\">\n" +
                "  <div style=\"display: inline-block;\">이메일 인증 절차에 따라 이메일 인증코드를 </div> \n" +
                "  <div style=\"display: inline-block;\"> 발급해드립니다.</div> \n" +
                "  <div style=\"display: inline-block;\">인증코드는 이메일 발송</div>\n" +
                "  <div style=\"display: inline-block;\">시점으로부터 3분동안 유효합니다.</div>\n" +
                "  </div>\n" +
                "\n" +
                "  </div>\n" +
                "</div>";
        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress("purple0249@gmil.com","Today-House-Rising-Test")); // 보내는 사람
        return message;
    }
    // 인증코드 만들기
    public static String createCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }
    public EmailAuthRes sendMessage(String to)throws Exception {
        String code = createCode();
        EmailAuthRes emailAuthRes = new EmailAuthRes(emailDao.postCode(code));
        MimeMessage message = createMessage(to, code);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return emailAuthRes;
    }


}