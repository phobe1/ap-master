package com.you.ap.domain.model.book;

import com.you.ap.domain.enums.book.BookHourBoxStatusEnum;

public class BookHourBoxModel {

    private int hourId;
    private int status;

    public BookHourBoxModel(){}

    public BookHourBoxModel(int hourId,BookHourBoxStatusEnum bookHourBoxStatusEnum){
        this.hourId = hourId;
        this.status = bookHourBoxStatusEnum.getKey();
    }

    public int getHourId() {
        return hourId;
    }

    public void setHourId(int hourId) {
        this.hourId = hourId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
