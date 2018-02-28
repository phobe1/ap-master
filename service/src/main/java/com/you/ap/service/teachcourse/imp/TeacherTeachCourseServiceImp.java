package com.you.ap.service.teachcourse.imp;
import com.you.ap.common.helper.StringUtil;
import com.you.ap.dao.course.ITeachCourseDAO;
import com.you.ap.domain.enums.course.TeachCourseStatusEnum;
import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.schema.teachcourse.TeachCourseDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.teachcourse.TeachCourseVO;
import com.you.ap.service.teachcourse.ICourseService;
import com.you.ap.service.teachcourse.ITeacherTeachCourseService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherTeachCourseServiceImp implements ITeacherTeachCourseService {

	Logger logger = LoggerFactory.getLogger(TeacherTeachCourseServiceImp.class);
	
	@Autowired private ITeachCourseDAO teachCourseDao;
	@Autowired private ICourseService courseService;


	@Override
	public ApiResponse applyTeachCourse(int teacherId, int courseId) {
		try{
			CourseDO courseDO = courseService.getById(courseId);
			if(courseDO == null){
				return ApiResponse.buildFailure("course id not exists");
			}
			return ApiResponse.buildSuccess(teachCourseDao.addTeachCourse(new TeachCourseDO().apply(teacherId,courseId)));
		}catch (Exception e){
			logger.error("applyTeachCourse",e);
			return ApiResponse.buildFailure("内部服务器错误");
		}
	}

	@Override
	public ApiResponse getMyTeachCourseList(int teacherId) {
		try{
			List<TeachCourseDO> teachCourseDOList =  teachCourseDao.getTCListByTId(teacherId);
			return ApiResponse.buildSuccess(teachCourseDOList.stream().map(teachCourseDO -> new TeachCourseVO()
					.of(teachCourseDO)
					.of(courseService.getById(teachCourseDO.getCourseId()))).collect(Collectors.toList()));
		}catch (Exception e){
			logger.error("getMyTeachCourseList",e);
			return ApiResponse.buildFailure("内部服务器错误");
		}
	}


	@Override
	public ApiResponse updateStatus(int teacherId, String teachCourseIds, TeachCourseStatusEnum status) {
		try{
			Set<Integer> idSets = StringUtil.splitStr(teachCourseIds);
			if(CollectionUtils.isEmpty(idSets)){
				return  ApiResponse.buildFailure("id is empty");
			}
			idSets.forEach(id->{
				if (teachCourseDao.checkTCIsValid(id,teacherId) == 0 ) {
					logger.error("check tc is valid tid{},tcid{}",teacherId,id);
					return;
				}
				teachCourseDao.updateTCStatus(id, teacherId, status.getKey());
			});
           return ApiResponse.buildSuccess("更新成功");
		}catch (Exception e){
			logger.error("updateStatus",e);
			return ApiResponse.buildFailure("内部服务器错误");
		}
	}

	@Override
	public ApiResponse deleteTeachCourse(int id, int teacherId) {
		try{
			return ApiResponse.buildSuccess(teachCourseDao.deleteTCByTeacher(id,teacherId));
		}catch (Exception e){
			logger.error("deleteTeachCourse",e);
			return ApiResponse.buildFailure("内部服务器错误");
		}

	}

}
