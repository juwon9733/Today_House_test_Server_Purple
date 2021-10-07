package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostBuyProductReq {
    private Integer userIdx;
    private Integer productArticleIdx;
    private Integer optionOneIdx;
    private Integer optionTwoIdx;   // can null
    private Integer productNum;
    private Integer optionExtraIdx;   // can null
    private Integer extraNum;   // can null

    private Integer buyProductIdx;
    private String orderName;
    private String orderEmail;
    private String orderPhone;
    private String deliverName;
    private String deliverPhone;
    private String deliveredAddress;
}
