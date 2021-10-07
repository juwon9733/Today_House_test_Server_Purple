package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetProductArticleTodayDealAllRes {
    private int productArticleIdx;
    private String category;
    private String photo;
    private String company;
    private String title;
    private int salePercent;
    private int price;
    private double reviewRating;
    private int numOfReview;
    private String enrolledTimeInTodayDeal;
    private boolean isScrap;
}
