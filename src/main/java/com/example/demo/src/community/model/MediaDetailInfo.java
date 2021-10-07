package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MediaDetailInfo {
    private int articleMediaIdx;        //ArticleMedia
    private String mediaUrl;        //ArticleMedia
    List<GetProductTagInfo> getProductTagInfoList;  //Product Tag
    private String contents;        //ArticleMedia
    private String hashTag;         //ArticleMedia
    private int numHeart;       //ArticleMedia
    private int numScrap;       //ArticleMedia
    private int numComment;     //ArticleMedia

    private boolean isHeart;        //따로 계산해야 하는 것
    private boolean isScrap;        //따로 계산해야 하는 것
}
