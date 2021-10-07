package com.example.demo.src.email;

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
public class EmailProvider {

    @Autowired
    private final EmailDao emailDao;

    public boolean checkCodeByAuthIdx(int authCodeIdx, String code) throws BaseException{
        if (checkCodeAuthIdx(authCodeIdx) == false) {
            throw new BaseException(CODE_AUTH_IDX_NOT_EXISTS);
        }
        try{
            return emailDao.checkCodeByAuthIdx(authCodeIdx, code);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkCodeAuthIdx(int authCodeIdx) throws BaseException{
        try{
            return emailDao.checkCodeAuthIdx(authCodeIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
