package com.you.ap.service.teachcourse.imp;

import com.google.common.collect.Lists;
import com.you.ap.dao.course.ITeachCourseDAO;
import com.you.ap.domain.enums.course.TeachCourseStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.schema.teachcourse.TeachCourseDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.teachcourse.TeachCourseVO;
import com.you.ap.service.teachcourse.ICourseService;
import com.you.ap.service.user.ITeacherInfoService;
import com.you.ap.service.user.IUserOnlineInfoService;
import com.you.ap.service.teachcourse.ITeachCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class TeachCourseServiceImp implements ITeachCourseService {
	
	@Autowired private IUserOnlineInfoService userOnlineInfoService;
	@Autowired private ITeachCourseDAO teachCourseDao;
    @Autowired private ICourseService courseService;
    @Autowired private ITeacherInfoService teacherInfoService;

	private Logger logger= LoggerFactory.getLogger(TeachCourseServiceImp.class);


	@Override
	public ApiResponse getDetailVOById(int id) {
		TeachCourseDO teachCourseDO =  getById(id);
		if (null == teachCourseDO){
			return null;
		}
		return ApiResponse.buildSuccess(new TeachCourseVO()
				.of(teacherInfoService.getById(teachCourseDO.getTeacherId())).of(teachCourseDO)
				.of(courseService.getById(teachCourseDO.getCourseId())).of(userOnlineInfoService.getByUser(teachCourseDO.getTeacherId(), UserTypeEnum.Teacher)));
	}

	@Transactional
	@Override
	public boolean createTeachCourse(int teacherId, int courseId,
			int moneyPerMinu) {

		//init one year book day data
		return false;//bookingTeachService.initOneYearBookDayList(teachCourseDO.getId());
	}

	@Override
	public Integer getTeacherIdById(int teachCourseId) {
		return teachCourseDao.getTeacherIdById(teachCourseId);
	}

	@Override
	public void closeVideoStatus(int teacherId) {
		try{
			teachCourseDao.closeVideoStatus(teacherId, TeachCourseStatusEnum.CLOSE.key);
		}catch (Exception e){
			logger.error("closeVideoStatus",e);
		}
	}

	@Override
	public boolean checkCanVideo(int teacherId) {
		if(!userOnlineInfoService.checkTeacherCanVideo(teacherId)){
			return false;
		}
		return teachCourseDao.checkHasVideoStatus(teacherId,TeachCourseStatusEnum.NORMAL.key)>0?true:false;
	}

	@Override
	public TeachCourseDO getById(int id) {
		return  teachCourseDao.getById(id);
	}

	@Override
	public List<Integer> getTCIdByTeacherId(Integer teacherId) {		
		return teachCourseDao.getTCIdByTeacherId(teacherId);
	}


	@Override
	public TeachCourseDO checkIsValid(int teacherId, int courseId) {
		return teachCourseDao.getTeachCourseByTIDCID(teacherId,courseId);
	}

	@Override
	public List<Integer> getAllTeacherIdList() {
		return teachCourseDao.getAllTeacherIdList();
	}

	@Override
	public List<CourseDO> getCourseListByTeacherId(int teacherId) {
		try{
			List<Integer> courseIdList = teachCourseDao.getCourseIdListByTId(teacherId);
			return courseIdList.stream().map(id->courseService.getById(id)).collect(toList());
		}catch (Exception e){
			logger.error("getBookDayListByTeacherId",e);
			return Lists.newArrayList();
		}
	}
	

}
