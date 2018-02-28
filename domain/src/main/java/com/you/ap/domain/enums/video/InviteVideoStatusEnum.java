package com.you.ap.domain.enums.video;

import com.google.common.collect.Maps;

import java.util.Map;

public enum InviteVideoStatusEnum {
    WAIT(1,"wait"),
    CUT(2,"挂断"),
    CONN(3,"连接中"),
    COMMIT(4,"通话结束"),
    NONE(5,"不存在");
	
	public final int key;
	public final String value;
	
	private static Map<Integer, InviteVideoStatusEnum> allUserTypes = Maps.newHashMap();
	
	static{
		allUserTypes.put(1, WAIT);
		allUserTypes.put(2, CUT);
		allUserTypes.put(3, CONN);
		allUserTypes.put(4, COMMIT);
		allUserTypes.put(5,NONE);
	}
	
	InviteVideoStatusEnum(int key, String value){
		this.key=key;
		this.value=value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public static InviteVideoStatusEnum valueOf(Integer key) {
		return allUserTypes.get(key);
		
	}
}
