package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewAllRes {
    private int reviewIdx;
    private String reviewerImage;
    private String reviewerNickName;
    private int rating;
    private String createdAt;
    private String productTitle;
    private String reviewPhoto;
    private String reviewContent;
    private int canHelpNumber;
    private boolean isUserClickHelp;


}
