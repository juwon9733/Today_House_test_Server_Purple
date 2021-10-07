package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductTagInfo {
    private int productTagIdx;        //ProductTag
    private int productArticleIdx;      //ProductTag

    private String productArticleImage;     //ProductArticle
    private String company;                 //ProductArticle
    private String title;                   //ProductArticle
    private int salePercent;             //ProductArticle
    private int price;                   //ProductArticle
    private double reviewRating;             //ProductArticle
    private int numOfReview;             //ProductArticle

    private int xAxis;      //ProductTag
    private int yAxis;      //ProductTag

}
