package com.you.ap.service.user.imp;

import com.you.ap.dao.user.IUserOnlineInfoDAO;
import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.user.IUserOnlineInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOnlineInfoServiceImp implements IUserOnlineInfoService {

	private Logger logger = LoggerFactory.getLogger(UserOnlineInfoServiceImp.class);
	
	@Autowired private IUserOnlineInfoDAO userOnlineInfoDAO;

	@Override
	public boolean checkAccessToken(String token) {
		if(token==null){
			return false;
		}
		UserOnlineInfoDO userOnlineInfoDO = getByToken(token);
		if(userOnlineInfoDO == null || userOnlineInfoDO.getStatus() == UserOnlineStatusEnum.OFFLINE.key){
			return false;
		}
		userOnlineInfoDAO.refreshAccessTime(userOnlineInfoDO.getId());
		return true;
	}

	@Override
	public boolean checkTeacherCanVideo(int teacherId) {
        UserOnlineInfoDO userOnlineInfoDO = getByUser(teacherId,UserTypeEnum.Teacher);
        if ( userOnlineInfoDO == null || userOnlineInfoDO.getStatus()!= UserOnlineStatusEnum.ONLINE.getKey()){
        	return false;
		}
		return true;
	}

	@Override
	public boolean quitAccess(int userId, int type) {
		userOnlineInfoDAO.updateStatusByUser(userId, type, UserOnlineStatusEnum.OFFLINE.getKey());
		return true;
	}

	@Override
	public UserOnlineInfoDO getByToken(String token) {
		if (token == null ){
			return null;
		}
		return userOnlineInfoDAO.getByToken(token);
	}

	@Override
	public UserOnlineInfoDO getByUser(int userId, UserTypeEnum userTypeEnum) {
		return userOnlineInfoDAO.getByUser(userId,userTypeEnum.key);
	}

	@Override
	public void addWithLogin(TokenModel tokenModel, UserLoginForm userLoginForm) {
			UserOnlineInfoDO userOnlineInfoDO = new UserOnlineInfoDO(tokenModel,userLoginForm);
			userOnlineInfoDAO.addWithLogin(userOnlineInfoDO);
	}

	@Override
	public boolean updateUserStatus(int userId,UserTypeEnum userTypeEnum, UserOnlineStatusEnum status) {
		try{
			userOnlineInfoDAO.updateStatusByUser(userId,userTypeEnum.key,status.key);
			return true;
		}catch (Exception e){
			logger.error("updateTeacherStatus",e);
			return false;
		}
	}

	@Override
	public void offlineAuto(String lastAccessTime) {
		try{
			userOnlineInfoDAO.offlineByTime(UserOnlineStatusEnum.OFFLINE.key,lastAccessTime);
		}catch (Exception e){
			logger.error("offlineAuto",e);
		}
	}
	
	
	@Override
	public ApiResponse updateChannel(TokenModel tokenModel, String channel) {
		try{
			if(tokenModel == null || StringUtils.isEmpty(channel)){
				return ApiResponse.buildSuccess("token or channel is valid");
			}
			return userOnlineInfoDAO.addChannelByToken(tokenModel.getUserId(),tokenModel.getUserType().getKey(), channel)>0?ApiResponse.buildSuccess("channelId 入库","success"):ApiResponse.buildFailure();
		}catch (Exception e){
			logger.error("addChannelByToken",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}


}
