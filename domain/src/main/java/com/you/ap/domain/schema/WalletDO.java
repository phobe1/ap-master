package com.you.ap.domain.schema;

/**
 * Created by liangjielin on 2017/12/15.
 */
public class WalletDO {
    private int id;
    private int userId;
    private int type;
    private int status;
    private float money;
    private int coin;
    private String created;
    private String modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
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

    @Override
    public String toString() {
        return "WalletDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", status=" + status +
                ", money=" + money +
                ", coin=" + coin +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                '}';
    }
}
