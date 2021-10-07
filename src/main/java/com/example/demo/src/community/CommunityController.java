package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.community.model.*;
import com.example.demo.src.store.model.DeleteProductScrapReq;
import com.example.demo.src.store.model.PostSeeProductReq;
import com.example.demo.src.store.model.PostSeeProductRes;
import com.example.demo.utils.JwtService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/communities")
public class CommunityController {

    @Autowired
    private final CommunityProvider communityProvider;
    @Autowired
    private final CommunityService communityService;
    @Autowired
    private final JwtService jwtService;

    // [41]
    String[] sizeList = {"10평 미만", "10평대", "20평대", "30평대", "40평대", "50평 이상"};
    String[] styleList = {"모던 스타일", "북유럽 스타일", "빈티지 스타일", "내추럴 스타일", "프로방스&로맨틱 스타일", "클래식&앤틱 스타일", "한국&아시아 스타일", "유니크 스타일"};
    String[] housingTypeList = {"원룸%오피스텔", "아파트", "빌라&연립", "단독주택", "사무공간", "상업공간", "기타"};
    String[] kindsOfArticleList = {"PHOTO", "PHOTOS", "VIDEO"};

    // [42]
    String[] spaceTypeList = {"원룸", "거실", "침실", "주방"};

    /**
     * [38]. 하단 메뉴 "홈"탭의, 상단 메뉴 "사진"탭에 해당하는 게시글에 대한 모든 정보(기본 정렬은 최신순)
     *
     * @param userIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/photo-tab/article/all")
    public BaseResponse<List<GetArticleAll>> getArticleAll(
            @RequestParam(required = false) Integer userIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (communityProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetArticleAll> getArticleAlls = communityProvider.getArticleAll(userIdx);
            return new BaseResponse<>(getArticleAlls);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [39]. 특정 게시글을 클릭하고 들어갔을 때, 해당 게시글에 내의 모든 정보(댓글 제외)
     *
     * @param userIdx
     * @param articleIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/photo-tab/article/detail/all")
    public BaseResponse<GetArticleDetailAll> getArticleDetailAll(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer articleIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (articleIdx == null) {
                return new BaseResponse<>(EMPTY_ARTICLE_IDX);
            }
            GetArticleDetailAllReq getArticleDetailAllReq = new GetArticleDetailAllReq(userIdx, articleIdx);
            if (communityProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetArticleDetailAll getArticleAlls = communityProvider.getArticleDetailAll(getArticleDetailAllReq);
            return new BaseResponse<>(getArticleAlls);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [40]. 특정 게시글 안에 있는, 특정 단일의 미디어(예를들어 하나의 사진, 동영상)에 대한 모든 정보
     *
     * @param userIdx
     * @param articleMediaIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/photo-tab/article/detail")
    public BaseResponse<MediaDetailInfo> getMediaDetailInfo(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer articleMediaIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (articleMediaIdx == null) {
                return new BaseResponse<>(EMPTY_ARTICLE_MEDIA_IDX);
            }
            MediaDetailInfoReq mediaDetailInfoReq = new MediaDetailInfoReq(userIdx, articleMediaIdx);
            if (communityProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            MediaDetailInfo mediaDetailInfo = communityProvider.getMediaDetailInfo(mediaDetailInfoReq);
            return new BaseResponse<>(mediaDetailInfo);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [41]. 게시글의 정보 등록(평수, 주거형태, 스타일), (게시글의 성격 : PHOTO, PHOTOS, VIDEO)
     *
     * @param postArticleKindReq
     * @return
     */
    @ResponseBody
    @PostMapping("/plus/article/kind")
    public BaseResponse<PostArticleKindRes> postArticleKind(
            @RequestBody PostArticleKindReq postArticleKindReq) {
        try {
            if (postArticleKindReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (postArticleKindReq.getKindsOfArticle() == null) {
                return new BaseResponse<>(EMPTY_KIND_OF_ARTICLE);
            }

            if (postArticleKindReq.getSize() != null &&
                    Arrays.asList(sizeList).contains(postArticleKindReq.getSize()) == false) {
                return new BaseResponse<>(INVALID_SIZE);
            }
            if (postArticleKindReq.getStyle() != null &&
                    Arrays.asList(styleList).contains(postArticleKindReq.getStyle()) == false) {
                return new BaseResponse<>(INVALID_STYLE);
            }
            if (postArticleKindReq.getHousingType() != null &&
                    Arrays.asList(housingTypeList).contains(postArticleKindReq.getHousingType()) == false) {
                return new BaseResponse<>(INVALID_HOUSING_TYPE);
            }
            if (Arrays.asList(kindsOfArticleList).contains(postArticleKindReq.getKindsOfArticle()) == false) {
                return new BaseResponse<>(INVALID_KINDS_OF_ARTICLE);
            }

            if (communityProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (postArticleKindReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostArticleKindRes postSeeProductRes = communityService.postArticleKind(postArticleKindReq);
            return new BaseResponse<>(postSeeProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [42]. (1).미디어(사진 또는 동영상), (2).설명, (3).공간 종류, (4)해시태그 등록
     * @param postArticleMediaReq
     * @return
     */
    @ResponseBody
    @PostMapping("/plus/article/media")
    public BaseResponse<PostArticleMediaRes> postArticleMedia(
            @RequestBody PostArticleMediaReq postArticleMediaReq) {
        try {
            if(postArticleMediaReq.getArticleIdx() == null) {
                return new BaseResponse<>(EMPTY_ARTICLE_IDX);
            }
            if(postArticleMediaReq.getMediaUrl() == null) {
                return new BaseResponse<>(EMPTY_MEDIA_URL);
            }
            if(postArticleMediaReq.getSpaceType() == null) {
                return new BaseResponse<>(EMPTY_SPACE_TYPE);
            }

            if (Arrays.asList(spaceTypeList).contains(postArticleMediaReq.getSpaceType()) == false) {
                return new BaseResponse<>(INVALID_SPACE_TYPE);
            }
            PostArticleMediaRes postArticleMediaRes = communityService.postArticleMedia(postArticleMediaReq);
            return new BaseResponse<>(postArticleMediaRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [43]. 상품 태그 등록(상품을 식별할 수 있는, productArticleIdx를 넘겨줘야함.)
     * @param postArticleProductTagReq
     * @return
     */
    @ResponseBody
    @PostMapping("/plus/article/product-tag")
    public BaseResponse<PostArticleProductTagRes> postArticleProductTag(
            @RequestBody PostArticleProductTagReq postArticleProductTagReq) {
        try {
            if(postArticleProductTagReq.getArticleMediaIdx() == null) {
                return new BaseResponse<>(EMPTY_ARTICLE_MEDIA_IDX);
            }
            if(postArticleProductTagReq.getProductArticleIdx() == null) {
                return new BaseResponse<>(EMPTY_PRODUCT_ARTICLE_IDX);
            }

            Integer xAxis = postArticleProductTagReq.getXAxis();
            Integer yAxis = postArticleProductTagReq.getYAxis();
            if(xAxis != null && xAxis >10 || xAxis < 0) {
                return new BaseResponse<>(INVALID_XAXIS);
            }
            if(yAxis != null && yAxis >10 || yAxis < 0) {
                return new BaseResponse<>(INVALID_YAXIS);
            }

            PostArticleProductTagRes postArticleProductTagRes =
                    communityService.postArticleProductTag(postArticleProductTagReq);
            return new BaseResponse<>(postArticleProductTagRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [44]. /communities/photo-tab/article
     * @param userIdx
     * @param articleIdx
     * @return
     */
    @ResponseBody
    @DeleteMapping("/photo-tab/article")
    public BaseResponse<String> deleteArticle(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer articleIdx) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(articleIdx == null) {
                return  new BaseResponse<>(EMPTY_ARTICLE_IDX);
            }
            if (communityProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            DeleteArticleReq deleteArticleReq = new DeleteArticleReq(userIdx, articleIdx);
            communityService.deleteArticle(deleteArticleReq);
            String res = "Article Deleted";
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [45]. 특정 유저가, 특정 게시글의 댓글 창 조회
     * @param userIdx
     * @param articleIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/photo-tab/article/comment")
    public BaseResponse<List<GetArticleCommentRes>> getArticleComment(
            @RequestParam(required = false) Integer userIdx,
            @RequestParam(required = false) Integer articleIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if (articleIdx == null) {
                return new BaseResponse<>(EMPTY_ARTICLE_IDX);
            }

            if (communityProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetArticleCommentReq getArticleCommentReq = new GetArticleCommentReq(userIdx, articleIdx);
            List<GetArticleCommentRes> getArticleCommentRes = communityProvider.getArticleComment(getArticleCommentReq);
            return new BaseResponse<>(getArticleCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
