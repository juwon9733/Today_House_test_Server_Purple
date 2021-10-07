package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int postCreateUser(PostCreateUserReq postCreateUserReq) {
        String postCreateUserQuery = "insert into User (email, passwd, nickName) VALUES (?, ?, ?)";
        Object[] postCreateUserParams = new Object[]{postCreateUserReq.getEmail(), postCreateUserReq.getPasswd(), postCreateUserReq.getNickName()};
        this.jdbcTemplate.update(postCreateUserQuery, postCreateUserParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public User getUserByEmail(String email) {
        String getUserByEmailQuery = "select * from User where email = ?";
        String getUserByEmailParams = email;
        return this.jdbcTemplate.queryForObject(getUserByEmailQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("Idx"),
                        rs.getString("email"),
                        rs.getString("passwd"),
                        rs.getString("nickName"),
                        rs.getString("profileImage"),
                        rs.getString("myUrl"),
                        rs.getString("backImage"),
                        rs.getString("introduction"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("status")),
                getUserByEmailParams);
    }
    public User getUsersByUserIdx(int userIdx) {
        String getUsersByUserIdxQuery = "select * from User where Idx = ?";
        int getUsersByUserIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUsersByUserIdxQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("Idx"),
                        rs.getString("email"),
                        rs.getString("passwd"),
                        rs.getString("nickName"),
                        rs.getString("profileImage"),
                        rs.getString("myUrl"),
                        rs.getString("backImage"),
                        rs.getString("introduction"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("status")),
                getUsersByUserIdxParams);
    }
    public List<GetUserNickNamesRes> getUserNickNames() {
        String getUserNickNamesQuery = "select nickName from User";
        return this.jdbcTemplate.query(getUserNickNamesQuery,
                (rs, rowNum) -> new GetUserNickNamesRes(
                        rs.getString("nickName")
                )
        );
    }
    public int patchUserPasswd(PatchUserPasswdReq modifyUserPasswdReq){
        String modifyUserInfoQuery = "UPDATE User SET passwd = ? WHERE Idx = ? ";
        Object[] modifyUserInfoParams = new Object[]{modifyUserPasswdReq.getPasswd(), modifyUserPasswdReq.getUserIdx()};
        return this.jdbcTemplate.update(modifyUserInfoQuery,modifyUserInfoParams);
    }
    public int patchUserProfile(PatchUserProfileReqTemp patchUserProfileReqTemp){
        String patchUserProfileQuery = "UPDATE User SET profileImage = ?, backImage = ?, nickName = ?, myUrl = ?," +
                " introduction = ?  WHERE Idx = ? ";
        Object[] patchUserProfileParams = new Object[]{patchUserProfileReqTemp.getProfileImage(),
                patchUserProfileReqTemp.getBackImage(), patchUserProfileReqTemp.getNickName(),
                patchUserProfileReqTemp.getMyUrl(), patchUserProfileReqTemp.getIntroduction(),
                patchUserProfileReqTemp.getUserIdx()};
        return this.jdbcTemplate.update(patchUserProfileQuery,patchUserProfileParams);
    }
    public int logout(String userJwtToken) {
        String logoutQuery = "insert into DeletedJwtToken (deletedToken) VALUES (?)";
        Object[] logoutParams = new Object[]{userJwtToken};
        this.jdbcTemplate.update(logoutQuery, logoutParams);
        String lastInserIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdxQuery, int.class);
    }
    public int postScrap(PostScrapReq postScrapReq) {
        String postScrapQuery = "insert into Scrap (userIdx, articleIdx, articleMediaIdx, productArticleIdx) values(?,?,?,?)";
        Object[] postScrapParams = new Object[]{postScrapReq.getUserIdx(), postScrapReq.getArticleIdx(),
                postScrapReq.getArticleMediaIdx(), postScrapReq.getProductArticleIdx()};
        this.jdbcTemplate.update(postScrapQuery, postScrapParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public void deleteUser(int userIdx) {
        String deleteUserQuery = "delete\n" +
                "from User\n" +
                "where Idx = ?;";
        Object[] deleteUserParams = new Object[]{userIdx};
        this.jdbcTemplate.update(deleteUserQuery,deleteUserParams);
    }
    /**
     * check 관련 함수 모음
     */
    public boolean checkEmail(String email) {
        String checkEmailQuery = "select exists(select * from User where email = ?)";
        String checkEmailParams = email;
        if (this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkNickName(String nickName) {
        String checkNickNameQuery = "select exists(select * from User where nickName = ?)";
        String checkNickNameParams = nickName;
        if (this.jdbcTemplate.queryForObject(checkNickNameQuery, int.class, checkNickNameParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkUserIdx(int userIdx) {
        String checkUserIdxQuery = "select exists(select email from User where Idx = ?)";
        int checkUserIdxParams = userIdx;
        if (this.jdbcTemplate.queryForObject(checkUserIdxQuery, int.class, checkUserIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkDeletedToken(String JwtToken) {
        String checkTokenQuery = "select exists(select Idx from DeletedJwtToken where deletedToken = ?)";
        String checkTokenParams = JwtToken;
        if(this.jdbcTemplate.queryForObject(checkTokenQuery,
                int.class,
                checkTokenParams) == 1) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean checkProductArticleIdx(int productArticleIdx) {
        String checkProductArticleIdxQuery = "select exists(select * from ProductArticle where Idx = ?)";
        int checkProductArticleIdxParams = productArticleIdx;
        if (this.jdbcTemplate.queryForObject(checkProductArticleIdxQuery, int.class, checkProductArticleIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkIsAlreadyScrapProduct(PostScrapReq postScrapReq) {
        String checkProductArticleIdxQuery = "select exists(select *\n" +
                "              from Scrap\n" +
                "              where userIdx = ?\n" +
                "                and productArticleIdx = ?);";
        int checkProductArticleIdxParams1 = postScrapReq.getUserIdx();
        int checkProductArticleIdxParams2 = postScrapReq.getProductArticleIdx();
        if (this.jdbcTemplate.queryForObject(checkProductArticleIdxQuery, int.class,
                checkProductArticleIdxParams1,
                checkProductArticleIdxParams2) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
