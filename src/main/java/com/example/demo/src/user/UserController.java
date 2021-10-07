package com.example.demo.src.user;

import com.example.demo.src.store.model.LogOutReq;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    /**
     * [1]. 이메일로 회원가입
     * @param postCreateUserReq
     * @return
     */
    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<PostCreateUserRes> postCreateUser(@RequestBody PostCreateUserReq postCreateUserReq) {
        try {
            if(postCreateUserReq.getEmail() == null) {
                return new BaseResponse<>(EMPTY_USER_EMAIL);
            }
            if(postCreateUserReq.getPasswd() == null) {
                return new BaseResponse<>(EMPTY_USER_PASSWD);
            }
            if(postCreateUserReq.getNickName() == null) {
                return new BaseResponse<>(EMPTY_USER_NICKNAME);
            }
            if(!isRegexEmail(postCreateUserReq.getEmail())) {
                return new BaseResponse<>(INVALID_USER_EMAIL);
            }
            if(!isRegexPasswd(postCreateUserReq.getPasswd())) {
                return new BaseResponse<>(INVALID_USER_PASSWD);
            }
            else if(postCreateUserReq.getPasswd().length() < 8) {
                return new BaseResponse<>(INVALID_USER_PASSWD);
            }
            PostCreateUserRes postCreateUserRes = userService.postCreateUser(postCreateUserReq);
            return new BaseResponse<>(postCreateUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [2]. 이메일로 로그인
     * @param postLoginReq
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> postLogin(@RequestBody PostLoginReq postLoginReq) {
        try {
            if(postLoginReq.getEmail() == null) {
                return new BaseResponse<>(EMPTY_USER_EMAIL);
            }
            if(postLoginReq.getPasswd() == null) {
                return new BaseResponse<>(EMPTY_USER_PASSWD);
            }
            if(!isRegexEmail(postLoginReq.getEmail())) {
                return new BaseResponse<>(INVALID_USER_EMAIL);
            }
            if(!isRegexPasswd(postLoginReq.getPasswd())) {
                return new BaseResponse<>(INVALID_USER_PASSWD);
            }
            else if(postLoginReq.getPasswd().length() < 8) {
                return new BaseResponse<>(INVALID_USER_PASSWD);
            }
            PostLoginRes postLoginRes = userProvider.postLogin(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [3]. 특정 유저 인덱스에, 해당하는 유저 정보 조회
     * @param userIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/profile")
    public BaseResponse<User> getUsersByUserIdx(@RequestParam(required = false) Integer userIdx) {
        try {
            if (userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            User user = userProvider.getUsersByUserIdx(userIdx);
            return new BaseResponse<>(user);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [4]. 모든 유저들의 닉네임
     * @return
     */
    @ResponseBody
    @GetMapping("/nick-names")
    public BaseResponse<List<GetUserNickNamesRes>> getUserNickNames() {
        try {
            List<GetUserNickNamesRes> getUserNickNamesRes = userProvider.getUserNickNames();
            return new BaseResponse<>(getUserNickNamesRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [5]. 비밀번호 수정
     * @param patchUserPasswdReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("/passwd")
    //Headers에 X-ACCESS-TOKEN을 같이 보내야한다.
    public BaseResponse<String> patchUserPasswd(@RequestBody PatchUserPasswdReq patchUserPasswdReq) {
        try {
            if (patchUserPasswdReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(userProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            if(!isRegexPasswd(patchUserPasswdReq.getPasswd())) {
                return new BaseResponse<>(INVALID_USER_PASSWD);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (patchUserPasswdReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.patchUserPasswd(patchUserPasswdReq);
            String result = "User Passwd Changed";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [6]. 프로필 정보 수정
     * @param patchUserProfileReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PatchMapping("/profile")
    public BaseResponse<String> patchUserProfile(@ModelAttribute PatchUserProfileReq patchUserProfileReq) {
        try {
            if(patchUserProfileReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(userProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (patchUserProfileReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.patchUserProfile(patchUserProfileReq);
            String result = "User Profile Patched";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [6-1]. 프로필 정보 수정
     * @param patchUserProfileReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("/profile/re")
    public BaseResponse<String> patchUserProfileRe(@RequestBody PatchUserProfileReq patchUserProfileReq) {
        try {
            if(patchUserProfileReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(userProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (patchUserProfileReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.patchUserProfile(patchUserProfileReq);
            String result = "User Profile Patched";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * [7]. 로그아웃
     * @return
     */
    @ResponseBody
    @PostMapping("/logout")
    public BaseResponse<PostLogoutRes> logout(@RequestBody LogOutReq logOutReq) {
        try {
            if(logOutReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(userProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (logOutReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            String userJwtToken = jwtService.getJwt();

            logOutReq = new LogOutReq(userIdxByJwt, userJwtToken);
            PostLogoutRes postLogoutRes = userService.logout(logOutReq.getUserJwtToken());
            return new BaseResponse<>(postLogoutRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * [8]. 회원 탈퇴하기
     * @param userIdx
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @DeleteMapping("/withdraw")
    public BaseResponse<String> deleteUser(@RequestParam(required = false) Integer userIdx ) {
        try {
            if(userIdx == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            if(userProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.deleteUser(userIdx);
            String res = "User Deleted";
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * [30]. 스크랩북 등록
     * @param postScrapReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("/scrap-book")
    public BaseResponse<PostScrapRes> postScrap(@RequestBody PostScrapReq postScrapReq) {
        try {
            if(postScrapReq.getUserIdx() == null) {
                return new BaseResponse<>(EMPTY_USER_IDX);
            }
            Integer articleIdx = postScrapReq.getArticleIdx();
            Integer articleMediaIdx = postScrapReq.getArticleMediaIdx();
            Integer productArticleIdx = postScrapReq.getProductArticleIdx();
            if(articleIdx != null && articleMediaIdx != null) {
                return new BaseResponse<>(CHOOSE_RIGHT_OPTION);
            }
            if (articleIdx != null && productArticleIdx != null) {
                return new BaseResponse<>(CHOOSE_RIGHT_OPTION);
            }
            if (articleMediaIdx != null && productArticleIdx != null) {
                return new BaseResponse<>(CHOOSE_RIGHT_OPTION);
            }
            if(userProvider.checkDeletedToken(jwtService.getJwt()) == true) {
                return new BaseResponse<>(DELETED_TOKEN);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            if (postScrapReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostScrapRes postScrapRes = userService.postScrap(postScrapReq);
            return new BaseResponse<>(postScrapRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}
