package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.S3.FileUploadService;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserDao userDao;
    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final FileUploadService fileUploadService;

    public PostCreateUserRes postCreateUser(PostCreateUserReq postCreateUserReq) throws BaseException {
        if(userProvider.checkEmail(postCreateUserReq.getEmail()) == true) {
            throw new BaseException(DUPLICATED_USER_EMAIL);
        }
        if(userProvider.checkNickName(postCreateUserReq.getNickName()) == true) {
            throw new BaseException(DUPLICATED_USER_NICKNAME);
        }
        try {
            String pwd;
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postCreateUserReq.getPasswd());
            postCreateUserReq.setPasswd(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int userIdx = userDao.postCreateUser(postCreateUserReq);
            String jwt = jwtService.createJwt(userIdx);
            return new PostCreateUserRes(userIdx, jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void patchUserPasswd(PatchUserPasswdReq patchUserPasswdReq) throws BaseException {
        if(userProvider.checkUserIdx(patchUserPasswdReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            String pwd;
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(patchUserPasswdReq.getPasswd());
            // 암호화된 비밀번호로, 비밀번호 변경
            patchUserPasswdReq.setPasswd(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int result = userDao.patchUserPasswd(patchUserPasswdReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_USERS_PASSWD);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void patchUserProfile(PatchUserProfileReq patchUserProfileReq) throws BaseException {
        if(userProvider.checkNickName(patchUserProfileReq.getNickName()) == true) {
            throw new BaseException(DUPLICATED_USER_NICKNAME);
        }
        User user = userProvider.getUsersByUserIdx(patchUserProfileReq.getUserIdx());
        // null이라면 이전 값으로 repalce.
        if(patchUserProfileReq.getBackImage() == null) {
            patchUserProfileReq.setBackImage(user.getBackImage());
        }
        if(patchUserProfileReq.getProfileImage() == null) {
            patchUserProfileReq.setBackImage(user.getProfileImage());
        }
        if(patchUserProfileReq.getNickName() == null) {
            patchUserProfileReq.setNickName(user.getNickName());
        }
        if(patchUserProfileReq.getMyUrl() == null) {
            patchUserProfileReq.setMyUrl(user.getMyUrl());
        }
        if(patchUserProfileReq.getIntroduction() == null) {
            patchUserProfileReq.setIntroduction(user.getIntroduction());
        }
        System.out.println(patchUserProfileReq.getNickName());
        PatchUserProfileReqTemp patchUserProfileReqTemp = PatchUserProfileReqTemp.builder()
                .userIdx(patchUserProfileReq.getUserIdx())
                .backImage(patchUserProfileReq.getBackImage())
                .profileImage(patchUserProfileReq.getProfileImage())
                .nickName(patchUserProfileReq.getNickName())
                .myUrl(patchUserProfileReq.getMyUrl())
                .introduction(patchUserProfileReq.getIntroduction())
                .build();
        try {
            int result = userDao.patchUserProfile(patchUserProfileReqTemp);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_USERS_PROFILE);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostLogoutRes logout(String userJwtToken) throws BaseException {
        try {
            int deletedJwtIdx = userDao.logout(userJwtToken);
            return new PostLogoutRes(deletedJwtIdx, userJwtToken);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostScrapRes postScrap(PostScrapReq postScrapReq) throws BaseException {
        if(userProvider.checkUserIdx(postScrapReq.getUserIdx()) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        if(userProvider.checkProductArticleIdx(postScrapReq.getProductArticleIdx()) == false) {
            throw new BaseException(PRODUCT_ARTICLE_IDX_NOT_EXISTS);
        }
        if(userProvider.checkIsAlreadyScrapProduct(postScrapReq) == true) {
            throw  new BaseException(ALREADY_SCRAPED_PRODUCT);
        }
        try {
            PostScrapRes postScrapRes = new PostScrapRes(userDao.postScrap(postScrapReq));
            return postScrapRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteUser(int userIdx) throws BaseException {
        if (userProvider.checkUserIdx(userIdx) == false) {
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            userDao.deleteUser(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
