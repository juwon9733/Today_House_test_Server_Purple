package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostScrapReq {
    private Integer userIdx;
    private Integer articleIdx;         // can null
    private Integer articleMediaIdx;       // can null
    private Integer productArticleIdx;      // can null
}
