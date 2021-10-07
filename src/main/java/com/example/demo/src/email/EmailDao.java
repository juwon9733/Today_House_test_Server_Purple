package com.example.demo.src.email;
import com.example.demo.src.email.model.EmailAuthRes;
import com.example.demo.src.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class EmailDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int postCode(String code) {
        String postCodeQuery = "insert into AuthCode (authCode) VALUES (?)";
        Object[] postCodeParams = new Object[]{code};
        this.jdbcTemplate.update(postCodeQuery, postCodeParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    public boolean checkCodeByAuthIdx(int authCodeIdx, String code) {
        String checkCodeByAuthIdxQuery = "select exists(select * from AuthCode where Idx = ? and authCode = ?);";
        int checkCodeByAuthIdxParams1 = authCodeIdx;
        String checkCodeByAuthIdxParams2 = code;
        if (this.jdbcTemplate.queryForObject(checkCodeByAuthIdxQuery, int.class,
                checkCodeByAuthIdxParams1,
                checkCodeByAuthIdxParams2
                ) == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkCodeAuthIdx(int authCodeIdx) {
        String checkCodeByAuthIdxQuery = "select exists(select * from AuthCode where Idx = ?);";
        int checkCodeByAuthIdxParams = authCodeIdx;
        if (this.jdbcTemplate.queryForObject(checkCodeByAuthIdxQuery, int.class, checkCodeByAuthIdxParams) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
