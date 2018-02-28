package com.you.ap.service.teachcourse.imp;

import com.you.ap.dao.course.ICourseDAO;
import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.teachcourse.ICourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImp implements ICourseService {
    Logger logger = LoggerFactory.getLogger(CourseServiceImp.class);

    @Autowired
    private ICourseDAO courseDAO;

    @Override
    public boolean createCourse(String courseName) {
        courseDAO.addCourse(courseName);
        return true;
    }

    @Override
    public ApiResponse getAllCourseList() {
        try{
            return ApiResponse.buildSuccess(courseDAO.getCourseList());
        }catch (Exception e){
            logger.error("getAllList",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public CourseDO getById(int id) {
        return courseDAO.getById(id);
    }
}
