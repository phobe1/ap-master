package com.you.ap.service.user;


import com.you.ap.domain.pojo.Teacher;
import com.you.ap.domain.vo.ApiResponse;
import org.apache.http.HttpException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ITeacherLoginService {

    public Object register(String phone, String code, String password, HttpServletRequest request);

	public Object loginNomal( String phone, String password, double lat, double lng);

	public Object loginSentCode(String phone, HttpServletRequest request) throws HttpException,IOException;

	//查看当前用户名是否可以使用
	public boolean nameIsUsed(String loginname);

	public boolean updateTeacher(Teacher teacher);

	public Object loginPhone(String phone, String code, double lat, double lng, HttpServletRequest request);
	
	public Teacher loginByWechat(String openid);
	
	public boolean phoneIsUsed(String phone);
	
	public boolean wechatIsUsed(String openid);
	
	public int getIdbyname(String loginname);

	ApiResponse feedback(String feedback, int teacherId);

	ApiResponse getTeacher(int teacherId);


}
