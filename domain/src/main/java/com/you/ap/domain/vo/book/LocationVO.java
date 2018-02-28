package com.you.ap.domain.vo.book;

import com.you.ap.domain.model.book.LocationModel;
import com.you.ap.domain.schema.book.LocationDO;

public class LocationVO {

    private int id;
    private String name;

    private LocationVO parent;

    public LocationVO(){}

    public LocationVO(LocationModel locationModel){
        if (locationModel==null){
            return;
        }
        this.id = locationModel.getId();
        this.name=locationModel.getName();
        if (locationModel.getParent()!=null){
            this.parent = new LocationVO(locationModel.getParent());
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationVO getParent() {
        return parent;
    }

    public void setParent(LocationVO parent) {
        this.parent = parent;
    }
}
