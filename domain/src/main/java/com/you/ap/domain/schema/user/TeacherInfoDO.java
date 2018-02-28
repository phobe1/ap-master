package com.you.ap.domain.schema.user;


import com.you.ap.domain.schema.BaseDO;

public class TeacherInfoDO extends BaseDO {

    private int id;
    private String collegeName;
    private int cityId;
    private String grade;
    private String profession;
    private float score;
    private int teachNum;
    private String imageUrl;
    private int moneyPerMinute;
    private String created;
    private String modified;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCollegeName() {
        return collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
