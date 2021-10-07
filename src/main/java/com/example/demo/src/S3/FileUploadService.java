package com.example.demo.src.S3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class FileUploadService {

    private final S3Service s3Service;

    public String uploadImage(MultipartFile file) {
        // getOriginalFilename : 업로드할 파일의 실제이름을 반환해준다고 생각하면 된다.
        String before_fileName = file.getOriginalFilename();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ fileName is :" + before_fileName);
        // createFileName : 파일 이름을 유니크하게 바꿔주는 메소드
        String fileName = createFileName(file.getOriginalFilename());
        System.out.println("######################### fileName is :" + fileName);

        // ObjectMetadata Amazon S3에 저장되는, 오브젝트 메타데이터를 표현한다.
        // setContentType : 파일의 컨텐츠 타입을 정한다.
        // getContentType : 파일의 컨텐츠 타입을 반환한다.
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            // getInputStream : 파일의 내용을 읽을 수 있는 inputStream을 반환한다.
            s3Service.uploadFile(fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename()));
        }
        // 최종적으로 파일의 Url을 리턴한다.
        return s3Service.getFileUrl(fileName);
    }
    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
        // 파일 이름을 유니크하게 바꿔준다.
        // getFileExtension : 파일의 확장자는 그대로 보존해주기 위해, 있는 함수이다..
    }
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }

    /**
     * 유저 프로필, 유저 배경 프로필 관련
     */
    public String uploadUserProfileImage(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            s3Service.uploadUserProfileFile(fileName, inputStream, objectMetadata);     // 변경이 필요한 부분
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename()));
        }
        return s3Service.getUserProfileFile(fileName);
    }
    public String uploadUserBackProfileImage(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            s3Service.uploadUserBackProfileFile(fileName, inputStream, objectMetadata);     // 변경이 필요한 부분
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename()));
        }
        return s3Service.getUserBackProfileFile(fileName);
    }
}
