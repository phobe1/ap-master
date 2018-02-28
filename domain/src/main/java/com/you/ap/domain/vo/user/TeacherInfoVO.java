package com.you.ap.domain.vo.user;

import com.you.ap.domain.schema.user.TeacherInfoDO;

public class TeacherInfoVO {

    private int id;
    private String name;

    private String college;

    private String grade;

    private String profession;

    private float score;

    private int teachNum;

    private String imageUrl;

    private int moneyPerMinute;

    private boolean isCollection;


    public TeacherInfoVO(){}

    public TeacherInfoVO(TeacherInfoDO teacherInfoDO){
        if (teacherInfoDO == null){
            return;
        }
        this.id=teacherInfoDO.getId();
        this.name=teacherInfoDO.getName();
        this.college=teacherInfoDO.getCollegeName();
        this.grade=teacherInfoDO.getGrade();
        this.profession = teacherInfoDO.getProfession();
        this.score= teacherInfoDO.getScore();
        this.teachNum =teacherInfoDO.getTeachNum();
        this.moneyPerMinute= teacherInfoDO.getMoneyPerMinute();
        this.imageUrl=teacherInfoDO.getImageUrl();
    }

    public TeacherInfoVO(TeacherInfoDO teacherInfoDO,boolean isCollection){
        if (teacherInfoDO == null){
            return;
        }
        this.id=teacherInfoDO.getId();
        this.name=teacherInfoDO.getName();
        this.college=teacherInfoDO.getCollegeName();
        this.grade=teacherInfoDO.getGrade();
        this.profession = teacherInfoDO.getProfession();
        this.score= teacherInfoDO.getScore();
        this.teachNum =teacherInfoDO.getTeachNum();
        this.moneyPerMinute= teacherInfoDO.getMoneyPerMinute();
        this.imageUrl=teacherInfoDO.getImageUrl();
        this.isCollection = isCollection;
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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getTeachNum() {
        return teachNum;
    }

    public void setTeachNum(int teachNum) {
        this.teachNum = teachNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMoneyPerMinute() {
        return moneyPerMinute;
    }

    public void setMoneyPerMinute(int moneyPerMinute) {
        this.moneyPerMinute = moneyPerMinute;
    }

    public boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }
}
