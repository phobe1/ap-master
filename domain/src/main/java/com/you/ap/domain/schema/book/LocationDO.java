package com.you.ap.domain.schema.book;

/**
 * Created by liangjielin on 2017/12/14.
 */
public class LocationDO {
    private int id;
    private int parentId;
    private String locationName;

    public LocationDO() {
    }

    public LocationDO(int id, int parentId, String locationName) {
        this.id = id;
        this.parentId = parentId;
        this.locationName = locationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "LocationDO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
