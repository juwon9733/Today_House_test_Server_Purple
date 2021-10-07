package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class UserProvider {

    @Autowired
    private final UserDao userDao;
    @Autowired
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    public PostLoginRes postLogin(PostLoginReq postLoginReq) throws BaseException{
        if(checkEmail(postLoginReq.getEmail()) != true){
            throw new BaseException(USER_EMAIL_NOT_EXISTS);
        }
        User user = getUserByEmail(postLoginReq.getEmail());
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPasswd());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        int userIdx = getUserByEmail(postLoginReq.getEmail()).getUserIdx();
        if(postLoginReq.getPasswd().equals(password)){
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN_BY_PASSWD);
        }
    }
    public User getUserByEmail(String email) throws BaseException {
        try {
            User user = userDao.getUserByEmail(email);
            return user;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public User getUsersByUserIdx(int userIdx) throws BaseException {
        if(checkUserIdx(userIdx) != true){
            throw new BaseException(USER_IDX_NOT_EXISTS);
        }
        try {
            User user = userDao.getUsersByUserIdx(userIdx);
            return user;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetUserNickNamesRes> getUserNickNames() throws BaseException {
        try {
            List<GetUserNickNamesRes> getUserNickNamesRes = userDao.getUserNickNames();
            return getUserNickNamesRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * check 관련 함수 모음
     */
    public boolean checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkNickName(String nickName) throws BaseException{
        try{
            return userDao.checkNickName(nickName);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkUserIdx(int userIdx) throws BaseException{
        try{
            return userDao.checkUserIdx(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkDeletedToken(String JwtToken) throws BaseException {
        try{
            return userDao.checkDeletedToken(JwtToken);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkProductArticleIdx(int productArticleIdx) throws BaseException {
        try {
            return userDao.checkProductArticleIdx(productArticleIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean checkIsAlreadyScrapProduct(PostScrapReq postScrapReq) throws BaseException {
        try {
            return userDao.checkIsAlreadyScrapProduct(postScrapReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
