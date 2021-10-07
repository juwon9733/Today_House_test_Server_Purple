package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.S3.FileUploadService;
import com.example.demo.src.store.model.*;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class StoreService {

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreDao storeDao;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final FileUploadService fileUploadService;

    public PostStoreSearchHistoryRes postStoreSearchHistory(PostStoreSearchHistoryReq postStoreSearchHistoryReq) throws BaseException {
        if (storeProvider.checkUserIdx(postStoreSearchHistoryReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            PostStoreSearchHistoryRes postStoreSearchHistoryRes = new PostStoreSearchHistoryRes(storeDao.postStoreSearchHistory(postStoreSearchHistoryReq));
            return postStoreSearchHistoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteSearch(DeleteSearchReq deleteSearchReq) throws BaseException {
        if (storeProvider.checkSearchIdx(deleteSearchReq) == false) {
            throw new BaseException(SEARCH_IDX_NOT_EXISTS);
        }
        try {
            storeDao.deleteSearch(deleteSearchReq.getSearchIdx());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteSearchAll(int userIdx) throws BaseException {
        if (storeProvider.checkUserIdx(userIdx) == false) {
            throw new BaseException(SEARCH_IDX_NOT_EXISTS);
        }
        try {
            storeDao.deleteSearchAll(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostShoppingBasketRes postShoppingBasket(PostShoppingBasketReq postShoppingBasketReq) throws BaseException {
        if (storeProvider.checkUserIdx(postShoppingBasketReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if (storeProvider.checkProductArticleIdx(postShoppingBasketReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        if (storeProvider.checkOptionOneIdx(postShoppingBasketReq.getOptionOneIdx()) == false) {
            throw new BaseException(OPTION_ONE_IDX_NOT_EXISTS);
        }
        if (postShoppingBasketReq.getOptionTwoIdx() != null && storeProvider.checkOptionTwoIdx(postShoppingBasketReq.getOptionTwoIdx())== false) {
            throw new BaseException(OPTION_TWO_IDX_NOT_EXISTS);
        }
        if (postShoppingBasketReq.getOptionExtraIdx() != null && storeProvider.checkOptionExtraIdx(postShoppingBasketReq.getOptionExtraIdx()) == false) {
            throw new BaseException(OPTION_EXTRA_IDX_NOT_EXISTS);
        }
        try {
            PostShoppingBasketRes postShoppingBasketRes = new PostShoppingBasketRes(storeDao.postShoppingBasket(postShoppingBasketReq));
            return postShoppingBasketRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteBasket(int basketIdx) throws BaseException {
        if(storeProvider.checkBasketIdx(basketIdx) == false) {
            throw new BaseException(BASKET_IDX_NOT_EXISTS);
        }
        try {
            storeDao.deleteBasket(basketIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostBuyProductRes postBuyProductAndOrderInfo(PostBuyProductReq postBuyProductReq) throws BaseException {
        if(storeProvider.checkUserIdx(postBuyProductReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if(storeProvider.checkProductArticleIdx(postBuyProductReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        if(storeProvider.checkOptionOneIdx(postBuyProductReq.getOptionOneIdx()) == false) {
            throw new BaseException(OPTION_ONE_IDX_NOT_EXISTS);
        }
        if(postBuyProductReq.getOptionTwoIdx() != null &&
                storeProvider.checkOptionTwoIdx(postBuyProductReq.getOptionTwoIdx()) == false) {
            throw new BaseException(OPTION_TWO_IDX_NOT_EXISTS);
        }
        if(postBuyProductReq.getOptionExtraIdx() != null &&
                storeProvider.checkOptionExtraIdx(postBuyProductReq.getOptionExtraIdx()) == false) {
            throw new BaseException(OPTION_EXTRA_IDX_NOT_EXISTS);
        }
        if(storeProvider.checkBuyProductIdx(postBuyProductReq.getBuyProductIdx()) == false) {
            throw new BaseException(BUY_PRODUCT_IDX_NOT_EXISTS);
        }
        try {
            int buyProductIdx = storeDao.postBuyProduct(postBuyProductReq);
            postBuyProductReq.setBuyProductIdx(buyProductIdx);
            int buyOrderInfoIdx = storeDao.postOrderInfo(postBuyProductReq);
            PostBuyProductRes postBuyProductRes = new PostBuyProductRes(buyProductIdx, buyOrderInfoIdx);
            // productArticle테이블의, productArticleIdx에 해당하는 상품의 buyNumber를 1올려준다.
            storeDao.patchAddbuyNumber(postBuyProductReq.getBuyProductIdx());
            return postBuyProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteBuy(int buyProductIdx) throws BaseException {
        if(storeProvider.checkBuyProductIdx(buyProductIdx) == false) {
            throw new BaseException(BUY_PRODUCT_IDX_NOT_EXISTS);
        }
        try {
            storeDao.deleteBuy(buyProductIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteProductScrap(DeleteProductScrapReq deleteProductScrapReq) throws BaseException {
        if(storeProvider.checkUserIdx(deleteProductScrapReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if(storeProvider.checkProductArticleIdx(deleteProductScrapReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            storeDao.deleteProductScrap(deleteProductScrapReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostSeeProductRes postSeeProduct(PostSeeProductReq postSeeProductReq) throws BaseException {
        if(storeProvider.checkUserIdx(postSeeProductReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if(storeProvider.checkProductArticleIdx(postSeeProductReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            // 만약에 이미 봤던 상품이라면, 이전의 상품을 지우고
            if (storeDao.checkAlreadySee(postSeeProductReq) == true) {
                storeDao.deleteSeeProduct(postSeeProductReq);
            }
            // 새롭게 인서트 해준다.
            PostSeeProductRes postBuyProductRes = new PostSeeProductRes(storeDao.postSeeProduct(postSeeProductReq));
            return postBuyProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
