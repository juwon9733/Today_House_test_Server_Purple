package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.store.model.GetStoreSearchHistoryRes;
import com.example.demo.src.store.model.*;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;
    @Autowired
    private final JwtService jwtService;

    String[] categoryList = {"가구", "패브릭", "조명", "가전", "주방용품", "장식소품", "수납정리", "생활용품"};


    /**
     * [9]. 특정 유저의, 스토어 검색의 최근 검색어 조회
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/search-history")
    public BaseResponse<List<GetStoreSearchHistoryRes>> getStoreSearchHistory(@RequestParam(required = false) Integer userIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetStoreSearchHistoryRes> getStoreSearchHistoryRes = storeProvider.getStoreSearchHistory(userIdx);
            return new BaseResponse<>(getStoreSearchHistoryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [10]. 특정 유저의, 스토어 검색의 검색어 등록
     *
     * @param postStoreSearchHistoryReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("/search-history")
    public BaseResponse<PostStoreSearchHistoryRes> postStoreSearchHistory(@RequestBody PostStoreSearchHistoryReq postStoreSearchHistoryReq) {
        try {
            if (postStoreSearchHistoryReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(postStoreSearchHistoryReq.getSearch() == null) {
                return new BaseResponse<>(EMPTY_SEARCH_KEYWORD);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (postStoreSearchHistoryReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostStoreSearchHistoryRes postStoreSearchHistoryRes = storeService.postStoreSearchHistory(postStoreSearchHistoryReq);
            return new BaseResponse<>(postStoreSearchHistoryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [11].
     *
     * @param userIdx
     * @param searchIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @DeleteMapping("/search-history")
    public BaseResponse<String> deleteSearch(@RequestParam(required = false) Integer userIdx,
                                             @RequestParam(required = false) Integer searchIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (searchIdx == null) {
                return new BaseResponse<>(EMPTY_SEARCH_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            DeleteSearchReq deleteSearchReq = new DeleteSearchReq(userIdx, searchIdx);
            storeService.deleteSearch(deleteSearchReq);
            String res = "Search History Deleted";
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [12]. 특정 유저의, 모든 검색어 기록 삭제
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @DeleteMapping("/search-history/all")
    public BaseResponse<String> deleteSearchAll(@RequestParam(required = false) Integer userIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            storeService.deleteSearchAll(userIdx);
            String res = "Search History All Deleted";
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [13]. 스토어에서의 검색어의 결과에 해당하는, 상품에 대한 모든 정보
     *
     * @param userIdx
     * @param keyword
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/search")
    public BaseResponse<List<ProductArticle>> getProductBySearch(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) String keyword) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (keyword == null) {
                return new BaseResponse<>(EMPTY_SEARCH_KEYWORD);
            }
            GetProductBySearchReq getProductBySearchReq = new GetProductBySearchReq(userIdx, keyword);
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (getProductBySearchReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<ProductArticle> productArticle = storeProvider.getProductBySearch(getProductBySearchReq);
            return new BaseResponse<>(productArticle);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [14]. 스토어홈의 최상단 광고 조회
     *
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/advertise")
    public BaseResponse<List<GetAdvertiseStoreHomeRes>> getAdvertiseStoreHome() {
        try {
            List<GetAdvertiseStoreHomeRes> getAdvertiseStoreHomeRes = storeProvider.getAdvertiseStoreHome();
            return new BaseResponse<>(getAdvertiseStoreHomeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [15]. 특정 상품 게시글 인덱스에 해당하는, 사진 조회
     *
     * @param productArticleIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/photo")
    public BaseResponse<List<GetProductPhotoRes>> getProductPhoto(
            @RequestParam(required = false) Integer productArticleIdx) {
        try {
            if (productArticleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            List<GetProductPhotoRes> getProductPhotoRes = storeProvider.getProductPhoto(productArticleIdx);
            return new BaseResponse<>(getProductPhotoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [16]. 특정 상품 게시글 인덱스에 해당하는, 상품에 대한 정보 조회(회사, 게시글 제목, 할인율, 가격, 리뷰 평점, 리뷰 개수)
     *
     * @param productArticleIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/info")
    public BaseResponse<GetProductInfoRes> getProductInfo(
            @RequestParam(required = false) Integer productArticleIdx) {
        try {
            if (productArticleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            GetProductInfoRes getProductInfoRes = storeProvider.getProductInfo(productArticleIdx);
            return new BaseResponse<>(getProductInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [17]. 특정 상품 게시글 인덱스에 해당하는, 배송 정보 조회
     *
     * @param productArticleIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/shipping")
    public BaseResponse<List<GetProductArticleShippingRes>> getProductArticleShipping(
            @RequestParam(required = false) Integer productArticleIdx) throws BaseException {
        try {
            if (productArticleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            List<GetProductArticleShippingRes> getProductArticleShippingRes = storeProvider.getProductArticleShipping(productArticleIdx);
            return new BaseResponse<>(getProductArticleShippingRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [18]. 특정유저가 장바구니에 담은, 상품 및 상품 상세 옵션들 조회
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/shopping-basket/all")
    public BaseResponse<List<GetShopBasketAllRes>> getUserShoppingBasket(
            @RequestParam(required = false) Integer userIdx) {
        try {
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetShopBasketAllRes> getShopBasketAllRes = storeProvider.getUserShoppingBasket(userIdx);
            return new BaseResponse<>(getShopBasketAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [19]. 특정 유저가 장바구니에, 특정 상품 옵션을 등록
     *
     * @param postShoppingBascketReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("/shopping-basket")
    public BaseResponse<PostShoppingBasketRes> postShoppingBascket(@RequestBody PostShoppingBasketReq postShoppingBasketReq) {
        try {
            if (postShoppingBasketReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (postShoppingBasketReq.getProductArticleIdx() == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            if (postShoppingBasketReq.getOptionOneIdx() == null) {
                return new BaseResponse<>(EMPTY_OPTION_ONE_IDX);
            }
            if (postShoppingBasketReq.getProductNum() == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_NUM);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (postShoppingBasketReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostShoppingBasketRes postShoppingBasketRes = storeService.postShoppingBasket(postShoppingBasketReq);
            return new BaseResponse<>(postShoppingBasketRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [20]. 특정유저가 장바구니에서, 특정 상품 옵션을 삭제
     * @param userIdx
     * @param basketIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @DeleteMapping("/shopping-basket")
    public BaseResponse<String> deleteBasket(@RequestParam(required = false) Integer userIdx, @RequestParam(required = false) Integer basketIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (basketIdx == null) {
                return new BaseResponse<>(EMPTY_BASKET_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            storeService.deleteBasket(basketIdx);
            String res = "Basket Deleted";
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [21]. 특정 유저가 특정 상품 게시글의, 특정 상품 옵션을 구매하는 정보를 등록
     *
     * @param postBuyProductReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("/buy-product")
    public BaseResponse<PostBuyProductRes> postBuyProductAndOrderInfo(@RequestBody PostBuyProductReq postBuyProductReq) {
        try {
            if(postBuyProductReq.getUserIdx()  == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(postBuyProductReq.getProductArticleIdx() == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            if(postBuyProductReq.getOptionOneIdx() == null) {
                return new BaseResponse<>(EMPTY_OPTION_ONE_IDX);
            }
            if(postBuyProductReq.getProductNum() == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_NUM);
            }
            if(postBuyProductReq.getBuyProductIdx() == null) {
                return new BaseResponse<>(EMPTY_BUY_PRODUCT_IDX);
            }
            if(postBuyProductReq.getOrderName() == null) {
                return new BaseResponse<>(EMPTY_ORDER_NAME);
            }
            if(postBuyProductReq.getOrderEmail() == null) {
                return new BaseResponse<>(EMPTY_ORDER_EMAIL);
            }
            if(postBuyProductReq.getOrderPhone() == null) {
                return new BaseResponse<>(EMPTY_ORDER_PHONE);
            }
            if(postBuyProductReq.getDeliverName() == null) {
                return new BaseResponse<>(EMPTY_DELIVER_NAME);
            }
            if(postBuyProductReq.getDeliverPhone() == null) {
                return new BaseResponse<>(EMPTY_DELIVER_PHONE);
            }
            if(postBuyProductReq.getDeliveredAddress() == null) {
                return new BaseResponse<>(EMPTY_DELIVER_ADDRESS);
            }

            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (postBuyProductReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostBuyProductRes postBuyProductRes = storeService.postBuyProductAndOrderInfo(postBuyProductReq);
            return new BaseResponse<>(postBuyProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * [22]. 특정 유저가, 특정 구매를 취소
     *
     * @param userIdx
     * @param buyIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @DeleteMapping("/buy-product")
    public BaseResponse<String> deleteBuy(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer buyProductIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(buyProductIdx == null) {
                return new BaseResponse<>(EMPTY_BUY_PRODUCT_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            storeService.deleteBuy(buyProductIdx);
            String res = "Buy Deleted";
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [23]. 특정유저가, 특정 상품 게시글을 클릭하여 들어갔을때, 상품에 대한 모든 정보
     *
     * @param getProductDetailAllReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/detail/all")
    public BaseResponse<GetProductDetailAllRes> getProductDetailAll(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer productArticleIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(productArticleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            GetProductDetailAllReq getProductDetailAllReq = new GetProductDetailAllReq(userIdx, productArticleIdx);
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (getProductDetailAllReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetProductDetailAllRes getProductDetailAllRes = storeProvider.getProductDetailAll(getProductDetailAllReq);
            return new BaseResponse<>(getProductDetailAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [24]. 특정 상품 게시글 인덱스를, 클릭하여 들어갔을때, 상품정보에 해당하는 정보 조회(정보는 사진으로 통일하였음)
     *
     * @param productArticleIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/detail-info")
    public BaseResponse<List<GetProductArticleDetailInfoRes>> getProductArticleDetailInfo(
            @RequestParam(required = false) Integer productArticleIdx) {
        try {
            if(productArticleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            List<GetProductArticleDetailInfoRes> getProductArticleDetailInfoRes = storeProvider.getProductArticleDetailInfo(productArticleIdx);
            return new BaseResponse<>(getProductArticleDetailInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [25]. 특정 상품 게시글 인덱스의, 구매하기 버튼을 눌렀을때,나타나는 옵션1, 옵션2, 추가옵션에 대한 정보 조회
     *
     * @param productArticleIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/product-option")
    public BaseResponse<List<GetProductArticleOptionRes>> getProductArticleOption(
            @RequestParam(required = false) Integer productArticleIdx) {
        try {
            if(productArticleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            List<GetProductArticleOptionOneRes> getProductArticleOptionOneRes = storeProvider.getProductArticleOptionOne(productArticleIdx);
            List<GetProductArticleOptionTwoRes> getProductArticleOptionTwoRes = storeProvider.getProductArticleOptionTwo(productArticleIdx);
            List<GetProductArticleOptionExtraRes> getProductArticleOptionExtraRes = storeProvider.getProductArticleOptionExtra(productArticleIdx);
            List<GetProductArticleOptionRes> getProductArticleOptionRes = Collections.singletonList(
                    new GetProductArticleOptionRes(getProductArticleOptionOneRes, getProductArticleOptionTwoRes, getProductArticleOptionExtraRes));
            return new BaseResponse<>(getProductArticleOptionRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [26]. 오늘의 딜에 해당하는, 상품 게시글 인덱스 조회
     *
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/today-deal")
    public BaseResponse<List<GetProductArticleTodayDealRes>> getProductArticleTodayDeal() {
        try {
            List<GetProductArticleTodayDealRes> getProductArticleTodayDealRes = storeProvider.getProductArticleTodayDeal();
            return new BaseResponse<>(getProductArticleTodayDealRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [27]. 오늘의 딜에 해당하는, 상품에 대한 모든 정보
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/today-deal/all")
    public BaseResponse<List<GetProductArticleTodayDealAllRes>> getProductArticleTodayDealAll(
            @RequestParam(required = false) Integer userIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetProductArticleTodayDealAllRes> getProductArticleTodayDealAllRes = storeProvider.getProductArticleTodayDealAll(userIdx);
            return new BaseResponse<>(getProductArticleTodayDealAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [28]. 카테고리에 해당하는, 상품에 대한 모든 정보 (카테고리의 경우 예를들어, "장식/소품"인 경우에는 슬래시를 빼고 "장식소품"으로 입력할 것)
     *
     * @param userIdx
     * @param category
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/category/all")
    public BaseResponse<List<ProductArticle>> getProductByCategoryAll(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) String category) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(category == null) {
                return new BaseResponse<>(EMPTY_CATEGORY);
            }
            GetProductByCategoryAllReq getProductByCategoryAllReq = new GetProductByCategoryAllReq(userIdx, category);
            if (Arrays.asList(categoryList).contains(getProductByCategoryAllReq.getCategory()) == false) {
                return new BaseResponse<>(INVALID_PRODUCT_CATEGORY);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (getProductByCategoryAllReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<ProductArticle> productArticle = storeProvider.getProductByCategoryAll(getProductByCategoryAllReq);
            return new BaseResponse<>(productArticle);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [29]. 특정 유저가 스크랩한, 상품 게시글 인덱스 조회
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/scrap-book/products")
    public BaseResponse<List<GetScrapProductsRes>> getScrapProducts(
            @RequestParam(required = false) Integer userIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetScrapProductsRes> getScrapProductsRes = storeProvider.getScrapProducts(userIdx);
            return new BaseResponse<>(getScrapProductsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [30]은 user 도메인에 있음.
     */

    /**
     * [31]. 특정유저가, 특정 상품 게시글에 대한 스크랩을 삭제
     *
     * @param deleteProductScrapReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @DeleteMapping("/scrap-book/products")
    public BaseResponse<String> deleteProductScrap(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer productArticleIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(productArticleIdx == null) {
                return  new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            DeleteProductScrapReq deleteProductScrapReq = new DeleteProductScrapReq(userIdx, productArticleIdx);
            storeService.deleteProductScrap(deleteProductScrapReq);
            String res = "Product Scrap Deleted";
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [32]. 특정유저의 스크랩북에 해당하는, 상품에 대한 모든 정보
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/scrap-book/products/all")
    public BaseResponse<List<GetUserProductScrapAllRes>> getUserProductScrapAll(
            @RequestParam(required = false) Integer userIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetUserProductScrapAllRes> getUserProductScrapAllRes = storeProvider.getUserProductScrapAll(userIdx);
            return new BaseResponse<>(getUserProductScrapAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [33]. 인기 상품에 해당하는(판매량을 기준으로) 상품 게시글 모든 정보, 내림 차순 조회
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/popular/all")
    public BaseResponse<List<ProductArticle>> getPopularProducts(
            @RequestParam(required = false) Integer userIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<ProductArticle> productArticles = storeProvider.getPopularProducts(userIdx);
            return new BaseResponse<>(productArticles);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [34]. 특정 유저가 최근에 본 상품에 해당하는, 상품 게시글에 대한 모든 정보
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/last-see/product/all")
    public BaseResponse<List<ProductArticle>> getLastSeeProduct(
            @RequestParam(required = false) Integer userIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<ProductArticle> productArticle = storeProvider.getLastSeeProduct(userIdx);
            return new BaseResponse<>(productArticle);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [35]. 유저가 최근에 본 상품에 등록하기
     *
     * @param postSeeProductReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("/last-see/product")
    public BaseResponse<PostSeeProductRes> postSeeProduct(
            @RequestBody PostSeeProductReq postSeeProductReq) {
        try {
            if(postSeeProductReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(postSeeProductReq.getProductArticleIdx() == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (postSeeProductReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostSeeProductRes postSeeProductRes = storeService.postSeeProduct(postSeeProductReq);
            return new BaseResponse<>(postSeeProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * [36]. 상품 게시글의 리뷰 평점 및, 리뷰 각 점수 개수
     *
     * @param productArticleIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/reviews/rating")
    public BaseResponse<GetProductRatingsRes> getProductRatings(
            @RequestParam(required = false) Integer productArticleIdx) throws BaseException {
        try {
            if(productArticleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            GetProductRatingsRes getProductRatingsRes = storeProvider.getProductRatings(productArticleIdx);
            return new BaseResponse<>(getProductRatingsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [37]. 상품 게시글을 클릭하고 들어갔을때, 리뷰에 대한 모든 정보
     *
     * @param userIdx
     * @param productAritcleIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @GetMapping("/product-article/reviews")
    public BaseResponse<List<GetReviewAllRes>> getReviewAll(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer productAritcleIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(productAritcleIdx == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }
            GetReviewAllReq getReviewAllReq = new GetReviewAllReq(userIdx, productAritcleIdx);
            if (storeProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetReviewAllRes> getReviewAllRes = storeProvider.getReviewAll(getReviewAllReq);
            return new BaseResponse<>(getReviewAllRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
