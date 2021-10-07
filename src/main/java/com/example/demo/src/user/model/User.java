package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String email;
    private String passwd;
    private String nickName;
    private String profileImage;
    private String myUrl;
    private String backImage;
    private String introduction;
    private String createdAt;
    private String updatedAt;
    private String status;
}
