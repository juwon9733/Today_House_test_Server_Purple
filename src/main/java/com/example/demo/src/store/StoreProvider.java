package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.*;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class StoreProvider {

    @Autowired
    private final StoreDao storeDao;

    public List<GetStoreSearchHistoryRes> getStoreSearchHistory(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            List<GetStoreSearchHistoryRes> getStoreSearchHistoryRes = storeDao.getStoreSearchHistory(userIdx);
            return getStoreSearchHistoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetScrapProductsRes> getScrapProducts(int userIdx) throws BaseException {
        if(checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            List<GetScrapProductsRes> getScrapProductsRes = storeDao.getScrapProducts(userIdx);
            return getScrapProductsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetAdvertiseStoreHomeRes> getAdvertiseStoreHome() throws BaseException {
        try {
            List<GetAdvertiseStoreHomeRes> getAdvertiseStoreHomeRes = storeDao.getAdvertiseStoreHome();
            return getAdvertiseStoreHomeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductPhotoRes> getProductPhoto(int productArticleIdx) throws BaseException {
        if (checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            List<GetProductPhotoRes> getProductPhotoRes = storeDao.getProductPhoto(productArticleIdx);
            return getProductPhotoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProductInfoRes getProductInfo(int productArticleIdx) throws BaseException {
        if (checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            GetProductInfoRes getProductInfoRes = storeDao.getProductInfo(productArticleIdx);
            return getProductInfoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductArticleShippingRes> getProductArticleShipping(int productArticleIdx) throws BaseException {
        if (checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            List<GetProductArticleShippingRes> getProductArticleShippingRes = storeDao.getProductArticleShipping(productArticleIdx);
            return getProductArticleShippingRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserProductScrapRes> getUserProductScrap(int userIdx) throws BaseException {
        try {
            List<GetUserProductScrapRes> getUserProductScrapRes = storeDao.getUserProductScrap(userIdx);
            return getUserProductScrapRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetShopBasketAllRes> getUserShoppingBasket(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            List<GetShopBasketAllRes> getShopBasketAllRes = new ArrayList<>();

            List<GetUserShoppingBasketRes> getUserShoppingBasketRes = storeDao.getUserShoppingBasket(userIdx);
            for (GetUserShoppingBasketRes var : getUserShoppingBasketRes) {
                int productArticleIdx = var.getProductArticleIdx();

                List<GetProductArticleShippingRes> shipping = getProductArticleShipping(productArticleIdx);     // shipping
                List<GetProductPhotoRes> photo = getProductPhoto(productArticleIdx); // photo
                GetProductInfoRes productInfo = getProductInfo(productArticleIdx); // category ~ numOfReview;

                getShopBasketAllRes.add(
                        new GetShopBasketAllRes(var.getBasketIdx(), userIdx, productArticleIdx, photo.get(0).getPhotoUrl(),
                                productInfo.getCompany(), productInfo.getTitle(), shipping.get(0).getShippingInfo(),
                                var.getOptionOneName(), var.getOptionOneChoose(), var.getOptionTwoName(),
                                var.getOptionTwoChoose(), var.getNumberOfProduct(), var.getProductPrice(),
                                var.getOptionExtraName(), var.getOptionExtraChoose(), var.getNumberOfExtraProduct(),
                                var.getExtraProductPrice())
                );
            }
            return getShopBasketAllRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductArticleDetailInfoRes> getProductArticleDetailInfo(int productArticleIdx) throws BaseException {
        if (checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            List<GetProductArticleDetailInfoRes> getProductArticleDetailInfoRes = storeDao.getProductArticleDetailInfo(productArticleIdx);
            return getProductArticleDetailInfoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductArticleOptionOneRes> getProductArticleOptionOne(int productArticleIdx) throws BaseException {
        if (checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            List<GetProductArticleOptionOneRes> getProductArticleOptionOneRes = storeDao.getProductArticleOptionOne(productArticleIdx);
            return getProductArticleOptionOneRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductArticleOptionTwoRes> getProductArticleOptionTwo(int productArticleIdx) throws BaseException {
        if (checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            List<GetProductArticleOptionTwoRes> getProductArticleOptionTwoRes = storeDao.getProductArticleOptionTwo(productArticleIdx);
            return getProductArticleOptionTwoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductArticleOptionExtraRes> getProductArticleOptionExtra(int productArticleIdx) throws BaseException {
        if (checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            List<GetProductArticleOptionExtraRes> getProductArticleOptionExtraRes = storeDao.getProductArticleOptionExtra(productArticleIdx);
            return getProductArticleOptionExtraRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * ################################################################################################################################################################
     */
    public List<GetProductArticleByCategoryRes> getProductArticleByCategory(String category) throws BaseException {
        try {
            List<GetProductArticleByCategoryRes> getProductArticleByCategoryRes = storeDao.getProductArticleByCategory(category);
            List<GetProductArticleByCategoryRes> temp = new ArrayList<>();
            for (GetProductArticleByCategoryRes getProductArticleByCategoryRe : getProductArticleByCategoryRes) {
                int productArticleIdx = getProductArticleByCategoryRe.getProductArticleIdx();
                System.out.println(productArticleIdx);
                temp.add(new GetProductArticleByCategoryRes(productArticleIdx));
            }
            getProductArticleByCategoryRes = temp;
            return getProductArticleByCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductArticleTodayDealRes> getProductArticleTodayDeal() throws BaseException {
        try {
            List<GetProductArticleTodayDealRes> getProductArticleTodayDealRes = storeDao.getProductArticleTodayDeal();
            return getProductArticleTodayDealRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductArticleTodayDealAllRes> getProductArticleTodayDealAll(int userIdx) throws BaseException {
        if(checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            boolean isScrap = false;
            List<GetProductArticleTodayDealAllRes> getProductArticleTodayDealAllRes = new ArrayList<>();

            // 해당 유저가 스크랩한, 상품 게시글 인덱스
            List<GetUserProductScrapRes> userScrapIdx = getUserProductScrap(userIdx);
            // 오늘의 딜에 해당하는, 상품 게시글 인덱스
            List<GetProductArticleTodayDealRes> productIdxTodayDeal = storeDao.getProductArticleTodayDeal();

            for (GetProductArticleTodayDealRes getProductArticleTodayDealRes : productIdxTodayDeal) {
                int productArticleIdx = getProductArticleTodayDealRes.getProductArticleIdx();
                String enrolledTimeTodayDeal = getProductArticleTodayDealRes.getEnrolledTimeInTodayDeal();
                List<GetProductPhotoRes> photo = getProductPhoto(productArticleIdx); // photo
                GetProductInfoRes productInfo = getProductInfo(productArticleIdx); // category ~ numOfReview;

                // 해당 유저가 스크랩한 것인지, 비교하는 구문
                for (GetUserProductScrapRes scrapProductIdx : userScrapIdx) {
                    if (productArticleIdx == scrapProductIdx.getProductArticleIdx()) isScrap = true;
                }
                // List add
                getProductArticleTodayDealAllRes.add(new GetProductArticleTodayDealAllRes(productArticleIdx, productInfo.getCategory(),
                        photo.get(0).getPhotoUrl(), productInfo.getCompany(), productInfo.getTitle(), productInfo.getSalePercent(),
                        productInfo.getPrice(), productInfo.getReviewRating(), productInfo.getNumOfReview(), enrolledTimeTodayDeal, isScrap
                ));
                // scrap 변수 초기화
                isScrap = false;
            }
            return getProductArticleTodayDealAllRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<ProductArticle> getProductByCategoryAll(GetProductByCategoryAllReq getProductByCategoryAllReq) throws BaseException {
        if(checkUserIdx(getProductByCategoryAllReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            boolean isScrap = false;
            List<GetUserProductScrapRes> userScrapProductIdx = getUserProductScrap(getProductByCategoryAllReq.getUserIdx());

            List<ProductArticle> productArticleRes = new ArrayList<>();
            // 카테고리에 해당하는, 상품 게시글 인덱스
            List<GetProductArticleByCategoryRes> getProductArticleByCategoryRes = storeDao.getProductArticleByCategory(
                    getProductByCategoryAllReq.getCategory());
            for (GetProductArticleByCategoryRes getProductArticleByCategoryRe : getProductArticleByCategoryRes) {
                int productArticleIdx = getProductArticleByCategoryRe.getProductArticleIdx();

                List<GetProductPhotoRes> photo = getProductPhoto(productArticleIdx); // photo
                GetProductInfoRes productInfo = getProductInfo(productArticleIdx); // category ~ numOfReview;

                // 해당 유저가 스크랩한 것인지, 비교하는 구문
                for (GetUserProductScrapRes scrapProductIdx : userScrapProductIdx) {
                    if (productArticleIdx == scrapProductIdx.getProductArticleIdx()) isScrap = true;
                }

                productArticleRes.add(new ProductArticle(photo.get(0).getProductArticleIdx(), productInfo.getCategory(),
                        photo.get(0).getPhotoUrl(), productInfo.getCompany(), productInfo.getTitle(), productInfo.getSalePercent(),
                        productInfo.getPrice(), productInfo.getReviewRating(), productInfo.getNumOfReview(), isScrap
                ));
                // 초기화
                isScrap = false;
            }
            return productArticleRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<ProductArticle> getProductBySearch(GetProductBySearchReq getProductBySearchReq) throws BaseException {
        if (checkUserIdx(getProductBySearchReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            boolean isScrap = false;
            List<ProductArticle> getProductArticleOuterInfo = new ArrayList<>();

            // 해당 유저가 스크랩한, 상품 게시글 인덱스
            List<GetUserProductScrapRes> userScrapProductIdx = getUserProductScrap(getProductBySearchReq.getUserIdx());
            // keyword로 검색한 결과의, 상품 게시글 인덱스
            List<GetProductArticleIdx> getProductArticleIdx = storeDao.getProductBySearch(getProductBySearchReq.getKeyword());

            for (GetProductArticleIdx productArticleIdx : getProductArticleIdx) {
                int articleIdx = productArticleIdx.getProductArticleIdx();

                List<GetProductPhotoRes> photo = getProductPhoto(articleIdx); // photo
                GetProductInfoRes productInfo = getProductInfo(articleIdx); // category ~ numOfReview;

                // 해당 유저가 스크랩한 것인지, 비교하는 구문
                for (GetUserProductScrapRes scrapProductIdx : userScrapProductIdx) {
                    if (articleIdx == scrapProductIdx.getProductArticleIdx()) isScrap = true;
                }
                getProductArticleOuterInfo.add(
                        new ProductArticle(articleIdx, productInfo.getCategory(), photo.get(0).getPhotoUrl(),
                                productInfo.getCompany(), productInfo.getTitle(), productInfo.getSalePercent(),
                                productInfo.getPrice(), productInfo.getReviewRating(), productInfo.getNumOfReview(),
                                isScrap)
                );
                // 초기화
                isScrap = false;
            }
            return getProductArticleOuterInfo;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserProductScrapAllRes> getUserProductScrapAll(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            List<GetUserProductScrapAllRes> getProductArticleOuterInfo = new ArrayList<>();
            // 특정 유저가 스크랩한, 상품 게시글의 인덱스(스크랩 인덱스도 같이)
            List<GetUserProductScrapRes> getUserProductScrap = storeDao.getUserProductScrap(userIdx);
            for (GetUserProductScrapRes getUserProductScrapRes : getUserProductScrap) {
                int productArticleIdx = getUserProductScrapRes.getProductArticleIdx();
                int scrapIdx = getUserProductScrapRes.getScrapIdx();
                System.out.println(productArticleIdx);
                List<GetProductPhotoRes> photo = getProductPhoto(productArticleIdx);
                GetProductInfoRes productInfo = getProductInfo(productArticleIdx);
                getProductArticleOuterInfo.add(
                        new GetUserProductScrapAllRes(productArticleIdx, productInfo.getCategory(), photo.get(0).getPhotoUrl(),
                                productInfo.getCompany(), productInfo.getTitle(), productInfo.getSalePercent(),
                                productInfo.getPrice(), productInfo.getReviewRating(), productInfo.getNumOfReview(),
                                true, scrapIdx)
                );
            }
            return getProductArticleOuterInfo;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProductDetailAllRes getProductDetailAll(GetProductDetailAllReq getProductDetailAllReq) throws BaseException {
        if (checkUserIdx(getProductDetailAllReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if (checkProductArticleIdx(getProductDetailAllReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            boolean isScrap = false;

            // 해당 유저가 스크랩한, 상품 게시글 인덱스
            List<GetUserProductScrapRes> userScrapProductIdx = getUserProductScrap(getProductDetailAllReq.getUserIdx());

            int productArticleIdx = getProductDetailAllReq.getProductArticleIdx();

            List<GetProductArticleShippingRes> shipping = getProductArticleShipping(productArticleIdx);     // shipping
            List<GetProductPhotoRes> photo = getProductPhoto(productArticleIdx); // photo
            GetProductInfoRes productInfo = getProductInfo(productArticleIdx); // category ~ numOfReview;

            // List<String> photoUrl 만들기
            List<String> photoUrl = new ArrayList<>();
            for (GetProductPhotoRes getProductPhotoRes : photo) {
                photoUrl.add(getProductPhotoRes.getPhotoUrl());
            }
            // List<String> shippingInfo 만들기
            List<String> ShippingInfo = new ArrayList<>();
            for (GetProductArticleShippingRes getProductArticleShippingRes : shipping) {
                ShippingInfo.add(getProductArticleShippingRes.getShippingInfo());
            }

            // 해당 유저가 스크랩한 것인지, 비교하는 구문
            for (GetUserProductScrapRes scrapProductIdx : userScrapProductIdx) {
                if (productArticleIdx == scrapProductIdx.getProductArticleIdx()) isScrap = true;
            }
            GetProductDetailAllRes getProductDetailAllRes = GetProductDetailAllRes.builder()
                    .productArticleIdx(productArticleIdx)
                    .category(productInfo.getCategory())
                    .photo(photoUrl)
                    .company(productInfo.getCompany())
                    .title(productInfo.getTitle())
                    .rating(productInfo.getReviewRating())
                    .numReview(productInfo.getNumOfReview())
                    .salePercent(productInfo.getSalePercent())
                    .price(productInfo.getPrice())
                    .shipping(ShippingInfo)
                    .isScrap(isScrap)
                    .build();

            return getProductDetailAllRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<ProductArticle> getPopularProducts(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            boolean isScrap = false;
            List<ProductArticle> productArticles = new ArrayList<>();

            // 인기 상품 순, 상품 게시글 인덱스
            List<GetProductArticleIdx> getProductArticleIdxes = storeDao.getPopularProducts();
            // 해당 유저가 스크랩한, 상품 게시글 인덱스
            List<GetUserProductScrapRes> userScrapProductIdx = getUserProductScrap(userIdx);

            for (GetProductArticleIdx var : getProductArticleIdxes) {
                int productArticleIdx = var.getProductArticleIdx();
                List<GetProductArticleShippingRes> shipping = getProductArticleShipping(productArticleIdx);     // shipping
                List<GetProductPhotoRes> photo = getProductPhoto(productArticleIdx); // photo
                GetProductInfoRes productInfo = getProductInfo(productArticleIdx); // category ~ numOfReview;

                // 해당 유저가 스크랩한 것인지, 비교하는 구문
                for (GetUserProductScrapRes scrapProductIdx : userScrapProductIdx) {
                    if (productArticleIdx == scrapProductIdx.getProductArticleIdx()) isScrap = true;
                }
                productArticles.add(new ProductArticle(productArticleIdx, productInfo.getCategory(),
                        photo.get(0).getPhotoUrl(), productInfo.getCompany(), productInfo.getTitle(),
                        productInfo.getSalePercent(), productInfo.getPrice(), productInfo.getReviewRating(),
                        productInfo.getNumOfReview(), isScrap));
                // 초기화
                isScrap = false;
            }
            return productArticles;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<ProductArticle> getLastSeeProduct(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            boolean isScrap = false;
            List<ProductArticle> productArticles = new ArrayList<>();

            // 유저가 최근에 본 상품 게시글 인덱스
            List<GetProductArticleIdx> getProductArticleIdxes = storeDao.getLastSeeProduct(userIdx);
            // 해당 유저가 스크랩한, 상품 게시글 인덱스
            List<GetUserProductScrapRes> userScrapProductIdx = getUserProductScrap(userIdx);

            for (GetProductArticleIdx var : getProductArticleIdxes) {
                int productArticleIdx = var.getProductArticleIdx();

                List<GetProductArticleShippingRes> shipping = getProductArticleShipping(productArticleIdx);     // shipping
                List<GetProductPhotoRes> photo = getProductPhoto(productArticleIdx); // photo
                GetProductInfoRes productInfo = getProductInfo(productArticleIdx); // category ~ numOfReview;

                // 해당 유저가 스크랩한 것인지, 비교하는 구문
                for (GetUserProductScrapRes scrapProductIdx : userScrapProductIdx) {
                    if (productArticleIdx == scrapProductIdx.getProductArticleIdx()) isScrap = true;
                }
                productArticles.add(new ProductArticle(productArticleIdx, productInfo.getCategory(),
                        photo.get(0).getPhotoUrl(), productInfo.getCompany(), productInfo.getTitle(),
                        productInfo.getSalePercent(), productInfo.getPrice(), productInfo.getReviewRating(),
                        productInfo.getNumOfReview(), isScrap));
                // 초기화
                isScrap = false;
            }
            return productArticles;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProductRatingsRes getProductRatings(int productArticleIdx) throws BaseException {
        if(checkProductArticleIdx(productArticleIdx) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            GetProductInfoRes getProductInfoRes = storeDao.getProductInfo(productArticleIdx);
            GetProductRatingNumRes getProductRatingNumRes = storeDao.getProductRatingNum(productArticleIdx);
            GetProductRatingsRes getProductRatingsRes = GetProductRatingsRes.builder()
                    .productArticleIdx(productArticleIdx)
                    .reviewNumber(getProductInfoRes.getNumOfReview())
                    .avgRating(getProductInfoRes.getReviewRating())
                    .one(getProductRatingNumRes.getOne())
                    .two(getProductRatingNumRes.getTwo())
                    .three(getProductRatingNumRes.getThree())
                    .four(getProductRatingNumRes.getFour())
                    .five(getProductRatingNumRes.getFive())
                    .build();
            return getProductRatingsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetReviewAllRes> getReviewAll(GetReviewAllReq getReviewAllReq) throws BaseException {
        if(checkUserIdx(getReviewAllReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if(checkProductArticleIdx(getReviewAllReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            boolean clickHelp = false;
            List<GetReviewAllRes> getReviewAllRes = new ArrayList<>();
            int userIdx = getReviewAllReq.getUserIdx();
            int productArticleIdx = getReviewAllReq.getProductArticleIdx();

            // 해당 상품 게시글 인덱스에 해당하는, 리뷰 인덱스 리스트를 구해오자
            List<GetReviewIdx> getReviewIdxes = storeDao.getRivewIdx(productArticleIdx);

            for (GetReviewIdx getReviewIdx : getReviewIdxes) {
                int reviewIdx = getReviewIdx.getReviewIdx();

                ReviewInfo reviewInfos = storeDao.getReviewInfo(reviewIdx);   // reviewInfo
                GetUserInfoRes userInfos = storeDao.getUserInfo(reviewInfos.getUserIdx()); // user
                GetProductInfoRes productInfo = getProductInfo(productArticleIdx); // category ~ numOfReview;

                // 유저가 도움이돼요 버튼 클릭했는지 안했는지
                List<GetUserClickHelpIdx> userClickHelpIdxes = storeDao.getUserClickHelp(userIdx);
                for (GetUserClickHelpIdx userClickHelpIdx : userClickHelpIdxes) {
                    if (reviewIdx == userClickHelpIdx.getUserClickedReviewIdx()) clickHelp = true;
                }

                getReviewAllRes.add(new GetReviewAllRes(reviewIdx, userInfos.getProfileImage(),
                        userInfos.getNickName(), reviewInfos.getRating(), reviewInfos.getCreatedAt(),
                        productInfo.getTitle(), reviewInfos.getPhoto(), reviewInfos.getContents(),
                        reviewInfos.getThisReviewHelp(), clickHelp));
                clickHelp = false;
            }
            return getReviewAllRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * check 관련 함수
     */
    public boolean checkDeletedToken(String JwtToken) throws BaseException {
        try {
            return storeDao.checkDeletedToken(JwtToken);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkUserIdx(int userIdx) throws BaseException {
        try {
            return storeDao.checkUserIdx(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkSearchIdx(DeleteSearchReq deleteSearchReq) throws BaseException {
        try {
            return storeDao.checkSearchIdx(deleteSearchReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkProductArticleIdx(int productArticleIdx) throws BaseException {
        try {
            return storeDao.checkProductArticleIdx(productArticleIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkOptionOneIdx(int optionOneIdx) throws BaseException {
        try {
            return storeDao.checkOptionOneIdx(optionOneIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkOptionTwoIdx(int optionTwoIdx) throws BaseException {
        try {
            return storeDao.checkOptionTwoIdx(optionTwoIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkOptionExtraIdx(int optionExtraIdx) throws BaseException {
        try {
            return storeDao.checkOptionExtraIdx(optionExtraIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkBasketIdx(int basketIdx) throws BaseException {
        try {
            return storeDao.checkBasketIdx(basketIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkBuyProductIdx(int buyProductIdx) throws BaseException {
        try {
            return storeDao.checkBuyProductIdx(buyProductIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
