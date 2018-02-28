package com.you.ap.service.user;


import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.pojo.Student;
import com.you.ap.domain.vo.ApiResponse;

import javax.servlet.http.HttpSession;

public interface IStudentLoginService extends  IBaseLoginService {
	
	//查看当前用户名是否可以使用
	ApiResponse nameIsUsed(String loginname);
	
	ApiResponse updateStudent(Student student);

	ApiResponse loginByPhone(UserLoginForm userLoginForm);

	ApiResponse loginByWechat(UserLoginForm userLoginForm);

	ApiResponse phoneIsUsed(String phone);

	ApiResponse wechatIsUsed(String openid);
	
	public String changePassword(Integer id, String password, String newpassword);

	public String  bandPhone(Integer id, String phone);

	ApiResponse  validWechatPhone(String phone);

    public boolean changePwdByphone(String phone, String password);


	ApiResponse resetpassword(UserLoginForm userLoginForm,HttpSession session);

	boolean sendChangePasswordCode(String phone,HttpSession session);

	ApiResponse feedback(String fedback,int studnetId);

	ApiResponse getStudentInfo(int studentId);

	ApiResponse realnameIdentify(int studentId,String name,String college,int grade,String identify_url);

	ApiResponse appIntroduction(String version);

	public boolean existPhone(String phone);
	
}
