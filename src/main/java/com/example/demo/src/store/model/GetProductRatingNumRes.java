package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRatingNumRes {
    private int productArticleIdx;
    private int one;
    private int two;
    private int three;
    private int four;
    private int five;
}
