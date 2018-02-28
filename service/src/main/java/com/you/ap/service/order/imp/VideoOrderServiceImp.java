package com.you.ap.service.order.imp;

import com.you.ap.common.helper.DateUtil;
import com.you.ap.dao.order.IVideoOrderDAO;
import com.you.ap.domain.enums.order.VideoOrderStatusEnum;
import com.you.ap.domain.form.OrderTeacherScoreForm;
import com.you.ap.domain.schema.InvitationTeachDO;
import com.you.ap.domain.schema.order.VideoOrderDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.user.ITeacherInfoService;
import com.you.ap.service.wallet.IWalletService;
import com.you.ap.service.order.IVideoOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class VideoOrderServiceImp implements IVideoOrderService {
	
	@Autowired private IVideoOrderDAO videoOrderDao;
    @Autowired private IWalletService walletService;
    @Resource
	private ITeacherInfoService teacherInfoService;

	private Logger logger= Logger.getLogger(VideoOrderServiceImp.class);
	
	@Override
	public VideoOrderDO createVideoOrder(InvitationTeachDO invitationTeachDO){
		try{
			if(invitationTeachDO==null){
				return null;
			}
			VideoOrderDO videoOrderDO=new VideoOrderDO(invitationTeachDO);
			int time = DateUtil.compareMinuteBetweenDate(invitationTeachDO.getEndTime(),invitationTeachDO.getBeginTime());
			if(time== 0 )time=1;
			videoOrderDO.initVideoTime(time);
			videoOrderDO.setCoin(videoOrderDO.getUseMinute()*teacherInfoService.getPrice(invitationTeachDO.getTeacherId()));
			videoOrderDao.add(videoOrderDO);
			walletService.transCoin(videoOrderDO.getStudentId(), videoOrderDO.getTeacherId(), videoOrderDO.getCoin());
			return videoOrderDO;
		}catch (Exception e){
			logger.error("createVideoOrder",e);
			return null;
		}
	}

	
	@Override
	public ApiResponse markScoreForVideoOrder(int studentId,OrderTeacherScoreForm form) {
		try{
			if ( form ==null){
				return ApiResponse.buildFailure();
			}
			VideoOrderDO order = videoOrderDao.getOrderById(form.getOrderId());
			//如果已经评论过 或者无此订单 ，返回false
			if( order == null || !order.getStudentId().equals(studentId)
					|| order.getStatus() == VideoOrderStatusEnum.FINISH_COMMENT.getKey()){
				return ApiResponse.buildFailure();
			}
			order.setTeacherCustomerScore(form.getTeacherCustomerScore());
			order.setTeacherMannerScore(form.getTeacherMannerScore());
			order.setTeacherSkillScore(form.getTeacherSkillScore());
			videoOrderDao.markOrder(order);
			logger.info(order);
			//更新该教师授课的平均评分
			float avgScore = (form.getTeacherCustomerScore()+form.getTeacherMannerScore()
					+form.getTeacherSkillScore())/3;

			teacherInfoService.updateScore(order.getTeacherId(), avgScore);
			return ApiResponse.buildSuccess();
		}catch (Exception e){
			logger.error("markScoreForVideoOrder",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}

	@Override
	public VideoOrderDO getById(int id) {
		VideoOrderDO order = null;
		try{
			 order = videoOrderDao.getOrderById(id);
		}catch (Exception e){
			logger.error("getById",e);

		}

		return order;
	}


	

}
