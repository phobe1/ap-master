package com.you.ap.domain.enums.course;

import com.google.common.collect.Maps;

import java.util.Map;

public enum TeachCourseStatusEnum {
	
	APPLY(0,"审核中"),CLOSE(1,"关闭中"),NORMAL(2,"接单中");
	
	public final int key;
	public final String value;
	
	private static Map<Integer, TeachCourseStatusEnum> allUserStatusEnum = Maps.newHashMap();
	
	static{
		allUserStatusEnum.put(0, APPLY);
		allUserStatusEnum.put(1,CLOSE);
		allUserStatusEnum.put(2, NORMAL);
	}
	
	TeachCourseStatusEnum(int key, String value){
		this.key=key;
		this.value=value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static TeachCourseStatusEnum valueOf(int key){
		return allUserStatusEnum.get(key);
	}

}
