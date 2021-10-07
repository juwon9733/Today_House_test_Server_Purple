package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class PostArticleKindReq {
    private Integer userIdx;
    private String size;        //can null
    private String style;        //can null
    private String housingType;        //can null
    private String kindsOfArticle;
}
