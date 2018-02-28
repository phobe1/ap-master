package com.you.ap.task.imp;

import com.you.ap.common.config.MessagePushConfiguration;
import com.you.ap.common.helper.DateUtil;
import com.you.ap.service.book.IBookService;
import com.you.ap.service.order.IBookOrderService;
import com.you.ap.service.teachcourse.ITeachCourseService;
import com.you.ap.service.user.IUserOnlineInfoService;
import com.you.ap.task.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class TaskServiceImp implements ITaskService {

	private Logger logger = LoggerFactory.getLogger(MessagePushConfiguration.class);


	@Autowired
	private IUserOnlineInfoService userOnlineInfoService;

	@Autowired
	private ITeachCourseService teachCourseService;

	@Autowired private IBookService bookService;

	@Autowired private IBookOrderService bookOrderService;
	
	//@Scheduled(cron="0 0/20 * * * *")
	@Override
	public void autoOfflineUserStatus() {
		try {
			System.out.println("task to offline user");
			//定义最后访问时间，--当前世界-30分钟
			String lastAccessTime = DateUtil.formateDateToYMDHMS(DateUtil.addMinutes(new Date(), -30));
			//将所有最后访问时间<lastAccessDate 的状态update为offline。
			userOnlineInfoService.offlineAuto(lastAccessTime);
		} catch (Exception e) {
			logger.error("autoIncrementTeacherBookTime",e);
		}
	}


	@PostConstruct
	@Scheduled(cron="1 2 0 * * ?")
	@Override
	public void addBookDay() {
        try{
			List<Integer> teacherIdList =  teachCourseService.getAllTeacherIdList();
			teacherIdList.forEach(id->bookService.autoAddBookDay(id));
			logger.info("addBookDay succeess! Day:{}",new Date());
		}catch (Exception e){
			logger.error("autoIncrementTeacherBookTime",e);
		}
	}

	@Scheduled(cron="0 0/15 * * * ?")
	@Override
	public void closeOutOfTimeBookOrder() {
		try{
			//String start = DateUtil.formateDateToYMDHMS(DateUtil.addMinutes(new Date(),-11));
			String end = DateUtil.formateDateToYMDHMS(DateUtil.addMinutes(new Date(),-5));
			List<Integer> result = bookOrderService.closeOutTimeOrders(end);
			logger.info("closeOutOfTimeBookOrder success!! endTime{},IDList:{}",end,result);
		}catch (Exception e){
			logger.error("autoCloseBookOrder",e);
		}
	}


	@Scheduled(cron="0 * * * * ?")
	@Override
	public void finishBookOrder() {
		try{
			//String start = DateUtil.formateDateToYMDHMS(DateUtil.addMinutes(new Date(),-1));
			String end = DateUtil.formateDateToYMDHMS(new Date());
			List<Integer> result = bookOrderService.finishOrder(end);
			logger.info("finishBookOrder success!! endTime:{},IDList:{}",end,result);
		}catch (Exception e){
			logger.error("autoFinishBookOrder",e);
		}
	}

}
