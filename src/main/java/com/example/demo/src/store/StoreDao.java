package com.example.demo.src.store;

import com.example.demo.src.store.model.*;
import com.example.demo.src.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class StoreDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetStoreSearchHistoryRes> getStoreSearchHistory(int userIdx) {
        String getStoreSearchHistoryQuery = "select Idx, search from ProductSearchHistory where userIdx = ? order by createdAt desc";
        int getStoreSearchHistoryParams = userIdx;
        return this.jdbcTemplate.query(getStoreSearchHistoryQuery,
                (rs, rowNum) -> new GetStoreSearchHistoryRes(
                        rs.getInt("Idx"),
                        rs.getString("search")
                ),
                getStoreSearchHistoryParams
        );
    }

    public int postStoreSearchHistory(PostStoreSearchHistoryReq postStoreSearchHistoryReq) {
        String postStoreSearchHistoryQuery = "insert into ProductSearchHistory (userIdx, search) values(?,?)";
        Object[] postStoreSearchHistoryParams = new Object[]{postStoreSearchHistoryReq.getUserIdx(), postStoreSearchHistoryReq.getSearch()};
        this.jdbcTemplate.update(postStoreSearchHistoryQuery, postStoreSearchHistoryParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public void deleteSearch(int searchIdx) {
        String deleteSearchQuery = "delete\n" +
                "from ProductSearchHistory\n" +
                "where Idx = ?;";
        Object[] deleteSearchParams = new Object[]{searchIdx};
        this.jdbcTemplate.update(deleteSearchQuery,deleteSearchParams);
    }
    public void deleteSearchAll(int userIdx) {
        String deleteSearchAllQuery = "delete\n" +
                "from ProductSearchHistory\n" +
                "where userIdx = ?;";
        Object[] deleteSearchAllParams = new Object[]{userIdx};
        this.jdbcTemplate.update(deleteSearchAllQuery,deleteSearchAllParams);
    }

    public List<GetScrapProductsRes> getScrapProducts(int userIdx) {
        String getScrapProductsQuery = "select userIdx, productArticleIdx\n" +
                "from Scrap\n" +
                "where userIdx = ?;";
        int getScrapProductsParams = userIdx;
        return this.jdbcTemplate.query(getScrapProductsQuery,
                (rs, rowNum) -> new GetScrapProductsRes(
                        rs.getInt("userIdx"),
                        rs.getInt("productArticleIdx")
                ),
                getScrapProductsParams
        );
    }

    public List<GetAdvertiseStoreHomeRes> getAdvertiseStoreHome() {
        String getAdvertiseStoreHomeQuery = "select Idx, photo from AdvertiseStoreHome;";
        int getAdvertiseStoreHomeParams;
        return this.jdbcTemplate.query(getAdvertiseStoreHomeQuery,
                (rs, rowNum) -> new GetAdvertiseStoreHomeRes(
                        rs.getInt("Idx"),
                        rs.getString("photo")
                )
        );
    }

    public List<GetProductPhotoRes> getProductPhoto(int productArticleIdx) {
        String getProductArticlePhotoQuery = "select productArticleIdx, photo\n" +
                "from ProductArticlePhoto\n" +
                "where productArticleIdx = ?;";
        int getProductArticlePhotoParmas = productArticleIdx;
        return this.jdbcTemplate.query(getProductArticlePhotoQuery,
                (rs, rowNum) -> new GetProductPhotoRes(
                        rs.getInt("productArticleIdx"),
                        rs.getString("photo")
                ),
                getProductArticlePhotoParmas
        );
    }

    public GetProductInfoRes getProductInfo(int productArticleIdx) {
        String getProductArticleInfoQuery = "select Idx, category, company, title, salePercent, price, reviewRating, numOfReview\n" +
                "from ProductArticle\n" +
                "where Idx = ?;";
        int getProductArticleInfoParmas = productArticleIdx;
        return this.jdbcTemplate.queryForObject(getProductArticleInfoQuery,
                (rs, rowNum) -> new GetProductInfoRes(
                        rs.getInt("Idx"),
                        rs.getString("category"),
                        rs.getString("company"),
                        rs.getString("title"),
                        rs.getInt("salePercent"),
                        rs.getInt("price"),
                        rs.getDouble("reviewRating"),
                        rs.getInt("numOfReview")
                ),
                getProductArticleInfoParmas
        );
    }
    public List<GetProductArticleShippingRes> getProductArticleShipping(int productArticleIdx) {
        String getProductArticleShippingQuery = "select productArticleIdx, shipping\n" +
                "from ProductShipping\n" +
                "where productArticleIdx = ?;";
        int getProductArticleShippingParmas = productArticleIdx;
        return this.jdbcTemplate.query(getProductArticleShippingQuery,
                (rs, rowNum) -> new GetProductArticleShippingRes(
                        rs.getInt("productArticleIdx"),
                        rs.getString("shipping")
                ),
                getProductArticleShippingParmas
        );
    }

    public List<GetUserProductScrapRes> getUserProductScrap(int userIdx) {
        String getUserProductScrapQuery = "select Idx, productArticleIdx\n" +
                "from Scrap\n" +
                "where userIdx = ?\n" +
                "order by createdAt desc;";
        int getUserProductScrapParams = userIdx;
        return this.jdbcTemplate.query(getUserProductScrapQuery,
                (rs, rowNum) -> new GetUserProductScrapRes(
                        rs.getInt("Idx"),
                        rs.getInt("productArticleIdx")
                ),
                getUserProductScrapParams
        );
    }
    public List<GetShopBasketOption> getShopBasketOption(int userIdx) {
        String getShopBasketOptionQuery = "select productArticleIdx,\n" +
                "       optionOneIdx,\n" +
                "       optionTwoIdx,\n" +
                "       productNum,\n" +
                "       optionExtraIdx,\n" +
                "       extraNum\n" +
                "from ShoppingBasket\n" +
                "where userIdx = ?;";
        int getShopBasketOptionParams = userIdx;
        return this.jdbcTemplate.query(getShopBasketOptionQuery,
                (rs, rowNum) -> new GetShopBasketOption(
                        rs.getInt("productArticleIdx"),
                        rs.getInt("optionOneIdx"),
                        rs.getInt("optionTwoIdx"),
                        rs.getInt("productNum"),
                        rs.getInt("optionExtraIdx"),
                        rs.getInt("extraNum")
                ),
                getShopBasketOptionParams
        );
    }

    public List<GetUserShoppingBasketRes> getUserShoppingBasket(int userIdx) {
        String getUserShoppingBascketQuery = "select Idx,\n" +
                "userIdx,\n" +
                "       productArticleIdx,\n" +
                "       optionOneName,\n" +
                "       optionOneChoose,\n" +
                "       optionTwoName,\n" +
                "       optionTwoChoose,\n" +
                "       productNum       as numberOfProduct,\n" +
                "       optionOnePrice   as productPrice,\n" +
                "       optionExtraName,\n" +
                "       optionExtraChoose,\n" +
                "       extraNum         as numberOfExtraProduct,\n" +
                "       optionExtraPrice as extraProductPrice\n" +
                "from ShoppingBasket\n" +
                "         inner join(select Idx          as oneIdx,\n" +
                "                           optionName   as optionOneName,\n" +
                "                           optionChoose as optionOneChoose,\n" +
                "                           price        as optionOnePrice\n" +
                "                    from ProductOptionOne) OptionOne\n" +
                "                   on ShoppingBasket.optionOneIdx = OptionOne.oneIdx\n" +
                "         left outer join(select Idx          as twoIdx,\n" +
                "                                optionName   as optionTwoName,\n" +
                "                                optionChoose as optionTwoChoose,\n" +
                "                                price        as optionTwoPrice\n" +
                "                         from ProductOptionTwo) OptionTwo\n" +
                "                        on ShoppingBasket.optionTwoIdx = OptionTwo.twoIdx\n" +
                "         left outer join(select Idx          as extraIdx,\n" +
                "                                optionName   as optionExtraName,\n" +
                "                                optionChoose as optionExtraChoose,\n" +
                "                                price        as optionExtraPrice\n" +
                "                         from ProductOptionExtra) OptionExtra\n" +
                "                        on ShoppingBasket.optionExtraIdx = OptionExtra.extraIdx\n" +
                "where userIdx = ?;";
        int getUserShoppingBascketParams = userIdx;
        return this.jdbcTemplate.query(getUserShoppingBascketQuery,
                (rs, rowNum) -> new GetUserShoppingBasketRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getInt("productArticleIdx"),
                        rs.getString("optionOneName"),
                        rs.getString("optionOneChoose"),
                        rs.getString("optionTwoName"),
                        rs.getString("optionTwoChoose"),
                        rs.getInt("numberOfProduct"),
                        rs.getInt("productPrice"),
                        rs.getString("optionExtraName"),
                        rs.getString("optionExtraChoose"),
                        rs.getInt("numberOfExtraProduct"),
                        rs.getInt("extraProductPrice")
                ),
                getUserShoppingBascketParams
        );
    }

    public int postShoppingBasket(PostShoppingBasketReq postShoppingBascketReq) {
        String postShoppingBascketQuery = "insert into ShoppingBasket (userIdx, productArticleidx, optionOneIdx, optionTwoIdx," +
                "productNum, optionExtraIdx, extraNum) values(?,?,?,?,?,?,?)";
        Object[] postShoppingBascketParams = new Object[]{postShoppingBascketReq.getUserIdx(), postShoppingBascketReq.getProductArticleIdx(),
                postShoppingBascketReq.getOptionOneIdx(), postShoppingBascketReq.getOptionTwoIdx(), postShoppingBascketReq.getProductNum(),
                postShoppingBascketReq.getOptionExtraIdx(), postShoppingBascketReq.getExtraNum()};
        this.jdbcTemplate.update(postShoppingBascketQuery, postShoppingBascketParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public void patchAddbuyNumber(int productArticleIdx) {
        String patchAddbuyNumberQuery = "update ProductArticle\n" +
                "set buyNumber = buyNumber + 1\n" +
                "where Idx = ?;";
        Object[] patchAddbuyNumberParams = new Object[]{productArticleIdx};
        this.jdbcTemplate.update(patchAddbuyNumberQuery,patchAddbuyNumberParams);
    }

    public List<GetProductArticleOptionOneRes> getProductArticleOptionOne(int productArticleIdx) {
        String getProductArticleOptionOneQuery = "select Idx, productArticleIdx, optionName, optionChoose, price\n" +
                "from ProductOptionOne\n" +
                "where productArticleIdx = ?;";
        int getProductArticleOptionOneParams = productArticleIdx;
        return this.jdbcTemplate.query(getProductArticleOptionOneQuery,
                (rs, rowNum) -> new GetProductArticleOptionOneRes(
                        rs.getInt("productArticleIdx"),
                        rs.getInt("Idx"),
                        rs.getString("optionName"),
                        rs.getString("optionChoose"),
                        rs.getInt("price")
                ),
                getProductArticleOptionOneParams
        );
    }

    public List<GetProductArticleOptionTwoRes> getProductArticleOptionTwo(int productArticleIdx) {
        String getProductArticleOptionTwoQuery = "select Idx, productArticleIdx, optionName, optionChoose, price\n" +
                "from ProductOptionTwo\n" +
                "where productArticleIdx = ?;";
        int getProductArticleOptionTwoParams = productArticleIdx;
        return this.jdbcTemplate.query(getProductArticleOptionTwoQuery,
                (rs, rowNum) -> new GetProductArticleOptionTwoRes(
                        rs.getInt("productArticleIdx"),
                        rs.getInt("Idx"),
                        rs.getString("optionName"),
                        rs.getString("optionChoose"),
                        rs.getInt("price")
                ),
                getProductArticleOptionTwoParams
        );
    }

    public List<GetProductArticleOptionExtraRes> getProductArticleOptionExtra(int productArticleIdx) {
        String getProductArticleOptionExtraQuery = "select Idx, productArticleIdx, optionName, optionChoose, price\n" +
                "from ProductOptionExtra\n" +
                "where productArticleIdx = ?;";
        int getProductArticleOptionExtraParams = productArticleIdx;
        return this.jdbcTemplate.query(getProductArticleOptionExtraQuery,
                (rs, rowNum) -> new GetProductArticleOptionExtraRes(
                        rs.getInt("productArticleIdx"),
                        rs.getInt("Idx"),
                        rs.getString("optionName"),
                        rs.getString("optionChoose"),
                        rs.getInt("price")
                ),
                getProductArticleOptionExtraParams
        );
    }

    public List<GetProductArticleDetailInfoRes> getProductArticleDetailInfo(int productArticleIdx) {
        String getProductArticleDetailInfoQuery = "select productArticleIdx, info\n" +
                "from ProductInfo\n" +
                "where productArticleIdx = ?;";
        int getProductArticleDetailInfoParams = productArticleIdx;
        return this.jdbcTemplate.query(getProductArticleDetailInfoQuery,
                (rs, rowNum) -> new GetProductArticleDetailInfoRes(
                        rs.getInt("productArticleIdx"),
                        rs.getString("info")
                ),
                getProductArticleDetailInfoParams
        );
    }

    public List<GetProductArticleByCategoryRes> getProductArticleByCategory(String category) {
        String getProductArticleByCategoryQuery = "select Idx\n" +
                "from ProductArticle\n" +
                "where category = ?";
        String getProductArticleByCategoryParams = category;
        return this.jdbcTemplate.query(getProductArticleByCategoryQuery,
                (rs, rowNum) -> new GetProductArticleByCategoryRes(
                        rs.getInt("Idx")
                ),
                getProductArticleByCategoryParams
        );
    }
    public List<GetProductArticleTodayDealRes> getProductArticleTodayDeal() {
        String getProductArticleTodayDealQuery = "select productArticleIdx, createdAt\n" +
                "from TodayDeal;";
        String getProductArticleTodayDealParams;
        return this.jdbcTemplate.query(getProductArticleTodayDealQuery,
                (rs, rowNum) -> new GetProductArticleTodayDealRes(
                        rs.getInt("productArticleIdx"),
                        rs.getString("createdAt")
                )
        );
    }
    public int postBuyProduct(PostBuyProductReq postBuyProductReq) {
        String postBuyProductQuery = "insert into BuyProduct (userIdx, productArticleidx, optionOneIdx, optionTwoIdx," +
                "productNum, optionExtraIdx, extraNum) values(?,?,?,?,?,?,?)";
        Object[] postBuyProductParams = new Object[]{postBuyProductReq.getUserIdx(), postBuyProductReq.getProductArticleIdx(),
                postBuyProductReq.getOptionOneIdx(), postBuyProductReq.getOptionTwoIdx(), postBuyProductReq.getProductNum(),
                postBuyProductReq.getOptionExtraIdx(), postBuyProductReq.getExtraNum()};
        this.jdbcTemplate.update(postBuyProductQuery, postBuyProductParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public int postOrderInfo(PostBuyProductReq postBuyProductReq) {
        String postOrderInfoQuery = "insert into BuyOrderInfo (userIdx, buyProductIdx, orderName, orderEmail, " +
                "orderPhone, deliveredName, deliveredPhone, deliveredAddress) values(?,?,?,?,?,?,?,?)";
        Object[] postOrderInfoParams = new Object[]{postBuyProductReq.getUserIdx(), postBuyProductReq.getBuyProductIdx(),
                postBuyProductReq.getOrderName(), postBuyProductReq.getOrderEmail(), postBuyProductReq.getOrderPhone(),
                postBuyProductReq.getDeliverName(), postBuyProductReq.getDeliverPhone(), postBuyProductReq.getDeliveredAddress()};
        this.jdbcTemplate.update(postOrderInfoQuery, postOrderInfoParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    public List<GetProductArticleIdx> getProductBySearch(String keyword) {
        String getProductBySearchQuery = "select Idx\n" +
                "from ProductArticle\n" +
                "where title LIKE ?;";
        String getProductBySearchParams = "%"+keyword+"%";
        return this.jdbcTemplate.query(getProductBySearchQuery,
                (rs, rowNum) -> new GetProductArticleIdx(
                        rs.getInt("Idx")
                ),
                getProductBySearchParams
        );
    }
    public void deleteProductScrap(DeleteProductScrapReq deleteProductScrapReq) {
        String deleteProductScrapQuery = "delete\n" +
                "from Scrap\n" +
                "where userIdx = ? and productArticleIdx = ?;";
        Object[] deleteProductScrapParams = new Object[]{deleteProductScrapReq.getUserIdx(),
                deleteProductScrapReq.getProductArticleIdx()};
        this.jdbcTemplate.update(deleteProductScrapQuery,deleteProductScrapParams);
    }
    public void deleteBasket(int basketIdx) {
        String deleteBasketQuery = "delete\n" +
                "from ShoppingBasket\n" +
                "where Idx = ?;";
        Object[] deleteBasketParams = new Object[]{basketIdx};
        this.jdbcTemplate.update(deleteBasketQuery,deleteBasketParams);
    }
    public void deleteBuy(int buyIdx) {
        String deleteBuyQuery = "delete\n" +
                "from BuyProduct\n" +
                "where Idx = ?;";
        Object[] deleteBuyParams = new Object[]{buyIdx};
        this.jdbcTemplate.update(deleteBuyQuery,deleteBuyParams);
    }
    public List<GetProductArticleIdx> getPopularProducts() {
        String getPopularProductsQuery = "select Idx\n" +
                "from ProductArticle\n" +
                "order by buyNumber desc;";
        String getPopularProductsParams;
        return this.jdbcTemplate.query(getPopularProductsQuery,
                (rs, rowNum) -> new GetProductArticleIdx(
                        rs.getInt("Idx")
                )
        );
    }
    public void deleteSeeProduct(PostSeeProductReq postSeeProductReq) {
        String deleteProductScrapQuery = "delete\n" +
                "from SeeProduct\n" +
                "where userIdx = ? and productArticleIdx = ?;";
        Object[] deleteProductScrapParams = new Object[]{
                postSeeProductReq.getUserIdx(), postSeeProductReq.getProductArticleIdx()};
        this.jdbcTemplate.update(deleteProductScrapQuery,deleteProductScrapParams);
    }
    public int postSeeProduct(PostSeeProductReq postSeeProductReq) {
        String postSeeProductQuery = "insert into SeeProduct (userIdx, productArticleIdx) values(?,?)";
        Object[] postSeeProductParams = new Object[]{postSeeProductReq.getUserIdx(), postSeeProductReq.getProductArticleIdx()};
        this.jdbcTemplate.update(postSeeProductQuery, postSeeProductParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public List<GetProductArticleIdx> getLastSeeProduct(int userIdx) {
        String getPopularProductsQuery = "select productArticleIdx\n" +
                "from SeeProduct\n" +
                "where userIdx = ?\n" +
                "order by createdAt desc;\n" +
                "\n";
        int getPopularProductsParams = userIdx;
        return this.jdbcTemplate.query(getPopularProductsQuery,
                (rs, rowNum) -> new GetProductArticleIdx(
                        rs.getInt("productArticleIdx")
                ),
                getPopularProductsParams
        );
    }
    public GetProductRatingNumRes getProductRatingNum(int productArticleIdx) {
        String getProductRatingsQuery = "select productArticleIdx, one, two, three, four, five\n" +
                "from ReviewRatingCal\n" +
                "where productArticleIdx = ?;";
        int getProductRatingsParams = productArticleIdx;
        return this.jdbcTemplate.queryForObject(getProductRatingsQuery,
                (rs, rowNum) -> new GetProductRatingNumRes(
                        rs.getInt("productArticleIdx"),
                        rs.getInt("one"),
                        rs.getInt("two"),
                        rs.getInt("three"),
                        rs.getInt("four"),
                        rs.getInt("five")
                ),
                getProductRatingsParams
        );
    }
    public GetUserInfoRes getUserInfo(int userIdx) {
        String getUserInfoQuery = "select * from User where Idx = ?";
        int getUserInfoParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserInfoQuery,
                (rs, rowNum) -> new GetUserInfoRes(
                        rs.getInt("Idx"),
                        rs.getString("email"),
                        rs.getString("passwd"),
                        rs.getString("nickName"),
                        rs.getString("profileImage"),
                        rs.getString("backImage"),
                        rs.getString("introduction"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("status")),
                getUserInfoParams);
    }
    public List<GetReviewIdx> getRivewIdx(int productArticleIdx) {
        String getRivewIdxQuery = "select Idx\n" +
                "from Review\n" +
                "where productArticleIdx = ?\n" +
                "order by thisReviewHelp desc;";
        int getRivewIdxParams = productArticleIdx;
        return this.jdbcTemplate.query(getRivewIdxQuery,
                (rs, rowNum) -> new GetReviewIdx(
                        rs.getInt("Idx")
                ),
                getRivewIdxParams
        );
    }
    public ReviewInfo getReviewInfo(int reviewIdx) {
        String getRivewIdxQuery = "select Idx, userIdx, productArticleIdx, " +
                "photo, rating, contents, thisReviewHelp, createdAt\n" +
                "from Review\n" +
                "where Idx = ?;";
        int getRivewIdxParams = reviewIdx;
        return this.jdbcTemplate.queryForObject(getRivewIdxQuery,
                (rs, rowNum) -> new ReviewInfo(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getInt("productArticleIdx"),
                        rs.getString("photo"),
                        rs.getInt("rating"),
                        rs.getString("contents"),
                        rs.getInt("thisReviewHelp"),
                        rs.getString("createdAt")
                ),
                getRivewIdxParams
        );
    }
    public List<GetUserClickHelpIdx> getUserClickHelp(int userIdx) {
        String getReviewPhotosQuery = "select reviewIdx\n" +
                "from ReviewHelpful\n" +
                "where userIdx = ?;";
        int getReviewPhotosParams = userIdx;
        return this.jdbcTemplate.query(getReviewPhotosQuery,
                (rs, rowNum) -> new GetUserClickHelpIdx(
                        rs.getInt("reviewIdx")
                ),
                getReviewPhotosParams
        );
    }
    /**
     * check 관련 함수 모음
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
    public boolean checkSearchIdx(DeleteSearchReq deleteSearchReq) {
        String checkSearchIdxQuery = "select exists(select * from ProductSearchHistory where Idx = ? and userIdx = ?)";
        int checkSearchIdxParams1 = deleteSearchReq.getSearchIdx();
        int checkSearchIdxParams2 = deleteSearchReq.getUserIdx();
        if (this.jdbcTemplate.queryForObject(checkSearchIdxQuery, int.class,
                checkSearchIdxParams1,
                checkSearchIdxParams2) == 1) {
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
    public boolean checkOptionOneIdx(int optionOneIdx) {
        String checkOptionOneIdxQuery = "select exists(select * from ProductOptionOne where Idx = ?)";
        int checkOptionOneIdxParams = optionOneIdx;
        if (this.jdbcTemplate.queryForObject(checkOptionOneIdxQuery, int.class, checkOptionOneIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkOptionTwoIdx(int optionTwoIdx) {
        String checkOptionTwoIdxQuery = "select exists(select * from ProductOptionTwo where Idx = ?)";
        int checkOptionTwoIdxParams = optionTwoIdx;
        if (this.jdbcTemplate.queryForObject(checkOptionTwoIdxQuery, int.class, checkOptionTwoIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkOptionExtraIdx(int optionExtraIdx) {
        String checkOptionExtraIdxQuery = "select exists(select * from ProductOptionExtra where Idx = ?)";
        int checkOptionExtraIdxParams = optionExtraIdx;
        if (this.jdbcTemplate.queryForObject(checkOptionExtraIdxQuery, int.class, checkOptionExtraIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkBasketIdx(int basketIdx) {
        String checkBasketIdxQuery = "select exists(select * from ShoppingBasket where Idx = ?)";
        int checkBasketIdxParams = basketIdx;
        if (this.jdbcTemplate.queryForObject(checkBasketIdxQuery, int.class, checkBasketIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkBuyProductIdx(int buyProductIdx) {
        String checkBuyProductIdxQuery = "select exists(select * from BuyProduct where Idx = ?)";
        int checkBuyProductIdxParams = buyProductIdx;
        if (this.jdbcTemplate.queryForObject(checkBuyProductIdxQuery, int.class, checkBuyProductIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkAlreadySee(PostSeeProductReq postSeeProductReq) {
        String checkAlreadySeeQuery = "select exists(select Idx from SeeProduct where userIdx = ? and productArticleIdx = ?);";
        int checkAlreadySeeParam1 = postSeeProductReq.getUserIdx();
        int checkAlreadySeeParam2 = postSeeProductReq.getProductArticleIdx();
        if (this.jdbcTemplate.queryForObject(checkAlreadySeeQuery,
                int.class,
                checkAlreadySeeParam1,
                checkAlreadySeeParam2) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
