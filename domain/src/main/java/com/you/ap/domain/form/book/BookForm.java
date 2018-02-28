package com.you.ap.domain.form.book;

import org.apache.commons.lang3.StringUtils;

public class BookForm {
    private Integer teacherId;
    private Integer bookDayId;
    private String hourBoxs;
    private Integer positionId;
    private Integer courseId;

    public boolean validBookParam(){
        if (this.bookDayId==null || StringUtils.isEmpty(hourBoxs) || teacherId==null
                || courseId==null){
            return false;
        }
        return true;
    }

    public boolean validPublishHourBoxsParam(){
        if (this.bookDayId==null || StringUtils.isEmpty(hourBoxs) || positionId==null||  positionId==0 ){
            return false;
        }
        return true;
    }



    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getBookDayId() {
        return bookDayId;
    }

    public void setBookDayId(Integer bookDayId) {
        this.bookDayId = bookDayId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getHourBoxs() {
        return hourBoxs;
    }

    public void setHourBoxs(String hourBoxs) {
        this.hourBoxs = hourBoxs;
    }
}
