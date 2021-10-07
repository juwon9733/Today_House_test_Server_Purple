package com.example.demo.src.community.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetArticleAll {
    private int articleIdx;     // Article
    private int userIdx;        // Article<게시한 사람임>

    private String mediaUrl;       // ArticleInMedia
    private String contents;        // ArticleInMedia
    private String spaceType;       // ArticleInMedia

    private String size;        // Article (can null)
    private String style;       // Article (can null)
    private String housingType;     // Article (can null)
    private String kindsOfArticle;      // Article
    private int numHeart;       // Article
    private int numScrap;       // Article
    private int numComment;     // Article
    private boolean isScrap;        // 따로 계산

}
