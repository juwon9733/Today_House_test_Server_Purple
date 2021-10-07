package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PatchUserProfileReqTemp {
    private int userIdx;
    private String backImage;
    private String profileImage;
    private String nickName;
    private String myUrl;
    private String introduction;
}
