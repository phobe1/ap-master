package com.you.ap.service.teachcourse;


import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.IBaseService;

public interface ICourseService extends IBaseService<CourseDO> {

    boolean createCourse(String courseName);

    ApiResponse getAllCourseList();

    
}
