package com.example.demo.src.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.src.S3.model.S3Component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class S3ServiceImpl implements  S3Service{

    private final AmazonS3Client amazonS3Client;
    private final S3Component s3Component;

    @Override
    public void uploadFile(String fileName, InputStream inputStream, ObjectMetadata objectMetadata) {
        amazonS3Client.putObject(
                new PutObjectRequest(s3Component.getBucket(), fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                // withCannedAcl 부분 : 외부에 공개할 이미지이므로, 해당 파일에 read권한을 추가한다.
        );
    }
    @Override
    public String getFileUrl(String fileName) {
        return String.valueOf(amazonS3Client.getUrl(s3Component.getBucket(), fileName));
        // 해당하는 bucket에서, 해당하는 파일의 url을 가져온다.
    }

    /**
     * 유저 프로필, 유저 배경 프로필 관련
     */
    @Override
    public void uploadUserProfileFile(String fileName, InputStream inputStream, ObjectMetadata objectMetadata) {
        amazonS3Client.putObject(
                new PutObjectRequest(s3Component.getBucket()+"/image/유저 프로필 이미지", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }
    @Override
    public String getUserProfileFile(String fileName) {
        return String.valueOf(amazonS3Client.getUrl(s3Component.getBucket()+"/image/유저 프로필 이미지", fileName));
    }
    @Override
    public void uploadUserBackProfileFile(String fileName, InputStream inputStream, ObjectMetadata objectMetadata) {
        amazonS3Client.putObject(
                new PutObjectRequest(s3Component.getBucket()+"/image/유저 프로필 배경 이미지", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }

    @Override
    public String getUserBackProfileFile(String fileName) {
        return String.valueOf(amazonS3Client.getUrl(s3Component.getBucket()+"/image/유저 프로필 배경 이미지", fileName));
    }

}
