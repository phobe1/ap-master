package com.you.ap.provider.controller;

import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.message.IMessagePushService;
import com.you.ap.service.user.IUserOnlineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private IUserOnlineInfoService userOnlineInfoService;
	
	@Autowired
	@Qualifier("messagePushServiceImp")
	private IMessagePushService iMessagePushService;
	
	@RequestMapping(value="/push/channel",method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse pushChannel(
			@RequestAttribute(value="all") TokenModel tokenModel,
			@RequestParam(value="channelId") String channelId
			){
		System.out.println(tokenModel.toString());
		return userOnlineInfoService.updateChannel(tokenModel, channelId);

	}
	
	
	
	

}
