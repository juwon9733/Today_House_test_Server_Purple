package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostArticleMediaReq {
    private Integer articleIdx;
    private String mediaUrl;
    private String contents;        // can null;
    private String spaceType;
    private String hashTag;         // can null;
}
