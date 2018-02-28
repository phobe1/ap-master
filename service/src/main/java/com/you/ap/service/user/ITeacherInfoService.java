package com.you.ap.service.user;


import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.IBaseService;

public interface ITeacherInfoService extends IBaseService<TeacherInfoDO> {

    void updateScore(int teacherId, float teachScore);

    int getPrice(int teacherId);

    ApiResponse updatePhoto(int id, String photoPath);

}
