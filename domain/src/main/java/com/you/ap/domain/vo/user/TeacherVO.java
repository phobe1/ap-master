package com.you.ap.domain.vo.user;

import com.you.ap.domain.pojo.Teacher;

import java.sql.Date;

/**
 * Created by liangjielin on 2018/1/5.
 */
public class TeacherVO {




    private int id;
    private String name;
    private String loginname;
    private String phone;
    private String imageurl;


    public TeacherVO(Teacher teacher) {
        if(teacher==null)return;
        this.id=teacher.getId();
        this.name=teacher.getName();
        this.imageurl=teacher.getImageurl();
        this.phone=teacher.getPhone();
        this.loginname=teacher.getLoginname();

    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


}
