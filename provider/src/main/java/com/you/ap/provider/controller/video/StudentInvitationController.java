package com.you.ap.provider.controller.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.you.ap.common.helper.SendMessage;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.video.IInviteVideoService;
import com.you.ap.service.zoom.IZoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/invitation/student")
public class StudentInvitationController {
	
	@Autowired private IInviteVideoService invitationService;

	@Autowired
	private IZoomService zoomService;

	// 学生主动发起邀请
	@RequestMapping(value = "/invite", method = RequestMethod.GET)
	@ResponseBody
	public Object invite(
			@RequestAttribute(value = "studentId") Integer studentId,
			@RequestParam(value = "teachCourseId") int teachCourseId,
			@RequestParam("stuZoomId") String stuZoomId) {
		return invitationService.inviteVideo(studentId, teachCourseId,stuZoomId);
	}
	
	@RequestMapping(value = "/cancle", method = RequestMethod.GET)
	@ResponseBody
	public Object cancleInvitation(
			@RequestAttribute(value = "studentId", required = true) Integer studentId) {
		return invitationService.closeInvitationByStudent(studentId);
	}
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	@ResponseBody
	public Object successVideo(
			@RequestAttribute(value = "studentId", required = true) Integer studentId) {
		return invitationService.successInvite(studentId);
	}

	@RequestMapping(value = "/getZoomInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getZoomInfo(
			@RequestAttribute(value = "studentId") int studentId){
		return ApiResponse.buildSuccess(zoomService.getInfo(studentId, UserTypeEnum.Student.key));
	}

	@RequestMapping(value = "/get/handle", method = RequestMethod.GET)
	@ResponseBody
	public Object checkVideoSuccess(
			@RequestAttribute(value = "studentId") int studentId
	) {
		return invitationService.getHandleInvitation(new TokenModel(UserTypeEnum.Student,studentId));
	}

}
