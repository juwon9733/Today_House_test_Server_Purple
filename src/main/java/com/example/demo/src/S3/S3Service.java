package com.example.demo.src.S3;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface S3Service {

    void uploadFile(
            String fileName,
            InputStream inputStream,
            ObjectMetadata objectMetadata
    );
    String getFileUrl(String fileName);

    void uploadUserProfileFile(
            String fileName,
            InputStream inputStream,
            ObjectMetadata objectMetadata
    );
    String getUserProfileFile(String fileName);
    void uploadUserBackProfileFile(
            String fileName,
            InputStream inputStream,
            ObjectMetadata objectMetadata
    );
    String getUserBackProfileFile(String fileName);

}
