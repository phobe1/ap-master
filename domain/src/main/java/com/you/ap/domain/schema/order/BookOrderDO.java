package com.you.ap.domain.schema.order;

import com.you.ap.domain.enums.order.OrderStatusEnum;
import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;

public class BookOrderDO {

    private int teacherId;
    private int courseId;
    private int bookDayId;
    private int bookDay;
    private String hourBoxs;
    private int locationId;
    private int pricePerMinute;
    private int id;
    private int studentId;
    private float money;
    private int payType;
    private String payTime;
    private String created;
    private String modified;
    private int status;

    private String finishBookOrderTime;
    public BookOrderDO(){}

    public BookOrderDO(int id,float money){
       this.id = id;
       this.money=money;
    }

    public BookOrderDO(BookForm bookForm, BookDayDetailDO bookDayDetailDO, TeacherInfoDO teacherInfoDO, int studentId){
        setStudentId(studentId);
        this.hourBoxs = bookForm.getHourBoxs();
        this.courseId = bookForm.getCourseId();
        this.pricePerMinute = teacherInfoDO.getMoneyPerMinute();
        this.bookDayId = bookForm.getBookDayId();
        this.bookDay = bookDayDetailDO.getBookDay();
        this.locationId = bookDayDetailDO.getLocationId();
        this.teacherId=bookDayDetailDO.getTeacherId();
        setStatus(OrderStatusEnum.NOT_PAY.getKey());
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getBookDayId() {
        return bookDayId;
    }

    public void setBookDayId(int bookDayId) {
        this.bookDayId = bookDayId;
    }

    public int getBookDay() {
        return bookDay;
    }

    public void setBookDay(int bookDay) {
        this.bookDay = bookDay;
    }

    public String getHourBoxs() {
        return hourBoxs;
    }

    public void setHourBoxs(String hourBoxs) {
        this.hourBoxs = hourBoxs;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(int pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFinishBookOrderTime() {
        return finishBookOrderTime;
    }

    public void setFinishBookOrderTime(String finishBookOrderTime) {
        this.finishBookOrderTime = finishBookOrderTime;
    }
}
