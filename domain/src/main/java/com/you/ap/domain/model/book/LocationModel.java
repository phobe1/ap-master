package com.you.ap.domain.model.book;

import com.you.ap.domain.schema.book.LocationDO;

import java.util.Objects;

public class LocationModel {

    private int id;
    private String name;
    private LocationModel parent;

    public LocationModel(){}

    public LocationModel(LocationDO locationDO){
        this.id=locationDO.getId();
        this.name=locationDO.getLocationName();
    }

    public LocationModel addParent(LocationDO locationDO){
        this.parent = new LocationModel(locationDO);
        return this.parent;
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

    public LocationModel getParent() {
        return parent;
    }

    public void setParent(LocationModel parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationModel that = (LocationModel) o;
        return
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
