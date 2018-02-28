package com.you.ap.provider.interceptor;

import com.you.ap.common.helper.CommonUtil;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.service.user.IUserOnlineInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Configuration
public class AccessInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger= LoggerFactory.getLogger(AccessInterceptor.class);
	
	@Autowired private IUserOnlineInfoService userinfoInfoService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			TokenModel tokenModel = CommonUtil.validToken(request.getParameter("token"));
		    if(tokenModel==null){
				logger.info("token is not valid");
				CommonUtil.setResponseData(response, "token is null or  invalid");
				return false;
		    }
			if(! userinfoInfoService.checkAccessToken(tokenModel.getToken())){
		    	logger.info("user is offline,pleace login or post the right token");
		    	CommonUtil.setResponseData(response, "user token is not exits");
				return false;
		    }
			logger.info("check ok and refresh success");
			setDataToRequest(request, tokenModel);
			logger.info("set data ok");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void setDataToRequest(HttpServletRequest request,TokenModel tokenModel){
		if(tokenModel== null||tokenModel.getUserType()==null||tokenModel.getUserId()==null){
			return;
		}
		request.setAttribute(UserTypeEnum.ALL.value,tokenModel);
		if(tokenModel.getUserType()==UserTypeEnum.Student){
			request.setAttribute(UserTypeEnum.Student.getValue(), tokenModel.getUserId());
		}else{
			request.setAttribute(UserTypeEnum.Teacher.getValue(), tokenModel.getUserId());
		}
	}
	
	

	
}
