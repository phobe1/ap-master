package com.you.ap.service.teachcourse.imp;

import com.you.ap.dao.course.IStudentCollectionDAO;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.schema.teachcourse.StudentCollectionDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.teachcourse.StudentCollectionVO;
import com.you.ap.service.teachcourse.IStudentCollectionService;
import com.you.ap.service.teachcourse.ITeachCourseService;
import com.you.ap.service.user.ITeacherInfoService;
import com.you.ap.service.user.IUserOnlineInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class StudentCollectionServiceImp implements IStudentCollectionService{

    private Logger logger= Logger.getLogger(StudentCollectionServiceImp.class);

    @Autowired private IStudentCollectionDAO studentCollectionDAO;

    @Autowired private ITeacherInfoService teacherInfoService;

    @Autowired private IUserOnlineInfoService userOnlineInfoService;

    @Autowired private ITeachCourseService teachCourseService;

    @Override
    public ApiResponse collect(int studentId, int teacherId) {
        try{
            StudentCollectionDO studentCollectionDO=new StudentCollectionDO(studentId,teacherId);
            studentCollectionDAO.collect(studentCollectionDO);
            return ApiResponse.buildSuccess(studentCollectionDO);
        }catch (Exception e){
            logger.error("collect",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteById(int studentId,int teacherId) {
        try {
            return ApiResponse.buildByResult(studentCollectionDAO.deleteById(studentId,teacherId));
        } catch (Exception e) {
            logger.error("deleteById",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public ApiResponse getCollectionListByStudent(
            int studentId,int index,int pageSize) {
        try{
            List<StudentCollectionDO> studentCollectionDOList = studentCollectionDAO.getStudentCollectionList(studentId, index*pageSize, pageSize);
            return ApiResponse.buildSuccess(studentCollectionDOList.
                    stream().
                    map(studentCollectionDO ->
                            new StudentCollectionVO(studentCollectionDO,
                                    teacherInfoService.getById(studentCollectionDO.getTeacherId()),
                                    userOnlineInfoService.getByUser(studentCollectionDO.getTeacherId(), UserTypeEnum.Teacher)).initVideoStatus(teachCourseService.checkCanVideo(studentCollectionDO.getTeacherId())))
            .collect(toList()));
        }catch (Exception e){
            logger.error("getCollectionListByStudent",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public boolean checkHasCollect(int studentId, int teacherId) {
        try{
            return studentCollectionDAO.checkCollect(studentId,teacherId)>0?true:false;
        }catch (Exception e){
            logger.error("checkHasCollect",e);
            return false;
        }
    }
}
