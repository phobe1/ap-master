package com.you.ap.provider.controller;

import com.you.ap.common.constant.Constant;
import com.you.ap.common.constant.ResponseConstant;
import com.you.ap.common.helper.FileUtil;
import com.you.ap.common.helper.MD5Util;
import com.you.ap.common.helper.SendMessage;
import com.you.ap.dao.user.IStudentDAO;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.user.IStudentLoginService;
import com.you.ap.service.user.IUserOnlineInfoService;
import com.you.ap.service.wallet.IWalletService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


import static com.you.ap.common.constant.Constant.*;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/student")
public class StudentLoginController {


	@Autowired
	private IStudentLoginService studentLoginService;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IStudentDAO studentDAO;




	@Autowired private IUserOnlineInfoService infoService;

	private Logger logger = LoggerFactory.getLogger(StudentLoginController.class);


	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Object register(UserLoginForm userLoginForm, HttpSession session) {
		ApiResponse checkResult = validMessageCode(session,userLoginForm);
		if (checkResult != null){
			return checkResult;
		}
		return studentLoginService.register(userLoginForm);
	}

	@RequestMapping(value ="/login/sendCode",method = RequestMethod.POST)
	@ResponseBody
	public Object sendCode(
			@RequestParam("phone") String phone,
			HttpSession session) {
		if (phone == null){
			return ApiResponse.buildFailure(ResponseConstant.PHONEORCODEValid);
		}
		String code=String.valueOf((int)((Math.random()*9+1)*100000));
		session.setAttribute(Constant.VALIDATE_PHONE, phone);
		session.setAttribute(VALIDATE_PHONE_CODE, code.toString());
		session.setAttribute(Constant.SEND_CODE_TIME, new Date().getTime());
		logger.info("code is "+code);
		return ApiResponse.buildSuccess(SendMessage.sentMessage(phone, code));
	}

	@RequestMapping(value = "/login/nomal", method = RequestMethod.POST)
	@ResponseBody
	public Object login(UserLoginForm userLoginForm) {

		userLoginForm.setPassword(MD5Util.getStringMD5String(userLoginForm.getPassword()));
		System.out.println(userLoginForm.toString());

		if (!userLoginForm.validNormalLoginParam()) {
			return ApiResponse.buildFailure("loginname and phone is empty or password, at least one is not empty");
		}
		return studentLoginService.login(userLoginForm);
	}

	@RequestMapping(value = "/login/phone", method = RequestMethod.POST)
	@ResponseBody
	public Object loginByPhone(UserLoginForm userLoginForm, HttpSession session) {
		ApiResponse checkResult = validMessageCode(session,userLoginForm);
		if (checkResult != null){
			return checkResult;
		}
		return studentLoginService.loginByPhone(userLoginForm);
	}

	@RequestMapping(value = "/login/wechat", method = RequestMethod.POST)
	@ResponseBody
	public Object loginByWechat(UserLoginForm userLoginForm) {
		if (!userLoginForm.validWeChatParam()) {
			return ApiResponse.buildFailure("openid is empty");
		}
		return studentLoginService.loginByWechat(userLoginForm);
	}

//todo 重构
//	@RequestMapping(value = "/register/wechat", method = RequestMethod.POST)
//	@ResponseBody
//	public Object registerByWechat(@RequestParam("phone") String phone,@RequestParam("code") String code,
//			@RequestParam("openid") String openid,@RequestParam("lat") double lat,
//			@RequestParam("lng") double lng,HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		String validCode = (String) session.getAttribute(VALIDATE_PHONE_CODE);
//        String validphone = (String) session.getAttribute(VALIDATE_PHONE);
//        if(!"".equals(phone)&&validCode.equals(code)&&validphone.equals(phone)&&!"".equals(openid)){
//        		Student student=new Student();
//        		student.setPhone(phone);
//        		student.setOpenid(openid);
//        		boolean isSucessful = studentLoginService.register(student);
//        		if(isSucessful){
//        			Map<String,String> map=new HashMap<String,String>();
//        			map.put("studentId", String.valueOf(student.getId()));
//        			map.put("token", createToken(student.getId(), lat, lng));
//        			return ApiResponse.buildSuccess(map,"register successful");
//        		}else{
//        			return ApiResponse.buildSuccess("register failed!");
//        		}
//
//        }
//		return ApiResponse.buildSuccess("code do not match phone");
//
//	}

//todo 重构
//	@RequestMapping(value = "/login/updateMessage", method = RequestMethod.POST)
//	@ResponseBody
//	public Object update(Student student) {
//		if (studentLoginService.updateStudent(student)) {
//			return ApiResponse.buildSuccess(student,"sucessful login");
//		} else {
//			return ApiResponse.buildSuccess("update failed!!!");
//		}
//	}

	@RequestMapping(value = "/register/validname/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Object validName(@PathVariable String name) {
		return studentLoginService.nameIsUsed(name);
	}

	@RequestMapping(value = "/register/validphone/{phone}", method = RequestMethod.GET)
	@ResponseBody
	public Object validPhone(@PathVariable String phone) {
		return  studentLoginService.phoneIsUsed(phone);
	}

//	/**
//	 * 登陆状态修改密码
//	 * @param studentId
//	 * @param oldPassword
//	 * @param newpassword
//	 * @return
//	 */
//	@RequestMapping(value = "/login/resetpassword/{studentId}/{oldPassword}/{newpassword}", method = RequestMethod.GET)
//	@ResponseBody
//	public Object changeWord(
//			@PathVariable("studentId") int studentId,
//			@PathVariable("oldPassword") String oldPassword,
//			@PathVariable("newpassword") String newpassword){
//		if(studentId>0&&oldPassword!=null&&newpassword!=null){
//			String outcome=studentLoginService.changePassword(studentId, MD5Util.getStringMD5String(oldPassword), MD5Util.getStringMD5String(newpassword));
//			return ApiResponse.buildSuccess(outcome);
//		}else{
//			return ApiResponse.buildSuccess("parames is wrong");
//		}
//	}


	@RequestMapping(value="/login/resetphone",method = RequestMethod.GET)
	@ResponseBody
	public Object forgotPassWord(
			@RequestParam("phone") String phone,
			@RequestParam("code") String code,
			@RequestParam("pwd") String newpassword,
			HttpServletRequest request){

		HttpSession session = request.getSession();
		String validCode = (String) session.getAttribute(VALIDATE_PHONE_CODE);
		String validphone = (String) session.getAttribute(Constant.VALIDATE_PHONE);

		if(validCode==null||validphone==null){
			return ApiResponse.buildSuccess("phone or code is empty");
		}


		if(!"".equals(phone)&&validCode.equals(code)&&validphone.equals(phone)){
			if(!studentLoginService.existPhone(phone)){

				return ApiResponse.buildFailure(ResponseConstant.PHONENOTEXIST);
			}
			boolean outcome=studentLoginService.changePwdByphone(validphone, MD5Util.getStringMD5String(newpassword));
			if(outcome){
				return ApiResponse.buildSuccess("update successful");
			}else{
				return ApiResponse.buildSuccess("update error");
			}

		}
		return ApiResponse.buildSuccess("phone and code not match");
	}

	/**
	 * 绑定手机号
	 * @param studentId
	 * @param userLoginForm
	 * @return
	 */
	@RequestMapping(value="/login/bandphone",method = RequestMethod.POST)
	@ResponseBody
	public Object bandPhone(
			@RequestParam("studentId") int studentId,
			UserLoginForm userLoginForm,
			HttpServletRequest request){
		HttpSession session = request.getSession();
		String validCode = (String) session.getAttribute(Constant.VALIDATE_PHONE_CODE);
		String validphone = (String) session.getAttribute(Constant.VALIDATE_PHONE);

		String phone=userLoginForm.getPhone();
		if(studentDAO.existPhone(phone)>0){
			return ApiResponse.buildFailure("绑定失败，手机号已被绑定，请更换手机号再试。");
		}

		String code=userLoginForm.getCode();
		double lat=userLoginForm.getLat();
		double lng=userLoginForm.getLng();

		if(validCode==null||validphone==null){
			return ApiResponse.buildSuccess("验证码失效");
		}
		if(!"".equals(phone)&&validCode.equals(code)&&validphone.equals(phone)){
			String outcome=studentLoginService.bandPhone(studentId, phone);
			if("successful".equals(outcome)){
				return studentLoginService.loginByPhone(userLoginForm);
			}
			return ApiResponse.buildSuccess("resson",outcome);
		}
		return ApiResponse.buildFailure("手机号和验证码不匹配");
	}


	/**
	 * 校验手机号与微信号存在关系
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/login/validation/wechat/{phone}",method = RequestMethod.GET)
	@ResponseBody
	public Object validPhoneWechat(@PathVariable("phone") String phone){

		if(phone==null||phone.equals("")){
			return "phone parameter is wrong";
		}
		return studentLoginService.validWechatPhone(phone);
	}


	private ApiResponse validMessageCode(HttpSession session,UserLoginForm userLoginForm){
		if ( userLoginForm==null || !userLoginForm.validPhoneParam()){
			return ApiResponse.buildFailure(ResponseConstant.PHONEORCODEValid);
		}
		String sessionCode = (String) session.getAttribute(VALIDATE_PHONE_CODE);
		String sessionPhone = (String) session.getAttribute(Constant.VALIDATE_PHONE);
		if ( userLoginForm.getPhone().equals(sessionPhone)&& userLoginForm.getCode().equals(sessionCode)){
			return null;
		}
		return ApiResponse.buildFailure(ResponseConstant.MESSAGECODEERROR);
	}

	@RequestMapping(value = "/login/forgetpassword",method = RequestMethod.GET)
	@ResponseBody
	public Object forgetPasswordAndsendCode(
			@RequestParam("phone") String phone,
			HttpSession session) {
		if (phone == null){
			return ApiResponse.buildFailure(ResponseConstant.PHONEORCODEValid);
		}
		if(!studentLoginService.existPhone(phone)){
			return ApiResponse.buildFailure(ResponseConstant.PHONENOTEXIST);
		}
		String code=String.valueOf((int)((Math.random()*9+1)*100000));
		session.setAttribute(Constant.VALIDATE_PHONE, phone);
		session.setAttribute(VALIDATE_PHONE_CODE, code.toString());
		session.setAttribute(Constant.SEND_CODE_TIME, new Date().getTime());
		return ApiResponse.buildSuccess(SendMessage.sentMessage(phone, code));
	}


	@RequestMapping(value = "/login/resetpassword",method = RequestMethod.GET)
	@ResponseBody
	public Object resetPassword(
			UserLoginForm userLoginForm,
			HttpSession session) {


		return studentLoginService.resetpassword(userLoginForm, session);

	}

	@RequestMapping(value = "/addfeedback", method = RequestMethod.POST)
	@ResponseBody
	public Object feedback(
			@RequestAttribute(value = "studentId") Integer studentId,
			@RequestParam(value = "feedback") String feedback) {
		System.out.println(studentId);
		return studentLoginService.feedback(feedback, studentId);
	}

	@RequestMapping(value = "/mymoney", method = RequestMethod.GET)
	@ResponseBody
	public Object getMymoney(
			@RequestAttribute(value = "studentId") Integer studentId) {
		System.out.println(studentId);
		return walletService.getWallet(studentId, UserTypeEnum.Student.getKey());
	}

	//测试接口，初始化用户钱包
//	@RequestMapping(value = "/initwallet", method = RequestMethod.GET)
//	@ResponseBody
//	public Object initwallet(
//			@RequestAttribute(value = "studentId") Integer studentId) {
//		System.out.println(studentId);
//		return walletService.initwallet(studentId, UserTypeEnum.Student.getKey());
//	}

	@RequestMapping(value = "/myInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getMyInfo(
			@RequestAttribute(value = "studentId") Integer studentId) {
		System.out.println(studentId);
		return studentLoginService.getStudentInfo(studentId);
	}

	@RequestMapping(value = "/realnameIdentify", method = RequestMethod.POST)
	@ResponseBody
	public Object getMyInfo(
			@RequestAttribute(value = "studentId") Integer studentId,
			@RequestParam(value = "college") String college,
			@RequestParam(value = "name") String name,
			@RequestParam(value="grade") Integer grade,
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request) {
		System.out.println(studentId);

		String path=FileUtil.saveFile(file,UserTypeEnum.Student,studentId,2);

		logger.info("begin realIdentify "+studentId+" path"+path);

		return studentLoginService.realnameIdentify(studentId,name,college,grade,path);

	}

	@RequestMapping(value = "/appIntroduction", method = RequestMethod.GET)
	@ResponseBody
	public Object appIntroduction(@Param(value = "version") String version) {

		return studentLoginService.appIntroduction(version);
	}



}
