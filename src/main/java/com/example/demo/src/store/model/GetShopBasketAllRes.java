package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetShopBasketAllRes {
    private int basketIdx;
    private int userIdx;
    private int productArticleIdx;
    private String photo;
    private String company;
    private String title;
    private String shipping;
    private String optionOneName;
    private String optionOneChoose;
    private String optionTwoName;
    private String optionTwoChoose;
    private int numberOfProduct;
    private int productPrice;
    private String optionExtraName;
    private String optionExtraChoose;
    private int numberOfExtraProduct;
    private int extraProductPrice;
}
