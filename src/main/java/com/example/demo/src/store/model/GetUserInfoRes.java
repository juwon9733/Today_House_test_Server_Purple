package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserInfoRes {
    private int Idx;
    private String email;
    private String passwd;
    private String nickName;
    private String profileImage;
    private String backImage;
    private String introduction;
    private String createdAt;
    private String updatedAt;
    private String status;
}
