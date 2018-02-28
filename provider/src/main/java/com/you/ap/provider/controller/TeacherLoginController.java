package com.you.ap.provider.controller;

import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.pojo.Teacher;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.user.ITeacherLoginService;
import com.you.ap.service.wallet.IWalletService;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/teacher")
public class TeacherLoginController {

	
	@Autowired
	private ITeacherLoginService teacherService;

	@Autowired
	private IWalletService walletService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse register(@RequestParam("phone") String phone, @RequestParam("password") String password,
								@RequestParam("code") String code, HttpServletRequest request){
		return (ApiResponse) teacherService.register(phone,code,password,request);


	}
	
	@RequestMapping(value ="/login/sendCode",method = RequestMethod.POST)  
    @ResponseBody  
    public ApiResponse sendCode(@RequestParam("phone") String phone, HttpServletRequest request) throws HttpException, IOException {
		return (ApiResponse) teacherService.loginSentCode( phone,  request);
    }
	
	@RequestMapping(value = "/login/nomal", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse login(

			@RequestParam(value="phone",required=false) String phone,
			@RequestParam("password") String password,
			@RequestParam("lat") double lat,
			@RequestParam("lng") double lng
			) {
				return (ApiResponse) teacherService.loginNomal( phone,  password,  lat,  lng);
		}

	@RequestMapping(value = "/login/phone", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse loginByPhone(
			@RequestParam("phone") String phone,
			@RequestParam("code") String code,
			@RequestParam("lat") double lat,
			@RequestParam("lng") double lng,
			HttpServletRequest request
			) {
		return (ApiResponse) teacherService.loginPhone( phone, code, lat,  lng, request);

	}
	
//	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
//	@ResponseBody
//	public ApiResponse updateInfo(Teacher teacher) {
//
//		if (teacherService.updateTeacher(teacher)) {
//			return ApiResponse.buildSuccess(teacher,"sucessful login" );
//		} else {
//			return ApiResponse.buildFailure("update failed!!!");
//		}
//	}

	@RequestMapping(value = "/addfeedback", method = RequestMethod.POST)
	@ResponseBody
	public Object feedback(
			@RequestAttribute(value = "teacherId") Integer teacherId,
			@RequestParam(value = "feedback") String feedback) {
		System.out.println(teacherId);
		return teacherService.feedback(feedback, teacherId);
	}

	@RequestMapping(value = "/mymoney", method = RequestMethod.GET)
	@ResponseBody
	public Object getMymoney(
			@RequestAttribute(value = "teacherId") Integer teacherId) {
		System.out.println(teacherId);
		return walletService.getWallet(teacherId, UserTypeEnum.Teacher.getKey());
	}

	@RequestMapping(value = "/myInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getMyInfo(
			@RequestAttribute(value = "teacherId") Integer teacherId) {
		System.out.println(teacherId);
		return teacherService.getTeacher(teacherId);
	}




}
