package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserProductScrapAllRes {
    private int productArticleIdx;
    private String category;
    private String photo;
    private String company;
    private String title;
    private int sale;
    private int price;
    private double rating;
    private int reviewNum;
    private boolean isScrap;
    private int scrapIdx;
}
