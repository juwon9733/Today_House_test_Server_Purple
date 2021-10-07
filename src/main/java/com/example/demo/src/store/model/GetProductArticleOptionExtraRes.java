package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductArticleOptionExtraRes {
    private int productArticleIdx;
    private int optionExtraIdx;
    private String optionExtraName;
    private String optionExtraChoose;
    private int optionExtraPrice;
}
