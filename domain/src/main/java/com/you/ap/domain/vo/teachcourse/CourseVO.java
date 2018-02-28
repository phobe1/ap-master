package com.you.ap.domain.vo.teachcourse;

import com.you.ap.domain.schema.teachcourse.CourseDO;

public class CourseVO {
    private int id;
    private String name;

    public CourseVO(){}

    public CourseVO(CourseDO courseDO){
        if (courseDO==null){
            return;
        }
        this.name=courseDO.getName();
        this.id=courseDO.getId();

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


}
