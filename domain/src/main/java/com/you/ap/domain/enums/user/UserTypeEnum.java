package com.you.ap.domain.enums.user;

import com.google.common.collect.Maps;

import java.util.Map;

public enum UserTypeEnum {
	
	Student(0,"studentId"),Teacher(1,"teacherId"),ALL(2,"all");
	
	public final int key;
	public final String value;
	
	private static Map<Integer, UserTypeEnum> allUserTypes = Maps.newHashMap();
	
	static{
		allUserTypes.put(0, Student);
		allUserTypes.put(1, Teacher);
		allUserTypes.put(2,ALL);
	}
	
	UserTypeEnum(int key, String value){
		this.key=key;
		this.value=value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public static UserTypeEnum valueOf(Integer key) {
		if(key==null){
			return null;
		}
		return allUserTypes.get(key);
	}

	public static UserTypeEnum notValueOf(Integer key) {
		if(key==null){
			return null;
		}
		if(key == Student.key){
			return Teacher;
		}
		else if(key== Teacher.key){
			return Student;
		}
		return ALL;
	}

}
