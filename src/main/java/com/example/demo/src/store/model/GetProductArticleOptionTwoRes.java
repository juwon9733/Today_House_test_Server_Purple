package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductArticleOptionTwoRes {
    private int productArticleIdx;
    private int optionTwoIdx;
    private String optionTwoName;
    private String optionTwoChoose;
    private int optionTwoPrice;
}
