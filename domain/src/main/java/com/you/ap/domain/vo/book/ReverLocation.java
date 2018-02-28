package com.you.ap.domain.vo.book;

import java.util.ArrayList;
import java.util.List;

public class ReverLocation {


    private int id;
    private String name;

    private ReverLocation[] son;

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

    public ReverLocation[] getSon() {
        return son;
    }

    public void setSon(ReverLocation[] son) {
        this.son = son;
    }
}
