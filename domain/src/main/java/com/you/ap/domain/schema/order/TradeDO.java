package com.you.ap.domain.schema.order;

/**
 * Created by liangjielin on 2017/12/21.
 */
public class TradeDO {
    int id;
    String orderNumber;
    int studentId;
    int teacherId;
    String note;
    double money;
    int coin;
    int status;
    String created;
    String modified;

    public TradeDO() {
    }

    //充值
    public TradeDO(int studentId, double money, int coin) {
        this.studentId = studentId;
        this.note = "充值";
        this.money = money;
        this.coin = coin;
    }

    //转账
    public TradeDO(int studentId, int teacherId, String note, double money, int coin) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.note = note;
        this.money = money;
        this.coin = coin;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
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

    @Override
    public String toString() {
        return "TradeDO{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", studentId=" + studentId +
                ", teacherId=" + teacherId +
                ", note='" + note + '\'' +
                ", money=" + money +
                ", coin=" + coin +
                ", status=" + status +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                '}';
    }
}
