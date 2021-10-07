package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserProfileReq {
    private Integer userIdx;
    private String backImage;        // can null
    private String profileImage;     // can null
    private String nickName;                // can null
    private String myUrl;                   // can null
    private String introduction;            // can null
}
