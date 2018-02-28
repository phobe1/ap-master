package com.you.ap.domain.schema.user;

/**
 * Created by liangjielin on 2017/12/20.
 */
public class StudentInfoDO {
    int id;
    String name;
    String loginname;
    int grade;
    String phone;
    String school;
    String imageurl;
    int isIdentity;
    int type;
    String identifyUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(int isIdentity) {
        this.isIdentity = isIdentity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdentifyUrl() {
        return identifyUrl;
    }

    public void setIdentifyUrl(String identifyUrl) {
        this.identifyUrl = identifyUrl;
    }

    @Override
    public String toString() {
        return "StudentInfoDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginname='" + loginname + '\'' +
                ", grade=" + grade +
                ", phone='" + phone + '\'' +
                ", school='" + school + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", isIdentity=" + isIdentity +
                ", type=" + type +
                '}';
    }
}
