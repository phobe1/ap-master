package com.you.ap.service.user.imp;

import com.you.ap.dao.user.IStudentDAO;
import com.you.ap.domain.schema.user.StudentInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.user.IStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentInfoServiceImp implements IStudentInfoService {

    @Autowired private IStudentDAO studentDAO;

    @Override
    public ApiResponse updatePhoto(int id, String photo) {
        StudentInfoDO studentInfoDO = getById(id);
        if (studentInfoDO == null){
            return ApiResponse.buildFailure();
        }
        studentInfoDO.setImageurl(photo);
        studentDAO.updateInfo(studentInfoDO);
        return ApiResponse.buildSuccess(studentInfoDO);
    }

    @Override
    public ApiResponse updateIdentifyPhoto(int id, String path) {
        StudentInfoDO studentInfoDO = getById(id);
        if (studentInfoDO == null){
            return ApiResponse.buildFailure();
        }
        studentInfoDO.setIdentifyUrl(path);
        studentDAO.updateInfo(studentInfoDO);
        return ApiResponse.buildSuccess();
    }

    @Override
    public StudentInfoDO getInfoById(int id) {
        return studentDAO.getStudentInfo(id);
    }

    @Override
    public StudentInfoDO getById(int id) {
        return studentDAO.getStudentInfo(id);
    }
}
