package com.you.ap.domain.enums.order;

import com.google.common.collect.Maps;

import java.util.Map;

public enum OrderStatusEnum {

	NOT_PAY(0,"未支付"),
	CANCELD(1,"已取消"),
	PAY_FINISH(2,"已支付"),
	ORDER_FINISH(3,"已完成")
	;
	
	private final int key;
    private final String value;

	private static Map<Integer, OrderStatusEnum> all = Maps.newHashMap();

    OrderStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	
	public static OrderStatusEnum valueOf(Integer key){
    	return all.get(key);
	}
    

}
