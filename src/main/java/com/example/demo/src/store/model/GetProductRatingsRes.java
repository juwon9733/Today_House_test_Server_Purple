package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetProductRatingsRes {
    private int productArticleIdx;
    private int reviewNumber;
    private double avgRating;
    private int one;
    private int two;
    private int three;
    private int four;
    private int five;
}
