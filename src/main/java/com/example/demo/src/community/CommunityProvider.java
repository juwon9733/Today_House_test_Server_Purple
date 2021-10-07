package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.src.community.model.*;
import com.example.demo.src.store.model.GetStoreSearchHistoryRes;
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
public class CommunityProvider {

    @Autowired
    private final CommunityDao communityDao;


    public List<GetArticleAll> getArticleAll(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            boolean isScrap = false;
            // 반환 값
            List<GetArticleAll> getArticleAlls = new ArrayList<>();

            // 해당 유저가 스크랩한, 게시글의 articleIdx를 가져온다.
            List<GetArticleIdxScraped> getArticleIdxScrapedList = getArticleIdxScraped(userIdx);
            // 모든 Article 인덱스를, 최신순으로 가져온다.
            List<GetAllArticleIdx> getAllArticleIdxes = getAllArticleIdx();
            for (GetAllArticleIdx var : getAllArticleIdxes) {
                int articleIdx = var.getArticleIdx();
                // 해당 articleIdx에 맞는, 정보(평수, 스타일, 주거공간, PHOTO or PHOTOS or VIDEO 인지 등)를 Article에서 가져온다.
                GetArticle getArticles = getArticle(articleIdx);
                // 해당 articleIdx에 맞는, 정보(사진, 내용, 공간타입)을 ArticleInMedia에서 가져온다.
                List<GetArticleInMedia> getArticleInMedias = getArticleInMedia(articleIdx);

                // 해당 유저가 스크랩 했는지 안했는지 비교
                for (GetArticleIdxScraped getArticleIdxScraped : getArticleIdxScrapedList) {
                    if(articleIdx == getArticleIdxScraped.getArticleIdx()) isScrap = true;
                }
                getArticleAlls.add(new GetArticleAll(articleIdx, getArticles.getUserIdx(),
                        getArticleInMedias.get(0).getMediaUrl(), getArticleInMedias.get(0).getContents(),
                        getArticleInMedias.get(0).getSpaceType(),
                        getArticles.getSize(), getArticles.getStyle(), getArticles.getHousingType(),
                        getArticles.getKindsOfArticle(),
                        getArticles.getNumHeart(), getArticles.getNumScrap(), getArticles.getNumComment(),
                        isScrap));
                // scrap 변수 초기화
                isScrap = false;
            }
            return getArticleAlls;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 해당 유저가 스크랩한, 게시글의 articleIdx를 가져온다.
    public List<GetArticleIdxScraped> getArticleIdxScraped(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            List<GetArticleIdxScraped> getArticleIdxScrapedList = communityDao.getArticleIdxScraped(userIdx);
            return getArticleIdxScrapedList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 해당 유저가 좋아요한, 게시글의 articleIdx를 가져온다.
    public List<GetArticleIdxHearted> getArticleIdxHearted(int userIdx) throws BaseException {
        if (checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            List<GetArticleIdxHearted> getArticleIdxHeartedList = communityDao.getArticleIdxHearted(userIdx);
            return getArticleIdxHeartedList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // Article의 모든 인덱스를 가져온다.(기준은, 날짜 최신순)
    public List<GetAllArticleIdx> getAllArticleIdx() throws BaseException {
        try {
            List<GetAllArticleIdx> getAllArticleIdxes = communityDao.getAllArticleIdx();
            return getAllArticleIdxes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // articleIdx에 해당하는, Article을 가져온다.
    public GetArticle getArticle(int articleIdx) throws BaseException {
        if (checkArticleIdx(articleIdx) == false) {
            throw new BaseException(ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            GetArticle getArticles = communityDao.getArticle(articleIdx);
            return getArticles;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // articleIdx에 해당하는, ArticleInMedia를 가져온다.
    public List<GetArticleInMedia> getArticleInMedia(int articleIdx) throws BaseException {
        if (checkArticleIdx(articleIdx) == false) {
            throw new BaseException(ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            List<GetArticleInMedia> getArticleInMedias = communityDao.getArticleInMedia(articleIdx);
            return getArticleInMedias;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetArticleDetailAll getArticleDetailAll(GetArticleDetailAllReq getArticleDetailAllReq) throws BaseException {
        if (checkUserIdx(getArticleDetailAllReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if (checkArticleIdx(getArticleDetailAllReq.getArticleIdx()) == false) {
            throw new BaseException(ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            int userIdx = getArticleDetailAllReq.getUserIdx();
            int articleIdx = getArticleDetailAllReq.getArticleIdx();
            boolean isHeart = false;
            boolean isScrap = false;

            // 해당 게시글 인덱스에 해당하는 정보를 가져온다.
            GetArticle getArticles = getArticle(articleIdx);
            // 게시한 유저의 프로필 정보(프로필 이미지, 닉네임 두개만)를 가져온다.
            UserSimpleInfo userSimpleInfo = getUserSimpleInfo(getArticles.getUserIdx());
            // 접근하는 유저가 좋아요한, 게시글의 articleIdx를 가져온다.
            List<GetArticleIdxHearted> getArticleIdxHeartedList = getArticleIdxHearted(userIdx);
            // 접근하는 유저가 스크랩한, 게시글의 articleIdx를 가져온다.
            List<GetArticleIdxScraped> getArticleIdxScrapedList = getArticleIdxScraped(userIdx);

            // ArticleInMedia에서, articleIdx에 해당하는, articleMediaIdx를 전부 가져온다.
            List<GetArticleInMedia> getArticleInMedias = getArticleInMedia(articleIdx);
            // 중간 반환 값
            List<MediaDetailInfo> mediaDetailInfoList = new ArrayList<>();
            for (GetArticleInMedia getArticleInMedia : getArticleInMedias) {
                MediaDetailInfoReq mediaDetailInfoReq = new MediaDetailInfoReq(userIdx, getArticleInMedia.getArticleMediaIdx());
                mediaDetailInfoList.add(getMediaDetailInfo(mediaDetailInfoReq));
            }
            ////////////////////////////////////////////////////////////////////////////////
            for (GetArticleIdxHearted getArticleIdxHearted : getArticleIdxHeartedList) {
                if(getArticleIdxHearted.getArticleIdx() == articleIdx) isHeart = true;
            }
            for (GetArticleIdxScraped getArticleIdxScraped : getArticleIdxScrapedList) {
                if(getArticleIdxScraped.getArticleIdx() == articleIdx) isScrap = true;
            }
            // 반환 값
            GetArticleDetailAll getArticleAlls = GetArticleDetailAll.builder()
                    .userIdx(getArticles.getUserIdx())
                    .userProfileImage(userSimpleInfo.getUserProfileImage())
                    .userNickName(userSimpleInfo.getUserNickName())
                    .articleCreatedAt(getArticles.getUpdatedAt())
                    .size(getArticles.getSize())
                    .style(getArticles.getStyle())
                    .housingType(getArticles.getHousingType())
                    .kindsOfArticle(getArticles.getKindsOfArticle())
                    .mediaDetailInfoList(mediaDetailInfoList)
                    .numComment(getArticles.getNumHeart())
                    .numScrap(getArticles.getNumScrap())
                    .numComment(getArticles.getNumComment())
                    .numShare(getArticles.getNumShare())
                    .isHeart(isHeart)
                    .isScrap(isScrap)
                    .build();

            return getArticleAlls;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // userIdx에 해당하는 간단한 유저 정보를 가져온다.
    public UserSimpleInfo getUserSimpleInfo(int userIdx) throws BaseException {
        if(checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            UserSimpleInfo userSimpleInfo = communityDao.getUserSimpleInfo(userIdx);
            return userSimpleInfo;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public MediaDetailInfo getMediaDetailInfo(MediaDetailInfoReq mediaDetailInfoReq) throws BaseException {
        if (checkUserIdx(mediaDetailInfoReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if (checkArticleMediaIdx(mediaDetailInfoReq.getArticleMediaIdx()) == false) {
            throw new BaseException(ARTICLE_MEDIA_IDX_NOT_EXISTS);
        }
        try {
            boolean isHeart = false;
            boolean isScrap = false;
            int userIdx = mediaDetailInfoReq.getUserIdx();
            int articleMediaIdx = mediaDetailInfoReq.getArticleMediaIdx();

            // articleMediaIdx에 해당하는 ArticleMedia의 정보를 가져온다.
            GetArticleMediaInfo articleMediaInfo = getArticleMediaInfo(articleMediaIdx);
            // articleMediaIdx에 해당하는 ProductTag의 정보를 가져온다.
            List<GetProductTagInfo> getProductTagInfoList = getProductTagInfo(articleMediaIdx);

            // 접근하는 유저가 해당 articleMediaIdx를 좋아요했는지 확인
            if(checkHeartArticleMediaIdx(userIdx, articleMediaIdx) == true) isHeart = true;
            // 접근하는 유저가 해당 articleMediaIdx를 스크랩했는지 확인
            if(checkScrapArticleMediaIdx(userIdx, articleMediaIdx) == true) isScrap = true;

            // 반환 값
            MediaDetailInfo mediaDetailInfo = MediaDetailInfo.builder()
                    .articleMediaIdx(articleMediaIdx)
                    .mediaUrl(articleMediaInfo.getMediaUrl())
                    .getProductTagInfoList(getProductTagInfoList)
                    .contents(articleMediaInfo.getContents())
                    .hashTag(articleMediaInfo.getHashTag())
                    .numHeart(articleMediaInfo.getNumHeart())
                    .numScrap(articleMediaInfo.getNumScrap())
                    .numComment(articleMediaInfo.getNumComment())
                    .isHeart(isHeart)
                    .isScrap(isScrap)
                    .build();

            return mediaDetailInfo;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetArticleMediaInfo getArticleMediaInfo(int articleMediaIdx) throws BaseException {
        if (checkArticleMediaIdx(articleMediaIdx) == false) {
            throw new BaseException(ARTICLE_MEDIA_IDX_NOT_EXISTS);
        }
        try {
            GetArticleMediaInfo getArticleInMedias = communityDao.getArticleMediaInfo(articleMediaIdx);
            return getArticleInMedias;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetProductTagInfo> getProductTagInfo(int articleMediaIdx) throws BaseException {
        if (checkArticleMediaIdx(articleMediaIdx) == false) {
            throw new BaseException(ARTICLE_MEDIA_IDX_NOT_EXISTS);
        }
        try {
            List<GetProductTagInfo> getProductTagInfoList = communityDao.getProductTagInfo(articleMediaIdx);
            return getProductTagInfoList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetArticleMediaIdx> getArticleMediaIdx(int articleIdx) throws BaseException {
        try {
            List<GetArticleMediaIdx> getArticleMediaIdxList = communityDao.getArticleMediaIdx(articleIdx);
            return getArticleMediaIdxList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetArticleCommentRes> getArticleComment(GetArticleCommentReq getArticleCommentReq) throws BaseException {
        if (checkUserIdx(getArticleCommentReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if (checkArticleIdx(getArticleCommentReq.getArticleIdx()) == false) {
            throw new BaseException(ARTICLE_IDX_NOT_EXISTS);
        }
        try {
            boolean isHeart = false;
            int userIdx = getArticleCommentReq.getUserIdx();
            int articleIdx = getArticleCommentReq.getArticleIdx();
            // 반환값
            List<GetArticleCommentRes> getArticleCommentRes = new ArrayList<>();

            // 특정 articleIdx에 해당하는, comment에 대한 정보 모두 가져오기
            List<GetCommentIdx> getCommentIdxList = getCommentIdx(articleIdx);
            for (GetCommentIdx getCommentIdx : getCommentIdxList) {
                int commentIdx = getCommentIdx.getCommentIdx();

                // 해당 commendIdx에 해당하는, 댓글에 대한 정보 가져오기
                GetCommentContents commentContets =  getCommentContents(commentIdx);
                UserSimpleInfo simpleInfo = getUserSimpleInfo(commentContets.getUserIdx());
                // 접근한 유저 기준, 하트하였는지 체크
                if (checkHeartComment(userIdx, commentIdx) == true) isHeart = true;

                getArticleCommentRes.add(new GetArticleCommentRes(simpleInfo,
                        commentContets.getComment(), commentContets.getUpdatedAt(),
                        commentContets.getNumHeart(), isHeart));
            }
            return getArticleCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetCommentIdx> getCommentIdx(int articleIdx) throws BaseException {
        try {
            List<GetCommentIdx> getCommentIdxes = communityDao.getCommentIdx(articleIdx);
            return getCommentIdxes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetCommentContents getCommentContents(int commentIdx) throws BaseException {
        try {
            GetCommentContents commentContents = communityDao.getCommentContents(commentIdx);
            return commentContents;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * check 관련 함수
     */
    public boolean checkDeletedToken(String JwtToken) throws BaseException {
        try {
            return communityDao.checkDeletedToken(JwtToken);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkUserIdx(int userIdx) throws BaseException {
        try {
            return communityDao.checkUserIdx(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkArticleIdx(int articleIdx) throws BaseException {
        try {
            return communityDao.checkArticleIdx(articleIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkArticleMediaIdx(int articleMediaIdx) throws BaseException {
        try {
            return communityDao.checkArticleMediaIdx(articleMediaIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkProductArticleIdx(int productArticleIdx) throws BaseException {
        try {
            return communityDao.checkProductArticleIdx(productArticleIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkHeartArticleMediaIdx(int userIdx, int articleMediaIdx) throws BaseException {
        try {
            return communityDao.checkHeartArticleMediaIdx(userIdx, articleMediaIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkScrapArticleMediaIdx(int userIdx, int articleMediaIdx) throws BaseException {
        try {
            return communityDao.checkScrapArticleMediaIdx(userIdx, articleMediaIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkArticleIdxAndUserIdx(int userIdx, int articleIdx) throws BaseException {
        try {
            return communityDao.checkArticleIdxAndUserIdx(userIdx, articleIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkHeartComment(int userIdx, int commentIdx) throws BaseException {
        try {
            return communityDao.checkHeartComment(userIdx, commentIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
