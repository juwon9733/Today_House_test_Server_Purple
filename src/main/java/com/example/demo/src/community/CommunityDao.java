package com.example.demo.src.community;

import com.example.demo.src.community.model.*;
import com.example.demo.src.store.model.DeleteProductScrapReq;
import com.example.demo.src.store.model.GetStoreSearchHistoryRes;
import com.example.demo.src.store.model.PostSeeProductReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommunityDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetArticleIdxScraped> getArticleIdxScraped(int userIdx) {
        String getArticleIdxScrapedQuery = "select Idx, articleIdx\n" +
                "from Scrap\n" +
                "where userIdx = ?;";
        int getArticleIdxScrapedParams = userIdx;
        return this.jdbcTemplate.query(getArticleIdxScrapedQuery,
                (rs, rowNum) -> new GetArticleIdxScraped(
                        rs.getInt("Idx"),
                        rs.getInt("articleIdx")
                ),
                getArticleIdxScrapedParams
        );
    }
    public List<GetArticleIdxHearted> getArticleIdxHearted(int userIdx) {
        String getArticleIdxHeartedQuery = "select Idx, articleIdx\n" +
                "from Heart\n" +
                "where userIdx = ?;";
        int getArticleIdxHeartedParams = userIdx;
        return this.jdbcTemplate.query(getArticleIdxHeartedQuery,
                (rs, rowNum) -> new GetArticleIdxHearted(
                        rs.getInt("Idx"),
                        rs.getInt("articleIdx")
                ),
                getArticleIdxHeartedParams
        );
    }

    public List<GetAllArticleIdx> getAllArticleIdx() {
        String getAllArticleIdxQuery = "select Idx\n" +
                "from Article\n" +
                "where status = 'Y'\n" +
                "order by createdAt desc;";
        int getAllArticleIdxParams;
        return this.jdbcTemplate.query(getAllArticleIdxQuery,
                (rs, rowNum) -> new GetAllArticleIdx(
                        rs.getInt("Idx")
                )
        );
    }
    public GetArticle getArticle(int articleIdx) {
        String getArticleQuery = "select Idx,\n" +
                "       userIdx,\n" +
                "       size,\n" +
                "       style,\n" +
                "       housingType,\n" +
                "       kindsOfArticle,\n" +
                "       numHeart,\n" +
                "       numScrap,\n" +
                "       numComment,\n" +
                "       numShare,\n" +
                "       createdAt,\n" +
                "       updatedAt,\n" +
                "       status\n" +
                "from Article\n" +
                "where Idx = ?\n" +
                "  and status = 'Y';";
        int getArticleParams = articleIdx;
        return this.jdbcTemplate.queryForObject(getArticleQuery,
                (rs, rowNum) -> new GetArticle(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("size"),
                        rs.getString("style"),
                        rs.getString("housingType"),
                        rs.getString("kindsOfArticle"),
                        rs.getInt("numHeart"),
                        rs.getInt("numScrap"),
                        rs.getInt("numComment"),
                        rs.getInt("numShare"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("status")
                ),
                getArticleParams
        );
    }
    public List<GetArticleInMedia> getArticleInMedia(int articleIdx) {
        String getArticleInMediaQuery = "select Idx,\n" +
                "       articleIdx,\n" +
                "       mediaUrl,\n" +
                "       contents,\n" +
                "       spaceType,\n" +
                "       hashTag,\n" +
                "       createdAt,\n" +
                "       updatedAt,\n" +
                "       status\n" +
                "from ArticleInMedia\n" +
                "where articleIdx = ?\n" +
                "  and status = 'Y'\n" +
                "order by Idx asc;";
        int getArticleInMediaParams = articleIdx;
        return this.jdbcTemplate.query(getArticleInMediaQuery,
                (rs, rowNum) -> new GetArticleInMedia(
                        rs.getInt("Idx"),
                        rs.getInt("articleIdx"),
                        rs.getString("mediaUrl"),
                        rs.getString("contents"),
                        rs.getString("spaceType"),
                        rs.getString("hashTag"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("status")
                ),
                getArticleInMediaParams
        );
    }
    public UserSimpleInfo getUserSimpleInfo(int userIdx) {
        String getUserSimpleInfoQuery = "select Idx, profileImage, nickName\n" +
                "from User\n" +
                "where Idx = ?;";
        int getUserSimpleInfoParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserSimpleInfoQuery,
                (rs, rowNum) -> new UserSimpleInfo(
                        rs.getInt("Idx"),
                        rs.getString("profileImage"),
                        rs.getString("nickName")
                ),
                getUserSimpleInfoParams
        );
    }
    public GetArticleMediaInfo getArticleMediaInfo(int articleMediaIdx) {
        String getArticleMediaInfoQuery = "select Idx,\n" +
                "       articleIdx,\n" +
                "       mediaUrl,\n" +
                "       contents,\n" +
                "       spaceType,\n" +
                "       hashTag,\n" +
                "       numHeart,\n" +
                "       numScrap,\n" +
                "       numComment\n" +
                "from ArticleInMedia\n" +
                "where Idx = ?;";
        int getArticleMediaInfoParams = articleMediaIdx;
        return this.jdbcTemplate.queryForObject(getArticleMediaInfoQuery,
                (rs, rowNum) -> new GetArticleMediaInfo(
                        rs.getInt("Idx"),
                        rs.getInt("articleIdx"),
                        rs.getString("mediaUrl"),
                        rs.getString("contents"),
                        rs.getString("spaceType"),
                        rs.getString("hashTag"),
                        rs.getInt("numHeart"),
                        rs.getInt("numScrap"),
                        rs.getInt("numComment")
                ),
                getArticleMediaInfoParams
        );
    }
    public List<GetProductTagInfo> getProductTagInfo(int articleMediaIdx) {
        String getProductTagInfoQuery = "select Idx,\n" +
                "       productArticleIdx,\n" +
                "       photo,\n" +
                "       company,\n" +
                "       title,\n" +
                "       salePercent,\n" +
                "       price,\n" +
                "       reviewRating,\n" +
                "       numOfReview,\n" +
                "       xAxis,\n" +
                "       yAxis\n" +
                "from ProductTag\n" +
                "         inner join(select min(Idx) as forPhoto, productArticleIdx as photoProductIdx, photo\n" +
                "                    from ProductArticlePhoto\n" +
                "                    group by productArticleIdx) ProductPhoto\n" +
                "                   on ProductTag.productArticleIdx = ProductPhoto.photoProductIdx\n" +
                "         inner join(select Idx as productIdx, company, title, salePercent, price, reviewRating, numOfReview\n" +
                "                    from ProductArticle) ProductArticleForPhoto\n" +
                "                   on ProductTag.productArticleIdx = ProductArticleForPhoto.productIdx\n" +
                "where articleMediaIdx = ?;";
        int getProductTagInfoParams = articleMediaIdx;
        return this.jdbcTemplate.query(getProductTagInfoQuery,
                (rs, rowNum) -> new GetProductTagInfo(
                        rs.getInt("Idx"),
                        rs.getInt("productArticleIdx"),
                        rs.getString("photo"),
                        rs.getString("company"),
                        rs.getString("title"),
                        rs.getInt("salePercent"),
                        rs.getInt("price"),
                        rs.getDouble("reviewRating"),
                        rs.getInt("numOfReview"),
                        rs.getInt("xAxis"),
                        rs.getInt("yAxis")
                ),
                getProductTagInfoParams
        );
    }
    public List<GetArticleMediaIdx> getArticleMediaIdx(int articleIdx) {
        String getArticleMediaIdxQuery = "select Idx\n" +
                "from ArticleInMedia\n" +
                "where articleIdx = ?;";
        int getArticleMediaIdxParams = articleIdx;
        return this.jdbcTemplate.query(getArticleMediaIdxQuery,
                (rs, rowNum) -> new GetArticleMediaIdx(
                        rs.getInt("Idx")
                ),
                getArticleMediaIdxParams
        );
    }
    public int postArticleKind(PostArticleKindReq postArticleKindReq) {
        String postArticleKindQuery = "insert into Article (userIdx, size, style, housingType, kindsOfArticle) values(?,?,?,?,?);";
        Object[] postArticleKindParams = new Object[]{postArticleKindReq.getUserIdx(), postArticleKindReq.getSize(),
                postArticleKindReq.getStyle(), postArticleKindReq.getSize(), postArticleKindReq.getKindsOfArticle()};
        this.jdbcTemplate.update(postArticleKindQuery, postArticleKindParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public int postArticleMedia(PostArticleMediaReq postArticleMediaReq) {
        String postArticleMediaQuery = "insert into ArticleInMedia (articleIdx, mediaUrl, contents, spaceType, hashTag) values(?,?,?,?,?);";
        Object[] postArticleMediaParams = new Object[]{postArticleMediaReq.getArticleIdx(), postArticleMediaReq.getMediaUrl(),
                postArticleMediaReq.getContents(), postArticleMediaReq.getSpaceType(), postArticleMediaReq.getHashTag()};
        this.jdbcTemplate.update(postArticleMediaQuery, postArticleMediaParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public int postArticleProductTag(PostArticleProductTagReq postArticleProductTagReq) {
        String PostArticleProductTagResQuery = "insert into ProductTag (articleMediaIdx, productArticleIdx, xAxis, yAxis) values(?,?,?,?);";
        Object[] PostArticleProductTagResParams = new Object[]{postArticleProductTagReq.getArticleMediaIdx(), postArticleProductTagReq.getProductArticleIdx(),
                postArticleProductTagReq.getXAxis(), postArticleProductTagReq.getYAxis()};
        this.jdbcTemplate.update(PostArticleProductTagResQuery, PostArticleProductTagResParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public List<GetCommentIdx> getCommentIdx(int articleIdx) {
        String getCommentIdxQuery = "select Idx\n" +
                "from Comment\n" +
                "where articleIdx = ?;";
        int getCommentIdxParams = articleIdx;
        return this.jdbcTemplate.query(getCommentIdxQuery,
                (rs, rowNum) -> new GetCommentIdx(
                        rs.getInt("Idx")
                ),
                getCommentIdxParams
        );
    }
    public GetCommentContents getCommentContents(int commentIdx) {
        String getCommentContentsQuery = "select Idx,\n" +
                "       userIdx,\n" +
                "       articleIdx,\n" +
                "       articleMediaIdx,\n" +
                "       comment,\n" +
                "       numHeart,\n" +
                "       createdAt,\n" +
                "       updatedAt\n" +
                "from Comment\n" +
                "where Idx = ?;";
        int getCommentContentsParams = commentIdx;
        return this.jdbcTemplate.queryForObject(getCommentContentsQuery,
                (rs, rowNum) -> new GetCommentContents(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getInt("articleIdx"),
                        rs.getInt("articleMediaIdx"),
                        rs.getString("comment"),
                        rs.getInt("numHeart"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt")
                ),
                getCommentContentsParams
        );
    }
    public void deleteArticle(DeleteArticleReq deleteArticleReq) {
        String deleteArticleQuery = "delete\n" +
                "from Article\n" +
                "where Idx = ? and userIdx = ?;";
        Object[] deleteArticleParams = new Object[]{deleteArticleReq.getArticleIdx(),
                deleteArticleReq.getUserIdx()};
        this.jdbcTemplate.update(deleteArticleQuery,deleteArticleParams);
    }
    public void deleteArticleInMedia(int articleIdx) {
        String deleteArticleQuery = "delete\n" +
                "from ArticleInMedia\n" +
                "where articleIdx = ?;";
        Object[] deleteArticleParams = new Object[]{articleIdx};
        this.jdbcTemplate.update(deleteArticleQuery,deleteArticleParams);
    }
    public void deleteProductTag(int articleMediaIdx) {
        String deleteProductTagQuery = "delete\n" +
                "from ProductTag\n" +
                "where articleMediaIdx = ?;";
        Object[] deleteProductTagParams = new Object[]{articleMediaIdx};
        this.jdbcTemplate.update(deleteProductTagQuery,deleteProductTagParams);
    }
    /**
     * check 관련 함수
     */
    public boolean checkDeletedToken(String JwtToken) {
        String checkTokenQuery = "select exists(select * from DeletedJwtToken where deletedToken = ?)";
        String checkTokenParams = JwtToken;
        if (this.jdbcTemplate.queryForObject(checkTokenQuery,
                int.class,
                checkTokenParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkUserIdx(int userIdx) {
        String checkUserIdxQuery = "select exists(select * from User where Idx = ?)";
        int checkUserIdxParams = userIdx;
        if (this.jdbcTemplate.queryForObject(checkUserIdxQuery, int.class, checkUserIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkArticleIdx(int articleIdx) {
        String checkArticleIdxQuery = "select exists(select * from Article where Idx = ?)";
        int checkArticleIdxParams = articleIdx;
        if (this.jdbcTemplate.queryForObject(checkArticleIdxQuery, int.class, checkArticleIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkArticleMediaIdx(int articleMediaIdx) {
        String checkArticleMediaIdxQuery = "select exists(select * from ArticleInMedia where Idx = ?)";
        int checkArticleMediaIdxParams = articleMediaIdx;
        if (this.jdbcTemplate.queryForObject(checkArticleMediaIdxQuery, int.class, checkArticleMediaIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkProductArticleIdx(int productArticleIdx) {
        String checkProductArticleIdxQuery = "select exists(select * from ProductArticle where Idx = ?)";
        int checkProductArticleIdxParams = productArticleIdx;
        if (this.jdbcTemplate.queryForObject(checkProductArticleIdxQuery, int.class, checkProductArticleIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkHeartArticleMediaIdx(int userIdx, int articleMediaIdx) {
        String checkHeartArticleMediaIdxQuery = "select exists(select *\n" +
                "              from Heart\n" +
                "              where userIdx = ?\n" +
                "                and articleMediaIdx = ?);";
        int checkHeartArticleMediaIdxParams1 = userIdx;
        int checkHeartArticleMediaIdxParams2 = articleMediaIdx;
        if (this.jdbcTemplate.queryForObject(checkHeartArticleMediaIdxQuery, int.class,
                checkHeartArticleMediaIdxParams1,
                checkHeartArticleMediaIdxParams2) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkScrapArticleMediaIdx(int userIdx, int articleMediaIdx) {
        String checkScrapArticleMediaIdxQuery = "select exists(select *\n" +
                "              from Scrap\n" +
                "              where userIdx = ?\n" +
                "                and articleMediaIdx = ?);";
        int checkScrapArticleMediaIdxParams1 = userIdx;
        int checkScrapArticleMediaIdxParams2 = articleMediaIdx;
        if (this.jdbcTemplate.queryForObject(checkScrapArticleMediaIdxQuery, int.class,
                checkScrapArticleMediaIdxParams1,
                checkScrapArticleMediaIdxParams2) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkArticleIdxAndUserIdx(int userIdx, int articleIdx) {
        String checkArticleIdxAndUserIdxQuery = "select exists(select *\n" +
                "              from Article\n" +
                "              where userIdx = ?\n" +
                "                and Idx = ?);";
        int checkArticleIdxAndUserIdxParams1 = userIdx;
        int checkArticleIdxAndUserIdxParams2 = articleIdx;
        if (this.jdbcTemplate.queryForObject(checkArticleIdxAndUserIdxQuery, int.class,
                checkArticleIdxAndUserIdxParams1,
                checkArticleIdxAndUserIdxParams2) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkHeartComment(int userIdx, int commentIdx) {
        String checkHeartCommentQuery = "select exists(select *\n" +
                "              from Heart\n" +
                "              where userIdx = ?\n" +
                "                and commentIdx = ?);";
        int checkHeartCommentParams1 = userIdx;
        int checkHeartCommentarams2 = commentIdx;
        if (this.jdbcTemplate.queryForObject(checkHeartCommentQuery, int.class,
                checkHeartCommentParams1,
                checkHeartCommentarams2) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
