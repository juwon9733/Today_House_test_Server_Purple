package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewInfo {
    private int reviewIdx;
    private int userIdx;
    private int productArticleIdx;
    private String photo;
    private int rating;
    private String contents;
    private int thisReviewHelp;
    private String createdAt;
}
