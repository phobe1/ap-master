package com.you.ap.common.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("message.pusher")
public class MessagePushConfiguration {

	private String regionId;
	private String accessKeyId;
	private String accessKeySecret;
	private Long studentAppKey;
	private Long teacherAppKey;

	private Logger logger = LoggerFactory.getLogger(MessagePushConfiguration.class);


	@Bean("teacherMessagePusher")
	public MessagePusher createTeacherMessagePusher(){
		try{
			IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
			MessagePusher client =  new MessagePusher(profile,teacherAppKey);
			logger.info("Teacher Pusher is created ************{},{},{},{} ",regionId,accessKeyId,accessKeySecret,teacherAppKey);
			return client;
		}catch (Exception e){
			logger.error("createTeacherPusher",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Bean("studentMessagePusher")
	public MessagePusher createStudentMessagePusher(){
		try{
			IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
			MessagePusher client =  new MessagePusher(profile,studentAppKey);
			logger.info("Teacher Pusher is created ************{},{},{},{} ",regionId,accessKeyId,accessKeySecret,studentAppKey);
			return client;
		}catch (Exception e){
			logger.error("createAliPushClient",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public class MessagePusher extends  DefaultAcsClient{
		private Long appKey;

		public MessagePusher(IClientProfile profile,Long appKey){
			super(profile);
			this.appKey = appKey;
		}

		public Long getAppKey() {
			return appKey;
		}

		public void setAppKey(Long appKey) {
			this.appKey = appKey;
		}
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public Long getStudentAppKey() {
		return studentAppKey;
	}

	public void setStudentAppKey(Long studentAppKey) {
		this.studentAppKey = studentAppKey;
	}

	public Long getTeacherAppKey() {
		return teacherAppKey;
	}

	public void setTeacherAppKey(Long teacherAppKey) {
		this.teacherAppKey = teacherAppKey;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
