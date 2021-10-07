package com.example.demo.src.email;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.community.model.GetArticleAll;
import com.example.demo.src.email.model.EmailAuthRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auths")
public class EmailController {

    @Autowired
    private final EmailService emailService;
    private final EmailProvider emailProvider;

    @GetMapping("/email") // 이메일 인증 코드 보내기
    public BaseResponse<EmailAuthRes> emailAuth(@RequestParam Map<String, String> email) throws Exception {
        if (email == null) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        EmailAuthRes emailAuthRes = emailService.sendMessage(email.get("email"));
        return new BaseResponse<>(emailAuthRes);
    }

    @GetMapping("/verify-code") // 이메일 인증 코드 검증
    public BaseResponse<Boolean> verifyCode(
            @RequestParam(required = false) Integer authCodeIdx,
            @RequestParam(required = false) String code) {
        try {
            if(authCodeIdx == null) {
                return new BaseResponse<>(EMPTY_AUTH_CODE_IDX);
            }
            if (code == null) {
                return new BaseResponse<>(EMPTY_CODE);
            }
            if (emailProvider.checkCodeByAuthIdx(authCodeIdx, code) ==  true) {
                return new BaseResponse<>(true);
            } else {
                return new BaseResponse<>(false);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }
}