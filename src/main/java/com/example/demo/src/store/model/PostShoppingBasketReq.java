package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostShoppingBasketReq {
    private Integer userIdx;
    private Integer productArticleIdx;
    private Integer optionOneIdx;
    private Integer optionTwoIdx;       // can null
    private Integer productNum;
    private Integer optionExtraIdx;     // can null
    private Integer extraNum;       // can null
}
