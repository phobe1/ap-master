package com.you.ap.domain.vo.user;

import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.schema.user.StudentInfoDO;

/**
 * Created by liangjielin on 2018/1/8.
 */
public class StudentLoginVO {

    private String token;
    private Integer userId;
    private UserTypeEnum userType;
    private String phone;


    public StudentLoginVO(TokenModel tokenModel, StudentInfoDO studentInfoDO) {
        this.token = tokenModel.getToken();
        this.userId = tokenModel.getUserId();
        this.userType=tokenModel.getUserType();
        this.phone=studentInfoDO.getPhone();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
