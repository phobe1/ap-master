package com.you.ap.service.video.imp;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.you.ap.dao.video.IVideoTeachDAO;
import com.you.ap.domain.enums.course.TeachCourseStatusEnum;
import com.you.ap.domain.enums.message.MessagePushEnum;
import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.enums.video.InviteVideoStatusEnum;
import com.you.ap.domain.model.MessgaeModel;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.model.VideoOrderMessageModel;
import com.you.ap.domain.schema.InvitationTeachDO;
import com.you.ap.domain.schema.order.VideoOrderDO;
import com.you.ap.domain.schema.teachcourse.TeachCourseDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.order.VideoOrderVO;
import com.you.ap.domain.vo.teachcourse.CourseVO;
import com.you.ap.service.message.IMessagePushService;
import com.you.ap.service.order.IVideoOrderService;
import com.you.ap.service.teachcourse.ICourseService;
import com.you.ap.service.teachcourse.IStudentCollectionService;
import com.you.ap.service.teachcourse.ITeachCourseService;
import com.you.ap.service.user.IStudentInfoService;
import com.you.ap.service.user.ITeacherInfoService;
import com.you.ap.service.user.IUserOnlineInfoService;
import com.you.ap.service.video.IInviteVideoService;
import com.you.ap.service.wallet.IWalletService;
import com.you.ap.service.zoom.IZoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Map;

@Service
public class InviteVideoServiceImp implements IInviteVideoService {

	private Logger logger = LoggerFactory.getLogger(InviteVideoServiceImp.class);

	@Autowired private IZoomService zoomService;
	
	@Autowired
	private IMessagePushService messagePushService;
	@Autowired
	private IVideoTeachDAO videoTeachDAO;
	@Autowired
	private IVideoOrderService videoOrderService;
	@Autowired
	private ITeachCourseService tcService;

	@Autowired
	private ICourseService courseService;
	@Autowired
	private IStudentInfoService studentInfoService;
	@Autowired
	private IUserOnlineInfoService userOnlineInfoService;

	@Resource private IStudentCollectionService studentCollectionService;

	@Resource private ITeacherInfoService teacherInfoService;
	
	@Autowired private IWalletService walletService;

	@Override
	public ApiResponse getHandleInvitation(TokenModel tokenModel) {
		if(tokenModel == null ){
			return  ApiResponse.buildFailure("数据异常");
		}
		if(tokenModel.getUserType() == UserTypeEnum.Teacher){
			return ApiResponse.buildSuccess(getHandleInvitationByTeacher(tokenModel.getUserId()));
		}
		return ApiResponse.buildSuccess(getHandleInvitationByStudent(tokenModel.getUserId()));
	}

	@Transactional
	@Override
	public ApiResponse inviteVideo(int studentId, int teachCourseId,String stuZoomId) {
		Map<String ,Object> result = Maps.newHashMap();
		try{
			logger.info("inviteVideo studentId:{},teachCourseId:{}",studentId,teachCourseId);
			// check 当前是否已经有在进行中的邀请。
			InvitationTeachDO invitationTeachDO = getHandleInvitationByStudent(studentId);
			if (invitationTeachDO != null) {
				logger.error("inviteVideo.当前用户已经有邀请");
				return ApiResponse.buildSuccess(InviteVideoStatusEnum.NONE);
			}
			TeachCourseDO teachCourseDO = tcService.getById(teachCourseId);
			if( teachCourseDO == null || teachCourseDO.getStatus()!= TeachCourseStatusEnum.NORMAL.key){
				logger.error("inviteVideo.课程不存在,或者老师没开启视频");
				return ApiResponse.buildFailure("课程不存在,或者老师没开启视频");
			}
			//tc is unnormal
			if (!userOnlineInfoService.checkTeacherCanVideo(teachCourseDO.getTeacherId())) {
				logger.error("inviteVideo.老师或者课程状态不能被邀请，老师未在线，忙碌，或者课程未开课");
				return ApiResponse.buildFailure("老师或者课程状态不能被邀请，老师未在线，忙碌，或者课程未开课");
			}
			if (getHandleInvitationByTeacher(teachCourseDO.getTeacherId()) != null) {
				logger.error("inviteVideo.该老师已经有邀请");
				return ApiResponse.buildFailure("该老师已经有邀请");
			}
			videoTeachDAO.add(InvitationTeachDO.buildNew(studentId,teachCourseDO.getTeacherId(),
					teachCourseId));
			try{
				messagePushService.pushMessage(new MessgaeModel(MessagePushEnum.OPEN_VIDEO,stuZoomId).of(UserTypeEnum.Teacher,teachCourseDO.getTeacherId()).
						addData(studentInfoService.getInfoById(studentId)).addData(new CourseVO(courseService.getById(teachCourseDO.getCourseId()))).notice());
				logger.info("inviteVideo. push message  success ");
			}catch (Exception e){
                logger.error("inviteVideo",e.getMessage());
                result.put("pushResult",e.getMessage());
			}
			userOnlineInfoService.updateUserStatus(studentId,UserTypeEnum.Student, UserOnlineStatusEnum.VIDEO);
			int totalCoin = walletService.getCoin(studentId, UserTypeEnum.Student.getKey());
			result.put("total_minute",totalCoin/teacherInfoService.getPrice(teachCourseDO.getTeacherId()));
			return ApiResponse.buildSuccess(result);
		}catch (Exception e){
			logger.error(e.getMessage());
			return ApiResponse.buildFailure(e.getMessage());
		}
	}

	@Transactional
	@Override
	public ApiResponse closeInvitationByStudent(int studentId) {
		try{
			logger.info("closeInvitationByStudent studentId:{}",studentId);
			VideoOrderDO videoOrderDO = null;
			InvitationTeachDO invitationTeachDO = getHandleInvitationByStudent(studentId);
			if (invitationTeachDO == null) {
				logger.error("closeInvitationByStudent.视频邀请不存在");
				return ApiResponse.buildSuccess(InviteVideoStatusEnum.NONE);
			}
			MessgaeModel messgae = null;
			if (invitationTeachDO.getStatus()== InviteVideoStatusEnum.WAIT.getKey()){
				invitationTeachDO.onCut();
				messgae = new MessgaeModel(MessagePushEnum.STUDENT_CUT).notice();
			}else{
				invitationTeachDO.onCommit();
				videoOrderDO = videoOrderService.createVideoOrder(invitationTeachDO);
				messgae = new MessgaeModel(MessagePushEnum.VIDEO_FINISH).addData(new VideoOrderMessageModel(videoOrderDO,0.077f).of(studentInfoService.getInfoById(invitationTeachDO.getStudentId())));
			}
			videoTeachDAO.update(invitationTeachDO);
			userOnlineInfoService.updateUserStatus(invitationTeachDO.getTeacherId(),UserTypeEnum.Teacher,UserOnlineStatusEnum.ONLINE);
			userOnlineInfoService.updateUserStatus(studentId,UserTypeEnum.Student,UserOnlineStatusEnum.ONLINE);
			tcService.closeVideoStatus(invitationTeachDO.getTeacherId());
			messagePushService.pushMessage(messgae.of(UserTypeEnum.Teacher,invitationTeachDO.getTeacherId()));
			if ( videoOrderDO == null){
				logger.error("closeInvitationByStudent","close success before video connect");
				return ApiResponse.buildSuccess(InviteVideoStatusEnum.CUT);
			}
			studentCollectionService.collect(studentId,invitationTeachDO.getTeacherId());
			VideoOrderVO videoOrderVO = new VideoOrderVO(videoOrderDO,teacherInfoService.getById(invitationTeachDO.getTeacherId()),studentCollectionService.checkHasCollect(studentId,invitationTeachDO.getTeacherId()));
			logger.info("closeInvitationByStudent,videoOrderDO:{}"+videoOrderVO);
			return ApiResponse.buildSuccess(videoOrderVO);
		}catch (Exception e){
			logger.error("closeInvitationByStudent",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}
	
	@Override
	public ApiResponse successInvite(int studentId) {
		try{
			logger.info("successInvite by student,id:{}",studentId);
			InvitationTeachDO invitationTeachDO = getHandleInvitationByStudent(studentId);
			if (invitationTeachDO == null || invitationTeachDO.getStatus()!=InviteVideoStatusEnum.WAIT.getKey()) {
				return ApiResponse.buildSuccess(InviteVideoStatusEnum.NONE);
			}
			videoTeachDAO.update(invitationTeachDO.onConnection());
			messagePushService.pushMessage(
					new MessgaeModel(MessagePushEnum.VIDEO_CONN)
							.of(UserTypeEnum.Teacher,invitationTeachDO.getTeacherId()).notice());
			logger.info("successInvite.上报开启视频状态成功");
			return ApiResponse.buildSuccess("上报开启视频状态成功");
		}catch (Exception e){
			logger.error("successInvite",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}

	@Transactional
	@Override
	public ApiResponse closeInvitationByTeacher(int teacherId) {
		try{
			logger.info("closeInvitationByTeacher teacherId:{}",teacherId);
			VideoOrderDO videoOrderDO = null;
			InvitationTeachDO invitationTeachDO = getHandleInvitationByTeacher(teacherId);

			if (invitationTeachDO == null) {
				logger.error("closeInvitationByTeacher。视频邀请不存在或者被关闭");
				return ApiResponse.buildSuccess(InviteVideoStatusEnum.NONE);
			}
			MessgaeModel messgaeModel = null;
			if (invitationTeachDO.getStatus()==InviteVideoStatusEnum.WAIT.getKey()){
				invitationTeachDO.onCut();
				messgaeModel = new MessgaeModel(MessagePushEnum.TEACHER_CUT).notice();
			}else{
				invitationTeachDO.onCommit();
				videoOrderDO = videoOrderService.createVideoOrder(invitationTeachDO);
				messgaeModel = new MessgaeModel(MessagePushEnum.VIDEO_FINISH).addData(new VideoOrderVO(videoOrderDO,teacherInfoService.getById(teacherId),studentCollectionService.checkHasCollect(invitationTeachDO.getStudentId(),teacherId)));
			}
			logger.info("closeInvitationByTeacher，invitationTeachDO:{},videoOrderDO:{}"+invitationTeachDO,videoOrderDO);
			videoTeachDAO.update(invitationTeachDO);
			userOnlineInfoService.updateUserStatus(teacherId,UserTypeEnum.Teacher,UserOnlineStatusEnum.ONLINE);
			userOnlineInfoService.updateUserStatus(invitationTeachDO.getStudentId(),UserTypeEnum.Student,UserOnlineStatusEnum.ONLINE);
			tcService.closeVideoStatus(teacherId);
			messagePushService.pushMessage(messgaeModel.of(UserTypeEnum.Student,invitationTeachDO.getStudentId()));
			if ( videoOrderDO == null){
				logger.error("closeInvitationByTeacher","close success before video connect");
				return ApiResponse.buildSuccess(InviteVideoStatusEnum.CUT);
			}
			return ApiResponse.buildSuccess(new VideoOrderMessageModel(videoOrderDO,0.077f).of(studentInfoService.getInfoById(invitationTeachDO.getStudentId())));
		}catch (Exception e){
			logger.error("closeInvitationByTeacher",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}


	@Override
	public ApiResponse aggreVideo(int teacherId,String key) {
		try{
			logger.info("aggreVideo ,teacherId:{} ,key: {} ",teacherId,key);
			InvitationTeachDO invitationTeachDO = getHandleInvitationByTeacher(teacherId);
			if (invitationTeachDO == null) {
				return ApiResponse.buildSuccess(InviteVideoStatusEnum.NONE);
			}
			userOnlineInfoService.updateUserStatus(teacherId,UserTypeEnum.Teacher, UserOnlineStatusEnum.VIDEO);
			JSONObject jsonObject = zoomService.getMeetingInfo(teacherId,UserTypeEnum.Teacher.key,key);
			messagePushService.pushMessage(new MessgaeModel(MessagePushEnum.VIDEO_AGGRE,jsonObject).of(UserTypeEnum.Student,invitationTeachDO.getStudentId()));
			return ApiResponse.buildSuccess(jsonObject);
		}
		catch (Exception e){
			logger.error("aggreVideo",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}

//	@Override
//	public ApiResponse getVideoCountByStudent(int studentId) {
//		try{
//			InvitationTeachDO invitationTeachDO = getHandleInvitationByStudent(studentId);
//			if (invitationTeachDO == null) {
//				return ApiResponse.buildFailure();
//			}
//			JSONObject jsonObject =  zoomService.getInfo(studentId,UserTypeEnum.Student.key);
//			if(jsonObject == null){
//				return ApiResponse.buildFailure("get counnt error");
//			}
//			messagePushService.pushMessage(new MessgaeModel(jsonObject.getString("id")).of(UserTypeEnum.Teacher,invitationTeachDO.getTeacherId()));
//			return ApiResponse.buildSuccess(jsonObject);
//		}catch (Exception e){
//			logger.error("",e);
//			return ApiResponse.buildFailure(e.getMessage());
//		}
//	}

//	@Override
//	public ApiResponse getVideoCountByTeacher(int teacherId, String key) {
//		try{
//			InvitationTeachDO invitationTeachDO = getHandleInvitationByTeacher(teacherId);
//			if (invitationTeachDO == null) {
//				return ApiResponse.buildFailure();
//			}
//			return ApiResponse.buildSuccess(zoomService.getMeetingInfo(teacherId,UserTypeEnum.Teacher.key,key));
//		}catch (Exception e){
//			logger.error("getVodeoCountByTeacher",e);
//			return ApiResponse.buildFailure(e.getMessage());
//		}
//	}
	
	
	private InvitationTeachDO getHandleInvitationByStudent(int studentId) {
		return videoTeachDAO.getHandleInvitationByStudent(studentId);
	}

	private InvitationTeachDO getHandleInvitationByTeacher(int teacherId) {
		return videoTeachDAO
				.getHandleInvitationByTeacher(teacherId);
	}

	@Override
	public ApiResponse checkVideoConn(int teacherId) {
		try {
			logger.info("checkVideoConn,teacherId:{}",teacherId);
			InvitationTeachDO invitationTeachDO = getHandleInvitationByTeacher(teacherId);
			if (invitationTeachDO != null && invitationTeachDO.getStatus()==InviteVideoStatusEnum.CONN.getKey()) {
				logger.info("check video connection ok !!");
				return ApiResponse.buildSuccess("ok");
			}
			logger.info("check video connection fai !!");
			return ApiResponse.buildFailure("not ok");
		}catch (Exception e){
			logger.error("",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}
	
	
	

	

}
