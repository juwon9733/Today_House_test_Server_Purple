package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetArticleMediaInfo {
    private int articleMediaIdx;
    private int articleIdx;
    private String mediaUrl;
    private String contents;
    private String spaceType;
    private String hashTag;
    private int numHeart;
    private int numScrap;
    private int numComment;

}
