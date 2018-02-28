package com.you.ap.domain.enums.message;

import com.google.common.collect.Maps;

import java.util.Map;

public enum MessagePushEnum {
    OPEN_VIDEO(1,"open video","您有一个通话"),
    STUDENT_CUT(2,"STUDENT CLOSE","视频已经被学生挂断"),
    TEACHER_CUT(3,"TEACHER CUT","视频已经被老师挂断"),
    VIDEO_CONN(4,"TEACHER CONN","视频已经连接成功"),
    VIDEO_FINISH(5,"VIDEO FINISH","视频已结束"),
	VIDEO_AGGRE(6,"VIDEO AGGRE","老师同意视频");
	
    private final int key;
    private final String value;
    private final String title;
	
	private static Map<Integer, MessagePushEnum> allUserTypes = Maps.newHashMap();
	
	static{
		allUserTypes.put(1, OPEN_VIDEO);
		allUserTypes.put(2, STUDENT_CUT);
		allUserTypes.put(3, TEACHER_CUT);
		allUserTypes.put(4, VIDEO_CONN);
		allUserTypes.put(5, VIDEO_FINISH);
	}
	
	MessagePushEnum(int key, String value,String title){
		this.key=key;
		this.value=value;
		this.title=title;
	}

	
	
	public int getKey() {
		return key;
	}



	public String getValue() {
		return value;
	}



	public static MessagePushEnum valueOf(Integer key) {
		if(key==null){
			return OPEN_VIDEO;
		}
		MessagePushEnum result =allUserTypes.get(key);
		return result==null?OPEN_VIDEO:result;
	}

	public String getTitle() {
		return title;
	}
}
