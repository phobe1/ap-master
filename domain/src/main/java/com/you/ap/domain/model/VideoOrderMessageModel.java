package com.you.ap.domain.model;

import com.you.ap.domain.schema.order.VideoOrderDO;
import com.you.ap.domain.schema.user.StudentInfoDO;

import java.util.Date;

public class VideoOrderMessageModel {

    private int id;
    private int teachCourseId;
    private int teacherId;
    private int studentId;
    private Date beginTime;
    private Date endTime;
    private int useMinute;
    private int coin;
    private float money;
    private String created;
    private String modified;
    private StudentInfoDO studentInfoDO;

    public VideoOrderMessageModel(){}

    public VideoOrderMessageModel of(StudentInfoDO studentInfoDO){
        this.studentInfoDO=studentInfoDO;
        return this;
    }

    public VideoOrderMessageModel(VideoOrderDO videoOrderDO,float poi){
        if(videoOrderDO == null){
            return;
        }
        this.id=videoOrderDO.getId();
        this.teachCourseId=videoOrderDO.getTeachCourseId();
        this.studentId=videoOrderDO.getStudentId();
        this.teacherId =videoOrderDO.getTeacherId();
        this.created =videoOrderDO.getCreated();
        this.modified=videoOrderDO.getModified();
        this.beginTime = videoOrderDO.getBeginTime();
        this.endTime=videoOrderDO.getEndTime();
        this.useMinute= videoOrderDO.getUseMinute();

        this.coin=videoOrderDO.getCoin();
        this.money=this.coin*poi;

    }

    public VideoOrderMessageModel(VideoOrderDO videoOrderDO){
        if(videoOrderDO == null){
            return;
        }
        this.id=videoOrderDO.getId();
        this.teachCourseId=videoOrderDO.getTeachCourseId();
        this.teacherId=videoOrderDO.getTeacherId();
        this.studentId=videoOrderDO.getStudentId();
        this.created =videoOrderDO.getCreated();
        this.modified=videoOrderDO.getModified();
        this.beginTime = videoOrderDO.getBeginTime();
        this.endTime=videoOrderDO.getEndTime();
        this.useMinute= videoOrderDO.getUseMinute();
        this.coin=videoOrderDO.getCoin();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getUseMinute() {
        return useMinute;
    }

    public void setUseMinute(int useMinute) {
        this.useMinute = useMinute;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeachCourseId() {
        return teachCourseId;
    }

    public void setTeachCourseId(int teachCourseId) {
        this.teachCourseId = teachCourseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public StudentInfoDO getStudentInfoDO() {
        return studentInfoDO;
    }

    public void setStudentInfoDO(StudentInfoDO studentInfoDO) {
        this.studentInfoDO = studentInfoDO;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
