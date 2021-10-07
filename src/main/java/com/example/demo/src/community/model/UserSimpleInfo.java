package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserSimpleInfo {
    private int userIdx;
    private String userProfileImage;
    private String userNickName;
}
