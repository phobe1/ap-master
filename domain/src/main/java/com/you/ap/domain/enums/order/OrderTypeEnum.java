package com.you.ap.domain.enums.order;

public enum  OrderTypeEnum {

    BOOK_ORDER("book"),RECHARGE("RECHARGE");

    final String key;
    OrderTypeEnum(String key){
        this.key=key;
    }

    public String getKey() {
        return key;
    }
}
