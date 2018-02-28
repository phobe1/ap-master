package com.you.ap.service.user.imp;

import com.you.ap.dao.user.ITeacherDAO;
import com.you.ap.dao.user.ITeacherInfoDAO;
import com.you.ap.domain.pojo.Teacher;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.user.TeacherInfoVO;
import com.you.ap.service.user.ITeacherInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherInfoServiceImp implements ITeacherInfoService {

    private Logger logger= Logger.getLogger(TeacherInfoServiceImp.class);


    @Autowired private ITeacherInfoDAO teacherInfoDAO;
    @Autowired private ITeacherDAO teacherDAO;
    @Override
    public TeacherInfoDO getById(int id) {
        try{
            return teacherInfoDAO.getById(id);
        }catch (Exception e){
            logger.error("TeacherInfoServiceImp.getById",e);
            return null;
        }
    }




    @Override
    public void updateScore(int teacherId, float teachScore) {
        TeacherInfoDO teacherInfoDO = teacherInfoDAO.getById(teacherId);
        if( null == teacherInfoDO){
            return;
        }
        teacherInfoDO.setScore((teacherInfoDO.getScore()*teacherInfoDO.getTeachNum()+teachScore)/(teacherInfoDO.getTeachNum()+1));
        teacherInfoDO.setTeachNum(teacherInfoDO.getTeachNum()+1);
        teacherInfoDAO.update(teacherInfoDO);
    }

    @Override
    public int getPrice(int teacherId) {
        TeacherInfoDO teacherInfoDO = getById(teacherId);
        if (teacherInfoDO!=null){
            return teacherInfoDO.getMoneyPerMinute();
        }
        return 100000000;
    }

    @Override
    public ApiResponse updatePhoto(int id, String photoPath) {
        TeacherInfoDO teacherInfoDO = teacherInfoDAO.getById(id);
        Teacher teacher=teacherDAO.getTeacher(id);
        if (teacherInfoDO == null){
            return ApiResponse.buildFailure();
        }
        teacherInfoDO.setImageUrl(photoPath);
        teacher.setImageurl(photoPath);
        teacherInfoDAO.update(teacherInfoDO);
        teacherDAO.update(teacher);
        return ApiResponse.buildSuccess(new TeacherInfoVO(teacherInfoDO));
    }


}
