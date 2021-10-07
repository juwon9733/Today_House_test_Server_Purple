package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetShopBasketOption {
    private int productArticleIdx;
    private int optionOneIdx;
    private int optionTwoIdx;
    private int productNum;
    private int optionExtraIdx;
    private int extraNum;
}
