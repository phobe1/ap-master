package com.you.ap.service.user;


import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public interface IUserOnlineInfoService {

	UserOnlineInfoDO getByToken(String token);

	UserOnlineInfoDO getByUser(int userId, UserTypeEnum userTypeEnum);

	void addWithLogin(TokenModel tokenModel, UserLoginForm userLoginForm);

	//拦截器中调用此方法check
	boolean checkAccessToken(String token);

	//直接呼叫老师时，check
	boolean checkTeacherCanVideo(int teacherId);

	//退出登陆
	boolean quitAccess(int userId, int type);

	boolean updateUserStatus(int userId,UserTypeEnum userTypeEnum, UserOnlineStatusEnum status);

	void offlineAuto(String lastAccessTime);
	
	ApiResponse updateChannel(TokenModel tokenModel, String channel);

}
