package com.you.ap.provider.controller.video;

import com.alibaba.fastjson.JSONObject;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.video.IInviteVideoService;
import com.you.ap.service.zoom.IZoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/invite/teacher")
public class TeacherInvitationController {

	@Autowired
	private IInviteVideoService invitationService;
	
	@Autowired
	private IZoomService zoomService;

	@RequestMapping(value = "/aggre", method = RequestMethod.POST)
	@ResponseBody
	public Object aggrVideo(
			@RequestAttribute(value = "teacherId") Integer teacherId,
			@RequestParam("stuZoomId") String stuZoomId) {
		return invitationService.aggreVideo(teacherId,stuZoomId);
	}
	
	@RequestMapping(value = "/cancle.do", method = RequestMethod.GET)
	@ResponseBody
	public Object cancleVideo(
			@RequestAttribute(value = "teacherId") Integer teacherId
			) {
		return invitationService.closeInvitationByTeacher(teacherId);
	}
	
	@RequestMapping(value = "/check.do", method = RequestMethod.GET)
	@ResponseBody
	public Object checkVideoSuccess(
			@RequestAttribute(value = "teacherId") Integer teacherId
			) {
		return invitationService.checkVideoConn(teacherId);
	}

	@RequestMapping(value = "/get/handle", method = RequestMethod.GET)
	@ResponseBody
	public Object checkVideoSuccess(
			@RequestAttribute(value = "teacherId") int teacherId
	) {
		return invitationService.getHandleInvitation(new TokenModel(UserTypeEnum.Teacher,teacherId));
	}


//	@RequestMapping(value = "/getZoomInfo", method = RequestMethod.GET)
//	@ResponseBody
//	public Object getZoomInfo(
//			@RequestAttribute(value = "teacherId") Integer teacherId,
//			@RequestParam(value="stuZoomId") String stuZoomId){
//		return invitationService.getVideoCountByTeacher(teacherId,stuZoomId);
//	}

}
