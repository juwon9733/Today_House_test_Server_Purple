package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetArticle {
    private int articleIdx;
    private int userIdx;
    private String size;
    private String style;
    private String housingType;
    private String kindsOfArticle;
    private int numHeart;
    private int numScrap;
    private int numComment;
    private int numShare;
    private String createdAt;
    private String updatedAt;
    private String status;
}
