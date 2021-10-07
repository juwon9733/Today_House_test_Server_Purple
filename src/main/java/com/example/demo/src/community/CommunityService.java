package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.src.S3.FileUploadService;
import com.example.demo.src.community.model.*;
import com.example.demo.src.store.model.DeleteProductScrapReq;
import com.example.demo.src.store.model.PostSeeProductReq;
import com.example.demo.src.store.model.PostSeeProductRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class CommunityService {

    @Autowired
    private final CommunityProvider communityProvider;
    @Autowired
    private final CommunityDao communityDao;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final FileUploadService fileUploadService;

    public PostArticleKindRes postArticleKind(PostArticleKindReq postArticleKindReq) throws BaseException {
        if(communityProvider.checkUserIdx(postArticleKindReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            PostArticleKindRes postArticleKindRes = new PostArticleKindRes(communityDao.postArticleKind(postArticleKindReq));
            return postArticleKindRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostArticleMediaRes postArticleMedia(PostArticleMediaReq postArticleMediaReq) throws BaseException {
        if(communityProvider.checkArticleIdx(postArticleMediaReq.getArticleIdx()) == false) {
            throw new BaseException(ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            PostArticleMediaRes postArticleMediaRes = new PostArticleMediaRes(communityDao.postArticleMedia(postArticleMediaReq));
            return postArticleMediaRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostArticleProductTagRes postArticleProductTag(PostArticleProductTagReq postArticleProductTagReq) throws BaseException {
        if(communityProvider.checkArticleMediaIdx(postArticleProductTagReq.getArticleMediaIdx()) == false) {
            throw new BaseException(ARTICLE_IDX_NOT_EXISTS);
        }
        if(communityProvider.checkProductArticleIdx(postArticleProductTagReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            PostArticleProductTagRes postArticleProductTagRes = new PostArticleProductTagRes(communityDao.postArticleProductTag(postArticleProductTagReq));
            return postArticleProductTagRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteArticle(DeleteArticleReq deleteArticleReq) throws BaseException {
        int userIdx = deleteArticleReq.getUserIdx();
        int articleIdx = deleteArticleReq.getArticleIdx();
        if(communityProvider.checkUserIdx(userIdx) ==  false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if(communityProvider.checkArticleIdx(articleIdx) == false) {
            throw new BaseException(ARTICLE_IDX_NOT_EXISTS);
        }
        if(communityProvider.checkArticleIdxAndUserIdx(userIdx, articleIdx) == false) {
            throw new BaseException(ARTICLE_IS_NOT_USERS);
        }
        try {
            // 일단 게시글 삭제
            communityDao.deleteArticle(deleteArticleReq);
            // 게시글에 해당하는 모든 articleMediaIdx 가져오고
            List<GetArticleMediaIdx> getArticleMediaIdxList = communityProvider.getArticleMediaIdx(articleIdx);
            // 게시글에 해당하는 미디어 삭제
            deleteArticleInMedia(articleIdx);
            // 해당 articleMediaIdx에 해당하는 ProductTag상품태그 삭제
            for (GetArticleMediaIdx var : getArticleMediaIdxList) {
                deleteProductTag(var.getArticleMediaIdx());
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteArticleInMedia(int articleIdx) throws BaseException {
        try {
            // ArticleInMedia에서, articleIdx에 해당하는 것 다지우기
            communityDao.deleteArticleInMedia(articleIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteProductTag(int articleMediaIdx) throws BaseException {
        try {
            communityDao.deleteProductTag(articleMediaIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
