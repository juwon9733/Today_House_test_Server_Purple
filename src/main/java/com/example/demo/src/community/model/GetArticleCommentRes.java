package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetArticleCommentRes {

    private UserSimpleInfo userSimpleInfo;      //User
    private String comment;     // Comment
    private String updatedAt;       //Comment
    private int numHeart;        //Comment

    private boolean isHeart;        //따로 계산
}
