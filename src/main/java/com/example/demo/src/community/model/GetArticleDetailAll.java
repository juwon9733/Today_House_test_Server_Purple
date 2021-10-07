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
public class GetArticleDetailAll {
    private int userIdx;            // Article (게시글을 게신한 사람의 유저 인덱스이다.)
    private String userProfileImage;        // User
    private String userNickName;        // User

    private String articleCreatedAt;        //Article
    private String size;        //Article
    private String style;       //Article
    private String housingType;     //Article
    private String kindsOfArticle;     //Article
    // 여기까지가 상단.

    private List<MediaDetailInfo> mediaDetailInfoList;      // [40] 이용
    private int numHeart;     //Article
    private int numScrap;     //Article
    private int numComment;     //Article
    private int numShare;     //Article

    private boolean isHeart;        // 따로 계싼
    private boolean isScrap;        // 따로 계싼


}
