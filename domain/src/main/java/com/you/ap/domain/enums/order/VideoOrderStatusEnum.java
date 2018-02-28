package com.you.ap.domain.enums.order;

public enum VideoOrderStatusEnum {

    NO_COMMENT(0,"已评价"),
    FINISH_COMMENT(1,"未评价");

    private final int key;
    private final String value;

    VideoOrderStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
