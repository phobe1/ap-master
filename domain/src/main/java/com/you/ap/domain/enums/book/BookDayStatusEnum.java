package com.you.ap.domain.enums.book;

import com.google.common.collect.Maps;

import java.util.Map;

public enum BookDayStatusEnum {
	
	NOT_BOOK(0,"禁止预约"),NORMAL_BOOK(1,"正常"),FULL_BOOK(2,"已经约满"),NORMAL_BOOK_OF_OTHER_PLCAE(3,"由于位置不可预约"),
	FULL_BOOK_OTHER_POSITION(4,"其他地点也已经约满");
	
	public final int key;
	public final String desc;
	
	public static final Map<Integer, BookDayStatusEnum> allBookDayStatusEnum =Maps.newHashMap();
	static{
		allBookDayStatusEnum.put(0, NOT_BOOK);
		allBookDayStatusEnum.put(1, NORMAL_BOOK);
		allBookDayStatusEnum.put(2, FULL_BOOK);
		allBookDayStatusEnum.put(3,NORMAL_BOOK_OF_OTHER_PLCAE);
		allBookDayStatusEnum.put(4,FULL_BOOK_OTHER_POSITION);
	}
	BookDayStatusEnum(int key, String desc){
		this.key=key;
		this.desc=desc;
	}
	public int getKey() {
		return key;
	}
	public String getDesc() {
		return desc;
	}
	
	public static BookDayStatusEnum valueOf(int key){
		return allBookDayStatusEnum.get(key);
	}

}
