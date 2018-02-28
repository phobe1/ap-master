package com.you.ap.domain.enums.book;

public enum BookDayStatusFormTeacher {
    NOT_OPEN_BOOK(0,"not open book"), PUBLISH_BOOK(1,"PUBLISH"),HAS_BOOK(2,"HAS BOOK"),FULL_BOOK(3,"FULL BOOK");
    public final int key;
    public final String desc;

    BookDayStatusFormTeacher(int key, String desc){
        this.key=key;
        this.desc=desc;
    }
    public int getKey() {
        return key;
    }
    public String getDesc() {
        return desc;
    }
}
