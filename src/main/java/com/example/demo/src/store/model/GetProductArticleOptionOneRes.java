package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductArticleOptionOneRes {
    private int productArticleIdx;
    private int optionOneIdx;
    private String optionOneName;
    private String optionOneChoose;
    private int optionOnePrice;
}
