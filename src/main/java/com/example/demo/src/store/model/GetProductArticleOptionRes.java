package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductArticleOptionRes {
    private List<GetProductArticleOptionOneRes> getProductArticleOptionOneRes;
    private List<GetProductArticleOptionTwoRes> getProductArticleOptionTwoRes;
    private List<GetProductArticleOptionExtraRes> getProductArticleOptionExtraRes;
}
