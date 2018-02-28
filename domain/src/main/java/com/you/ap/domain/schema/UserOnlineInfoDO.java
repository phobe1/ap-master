package com.you.ap.domain.schema;


import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.model.TokenModel;

public class UserOnlineInfoDO {

	private int id;
	private int userId;
	private int type;
	private String token;
	private String created;
	private String modified;
	private double lat;
	private double lng;
	private String accessTime;
	private int status;
    private String channel;





	public UserOnlineInfoDO() {

	}

	public UserOnlineInfoDO(TokenModel tokenModel, UserLoginForm userLoginForm) {
		this.userId = tokenModel.getUserId();
		this.type = tokenModel.getUserType().getKey();
		this.status= UserOnlineStatusEnum.ONLINE.getKey();
		this.token = tokenModel.getToken();
		this.lat = userLoginForm.getLat();
		this.lng = userLoginForm.getLng();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}
}
