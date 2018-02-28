package com.you.ap.domain.vo.user;

import com.you.ap.domain.schema.UserOnlineInfoDO;

public class UserOnlineInfoVO {

    private double lat;
    private double lng;
    private int status;
    private String accessTime;
    private int type;

    public UserOnlineInfoVO(){}

    public UserOnlineInfoVO(UserOnlineInfoDO userOnlineInfoDO){
        if(userOnlineInfoDO==null){
            return;
        }
        this.lat = userOnlineInfoDO.getLat();
        this.lng = userOnlineInfoDO.getLng();
        this.status= userOnlineInfoDO.getStatus();
        this.accessTime =userOnlineInfoDO.getAccessTime();
        this.type=userOnlineInfoDO.getType();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
