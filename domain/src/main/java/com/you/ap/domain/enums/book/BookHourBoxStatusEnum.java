package com.you.ap.domain.enums.book;

import com.google.common.collect.Maps;

import java.util.Map;

public enum BookHourBoxStatusEnum {

    NO_BOOK(0,"禁止预约"),NORMAL_BOOK(1,"可预约"),HAS_BOOK(2,"已经有约");

    public final int key;
    public final String desc;

    public static final Map<Integer, BookHourBoxStatusEnum> all = Maps.newHashMap();
    static{
        all.put(0, NO_BOOK);
        all.put(1, NORMAL_BOOK);
        all.put(2, HAS_BOOK);
    }
    BookHourBoxStatusEnum(int key, String desc){
        this.key=key;
        this.desc=desc;
    }
    public int getKey() {
        return key;
    }
    public String getDesc() {
        return desc;
    }

    public static BookHourBoxStatusEnum valueOf(int key){
        return all.get(key);
    }
}
