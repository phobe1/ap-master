package com.you.ap.service.message.imp;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.you.ap.common.config.MessagePushConfiguration;
import com.you.ap.common.helper.CommonUtil;
import com.you.ap.domain.enums.message.MessagePushEnum;
import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.MessgaeModel;
import com.you.ap.domain.model.VideoOrderMessageModel;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.schema.order.VideoOrderDO;
import com.you.ap.service.message.IMessagePushService;
import com.you.ap.service.user.IStudentInfoService;
import com.you.ap.service.user.IUserOnlineInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class MessagePushServiceImp implements IMessagePushService {
	
	private Logger logger = LoggerFactory.getLogger(MessagePushServiceImp.class);
	
	@Autowired
	@Qualifier("studentMessagePusher")
	private MessagePushConfiguration.MessagePusher studentPusher;

	@Autowired
	@Qualifier("teacherMessagePusher")
	private MessagePushConfiguration.MessagePusher teacherPusher;

	@Resource private IUserOnlineInfoService userOnlineInfoService;

	@Resource private IStudentInfoService studentInfoService;

	private void pushMessage(String channelId,MessgaeModel data) throws Exception{
		if (channelId == null|| data == null || data.getTokenModel() == null){
			throw new RuntimeException("param is ill");
		}
		MessagePushConfiguration.MessagePusher pusher = null;
		if (data.getTokenModel().getUserType() == UserTypeEnum.Teacher){
			pusher = teacherPusher;
		}else{
			pusher = studentPusher;
		}
		PushResponse response;
		//response = pusher.getAcsResponse(createMessageRequest(channelId,pusher.getAppKey(),data));
		//logger.info("requestId:{},messageId:{},channelId:{},data:{}",response.getRequestId(),response.getMessageId(),channelId,data);
		//if(data.isNotice()) {
			response = pusher.getAcsResponse(createNoticeRequest(channelId, pusher.getAppKey(), data));

			logger.info("requestId:{},messageId:{},channelId:{}", response.getRequestId(), response.getMessageId(), channelId);
//			return;
//		}
	}

	private PushRequest createMessageRequest(String channelId, Long appkey, MessgaeModel data){
		if (channelId == null|| data == null || appkey == null|| data == null){
			throw new RuntimeException("param is ill");
		}
		PushRequest request = new PushRequest();
		request.setProtocol(ProtocolType.HTTPS);
		request.setMethod(MethodType.POST);
		request.setAppKey(appkey);
		request.setTarget("DEVICE");
		request.setDeviceType("ALL");
		request.setPushType("MESSAGE");
		request.setIOSApnsEnv("PRODUCT");
		request.setTargetValue(channelId);
		request.setTitle(data.getTitle());
		request.setIOSMusic("default");
		request.setBody(CommonUtil.toJson(data));
		return  request;
	}

	private PushRequest createNoticeRequest(String channelId, Long appkey, MessgaeModel data){
		if (channelId == null|| data == null || appkey == null|| data == null){
			throw new RuntimeException("param is ill");
		}
		PushRequest request = new PushRequest();
		request.setProtocol(ProtocolType.HTTPS);
		request.setMethod(MethodType.POST);
		request.setAppKey(appkey);
		request.setTarget("DEVICE");
		request.setDeviceType("ALL");
		request.setPushType("NOTICE");
		request.setIOSApnsEnv("PRODUCT");
		request.setIOSMusic("default");
		request.setTargetValue(channelId);
		request.setTitle(data.getMessagePushEnum().getTitle());
		request.setBody(CommonUtil.toJson(data));
		JSONObject js=new JSONObject();
		js.put("k1","ddd");
		js.put("k2","eee");
		request.setIOSExtParameters(js.toJSONString());
		return  request;
	}

	public void pushMessage(MessgaeModel data) throws Exception{
		if(data == null || data.getTokenModel() == null){
			throw new RuntimeException("data is null");
		}
		UserOnlineInfoDO userOnlineInfoDO = userOnlineInfoService.getByUser(data.getTokenModel().getUserId(), data.getTokenModel().getUserType());
		if (userOnlineInfoDO == null || userOnlineInfoDO.getStatus() == UserOnlineStatusEnum.OFFLINE.key|| StringUtils.isEmpty(userOnlineInfoDO.getChannel())){
			throw new RuntimeException("user pushed error beacause of its status");
		}
		pushMessage(userOnlineInfoDO.getChannel(), data);
	}

	@PostConstruct
	public  void test(){
		try{
			pushMessage("c277e4d9d4474c80a6aaf19f37416764", new MessgaeModel(MessagePushEnum.VIDEO_FINISH).addData(new VideoOrderMessageModel(new VideoOrderDO(),0.077f).of(studentInfoService.getInfoById(8))).of(UserTypeEnum.Teacher,3));

		}catch (Exception e){
			e.printStackTrace();
		}
	}


	
	
	

}
