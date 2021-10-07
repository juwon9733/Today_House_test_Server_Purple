package com.example.demo.src.S3;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileUploadService fileUploadService;


    @PostMapping("/api/v1/upload")
    public String uploadImage(@RequestBody MultipartFile file) {
        // multipart : 요청 시 form-data 요청을 받을 수 있도록 한다.
        return fileUploadService.uploadImage(file);
    }
    /*
    @PostMapping("/api/v1/upload")
    public String uploadImage(@RequestParam MultipartFile file) {
        // multipart : 요청 시 form-data 요청을 받을 수 있도록 한다.
        return fileUploadService.uploadImage(file);
    }

     */
}
