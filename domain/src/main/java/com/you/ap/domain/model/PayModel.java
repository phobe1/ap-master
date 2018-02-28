package com.you.ap.domain.model;

import com.you.ap.domain.enums.order.OrderTypeEnum;
import com.you.ap.domain.schema.order.BookOrderDO;

public class PayModel {

    private String studentId;
    private String body;
    private String detail;
    private String attach;
    private String totalFee;
    private String ip;
    private String orderNumber;

    public PayModel(int studentId,int money) {
        this.studentId=studentId+"";
        this.body="充值订单";
        this.detail=OrderTypeEnum.RECHARGE.getKey();
        this.totalFee=money+"";
        this.ip="";
    }

    public PayModel() {

    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "PayModel{" +
                "studentId='" + studentId + '\'' +
                ", body='" + body + '\'' +
                ", detail='" + detail + '\'' +
                ", attach='" + attach + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", ip='" + ip + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
