package com.you.ap.domain.enums.user;

import com.google.common.collect.Maps;

import java.util.Map;

public enum UserOnlineStatusEnum {
	ONLINE(1,"在线") , OFFLINE(0,"离线"), BUSY(2,"忙碌"),VIDEO(3,"教学中");
	
	public final int key;
	public final String value;
	
	private static Map<Integer, UserOnlineStatusEnum> allUserStatusEnum = Maps.newHashMap();
	
	static{
		allUserStatusEnum.put(0, OFFLINE);
		allUserStatusEnum.put(1, ONLINE);
		allUserStatusEnum.put(2, BUSY);
		allUserStatusEnum.put(3, VIDEO);
	}
	
	UserOnlineStatusEnum(int key, String value){
		this.key=key;
		this.value=value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static UserOnlineStatusEnum valueOf(int key){
		return allUserStatusEnum.get(key);
	}
}
