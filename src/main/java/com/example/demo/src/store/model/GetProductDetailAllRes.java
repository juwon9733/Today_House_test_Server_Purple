package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class GetProductDetailAllRes {
    private int productArticleIdx;
    private String category;
    private List<String> photo;
    private String company;
    private String title;
    private double rating;
    private int numReview;
    private int salePercent;
    private int price;
    private List<String> shipping;
    private boolean isScrap;
}
