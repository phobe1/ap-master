package com.you.ap.domain.vo.order;

import com.you.ap.domain.model.book.LocationModel;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.schema.order.BookOrderDO;
import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.schema.user.StudentInfoDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.book.LocationVO;
import com.you.ap.domain.vo.teachcourse.CourseVO;
import com.you.ap.domain.vo.user.TeacherInfoVO;
import com.you.ap.domain.vo.user.UserOnlineInfoVO;

public class BookOrderVO {
    private TeacherInfoVO teacherInfoVO;
    private int bookOrderId;
    private float money;
    private int status;
    private int bookDay;
    private String hourBoxs;
    private int payType;
    private String payTime;
    private LocationVO locationVO;
    private String created;
    private String modified;
    private CourseVO courseVO;
    private UserOnlineInfoVO userOnlineInfoVO;
    private StudentInfoDO studentInfoDO;



    public BookOrderVO(){}

    public BookOrderVO(BookOrderDO bookOrderDO){
        if (bookOrderDO == null){
            return;
        }
        this.payType = bookOrderDO.getPayType();
        this.bookOrderId =bookOrderDO.getId();
        this.bookDay =bookOrderDO.getBookDay();
        this.hourBoxs=bookOrderDO.getHourBoxs();
        this.money= bookOrderDO.getMoney();
        this.status=bookOrderDO.getStatus();
        this.created=bookOrderDO.getCreated();
        this.payTime =bookOrderDO.getPayTime();
    }

    public BookOrderVO of(TeacherInfoDO teacherInfoDO){
        if (teacherInfoDO == null){
            return this;
        }
        this.teacherInfoVO = new TeacherInfoVO(teacherInfoDO);
        return this;
    }

    public BookOrderVO of(StudentInfoDO studentInfoDO){
        if(studentInfoDO==null){
            return this;
        }
        this.studentInfoDO=studentInfoDO;
        return this;
    }

    public BookOrderVO of(CourseDO courseDO){
        if (courseDO == null){
            return this;
        }
        this.courseVO = new CourseVO(courseDO);
        return this;
    }

    public BookOrderVO of(LocationModel locationModel){
        if (locationModel == null){
            return this;
        }
        this.locationVO = new LocationVO(locationModel);
        return this;
    }

    public BookOrderVO of(UserOnlineInfoDO userOnlineInfoDO){
        if (userOnlineInfoDO == null){
            return this;
        }
        this.userOnlineInfoVO = new UserOnlineInfoVO(userOnlineInfoDO);
        return this;
    }



    public TeacherInfoVO getTeacherInfoVO() {
        return teacherInfoVO;
    }

    public void setTeacherInfoVO(TeacherInfoVO teacherInfoVO) {
        this.teacherInfoVO = teacherInfoVO;
    }

    public int getBookOrderId() {
        return bookOrderId;
    }

    public void setBookOrderId(int bookOrderId) {
        this.bookOrderId = bookOrderId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public CourseVO getCourseVO() {
        return courseVO;
    }

    public void setCourseVO(CourseVO courseVO) {
        this.courseVO = courseVO;
    }

    public int getBookDay() {
        return bookDay;
    }

    public void setBookDay(int bookDay) {
        this.bookDay = bookDay;
    }

    public LocationVO getLocationVO() {
        return locationVO;
    }

    public void setLocationVO(LocationVO locationVO) {
        this.locationVO = locationVO;
    }

    public String getHourBoxs() {
        return hourBoxs;
    }

    public void setHourBoxs(String hourBoxs) {
        this.hourBoxs = hourBoxs;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public UserOnlineInfoVO getUserOnlineInfoVO() {
        return userOnlineInfoVO;
    }

    public void setUserOnlineInfoVO(UserOnlineInfoVO userOnlineInfoVO) {
        this.userOnlineInfoVO = userOnlineInfoVO;
    }

    public StudentInfoDO getStudentInfoDO() {
        return studentInfoDO;
    }

    public void setStudentInfoDO(StudentInfoDO studentInfoDO) {
        this.studentInfoDO = studentInfoDO;
    }
}
