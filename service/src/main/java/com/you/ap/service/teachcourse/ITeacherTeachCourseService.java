package com.you.ap.service.teachcourse;

import com.you.ap.domain.enums.course.TeachCourseStatusEnum;
import com.you.ap.domain.vo.ApiResponse;

public interface ITeacherTeachCourseService {

	ApiResponse applyTeachCourse(int teacherId, int courseId);

	ApiResponse getMyTeachCourseList(int teacherId);

	ApiResponse updateStatus(int teacherId, String teachCourseIds,TeachCourseStatusEnum teachCourseStatusEnum);

	ApiResponse deleteTeachCourse(int id, int teacherId);

}
