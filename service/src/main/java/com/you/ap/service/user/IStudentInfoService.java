package com.you.ap.service.user;

import com.you.ap.domain.schema.user.StudentInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.IBaseService;

public interface IStudentInfoService extends IBaseService<StudentInfoDO> {

    ApiResponse updatePhoto(int id, String path);

    ApiResponse updateIdentifyPhoto(int id, String path);

    StudentInfoDO getInfoById(int id);

}
