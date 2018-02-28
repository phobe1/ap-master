package com.you.ap.service.teachcourse;

import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.schema.teachcourse.TeachCourseDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.teachcourse.TeachCourseVO;
import com.you.ap.service.IBaseService;

import java.util.List;

public interface ITeachCourseService extends IBaseService<TeachCourseDO>{

	ApiResponse getDetailVOById(int id);

	TeachCourseDO checkIsValid(int teacherId, int courseId);

	List<Integer> getAllTeacherIdList();

	List<CourseDO> getCourseListByTeacherId(int teacherId);

	boolean createTeachCourse(int teacherId, int courseId, int moneyPerMinu);
	//******************admin end****************

	List<Integer> getTCIdByTeacherId(Integer teacherId);
	Integer getTeacherIdById(int teachCourseId);
	
	//******************common end****************   

	void closeVideoStatus(int teacherId);

	boolean checkCanVideo(int teacherId);

}
