package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductArticleDetailInfoRes {
    private int productArticleIdx;
    private String infoPhotoUrl;
}
