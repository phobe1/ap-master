package com.you.ap.domain.form.user;

public class UserLoginForm {

    private String loginname;

    private String phone;
    private String code;
    private String password;
    private String openId;
    private double lat;
    private double lng;

    public boolean validPhoneParam(){
        if (null!=phone && code!=null ){
            return true;
        }
        return true;
    }

    public boolean validNormalLoginParam(){
        if ((null==phone && loginname==null)|| password == null ){
            return false;
        }
        return true;
    }

    public boolean validWeChatParam(){
        if (null==code){
            return false;
        }
        return true;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public String getPassword() {
        return password;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "UserLoginForm{" +
                "loginname='" + loginname + '\'' +
                ", phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                ", password='" + password + '\'' +
                ", openId='" + openId + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

