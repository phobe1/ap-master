package com.you.ap.provider.controller;

import com.you.ap.dao.user.ITeacherDAO;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.pojo.Teacher;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.book.IBookLocationService;
import com.you.ap.service.user.IStudentLoginService;
import com.you.ap.service.user.ITeacherInfoService;
import com.you.ap.service.wallet.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liangjielin on 2017/12/17.
 */
@Controller

public class TestController {
    @Autowired
    private IStudentLoginService studentLoginService;
    @Autowired
    private IBookLocationService locationService;
    @Autowired
    private IWalletService walletService;
    @Autowired
    private ITeacherDAO teacherDAO;

    @RequestMapping(value = "/location", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse testlocation() {

        return ApiResponse.buildSuccess(locationService.getLocationJson());
    }


    //测试接口，初始化用户钱包
	@RequestMapping(value = "/student/initwallet", method = RequestMethod.GET)
	@ResponseBody
	public Object initStudentwallet(
			@RequestAttribute(value = "studentId") Integer studentId) {
		System.out.println(studentId);
		return walletService.initwallet(studentId, UserTypeEnum.Student.getKey());
	}
    @RequestMapping(value = "/teacher/initwallet", method = RequestMethod.GET)
    @ResponseBody
    public Object initTeacherwallet(
            @RequestAttribute(value = "teacherId") Integer teacherId) {
        System.out.println(teacherId);
        return walletService.initwallet(teacherId, UserTypeEnum.Teacher.getKey());
    }

    @RequestMapping(value = "student/transMoney", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse transMoney(@RequestParam(value = "money") double money,
                             @RequestAttribute(value = "studentId") Integer studentId,
                             @RequestParam(value = "teacherId") Integer teacherId) {

        return walletService.transMoney(studentId,teacherId,money);
    }

    @RequestMapping(value = "student/transCoin", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse transCoin(@RequestParam(value = "coin") int coin,
                                  @RequestAttribute(value = "studentId") Integer studentId,
                                  @RequestParam(value = "teacherId") Integer teacherId) {

        walletService.transCoin(studentId,teacherId,coin);
        return  ApiResponse.buildSuccess("");
    }

    @RequestMapping(value = "student/addMoney",method=RequestMethod.GET)
    @ResponseBody
    public ApiResponse addMoney(@RequestParam(value = "money") double money,
                                @RequestAttribute(value = "studentId") Integer studentId){
        return walletService.addMoney(studentId,UserTypeEnum.Student.getKey(),money);
    }

    @RequestMapping(value = "student/addCoin",method=RequestMethod.GET)
    @ResponseBody
    public ApiResponse addMoney(@RequestParam(value = "coin") int coin,
                                @RequestAttribute(value = "studentId") Integer studentId){
        return walletService.addCoin(studentId,UserTypeEnum.Student.getKey(),coin);
    }

    @RequestMapping(value = "addteacher",method=RequestMethod.POST)
    @ResponseBody
    public ApiResponse addTeacher(Teacher teacher){
        return ApiResponse.buildSuccess(teacherDAO.insert(teacher));
    }





}