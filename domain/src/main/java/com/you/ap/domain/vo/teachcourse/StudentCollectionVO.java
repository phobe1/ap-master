package com.you.ap.domain.vo.teachcourse;

import com.you.ap.domain.enums.course.TeachCourseStatusEnum;
import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.schema.teachcourse.StudentCollectionDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.user.TeacherInfoVO;
import com.you.ap.domain.vo.user.UserOnlineInfoVO;

public class StudentCollectionVO {

    private int id;
    private TeacherInfoVO teacherInfoVO;
    private String collectionTime;
    private UserOnlineInfoVO userOnlineInfoVO;

    public StudentCollectionVO(StudentCollectionDO studentCollectionDO, TeacherInfoDO teacherInfoDO, UserOnlineInfoDO userOnlineInfoDO){
        this.id = studentCollectionDO.getId();
        this.collectionTime=studentCollectionDO.getCollectionTime();
        this.teacherInfoVO = new TeacherInfoVO(teacherInfoDO);
        this.userOnlineInfoVO =new UserOnlineInfoVO(userOnlineInfoDO);
    }

    public StudentCollectionVO initVideoStatus(boolean canVideo){
        if (canVideo){
            this.userOnlineInfoVO.setStatus(1);
        }else if (userOnlineInfoVO.getStatus()== UserOnlineStatusEnum.BUSY.key){
            this.userOnlineInfoVO.setStatus(3);
        }else{
            this.userOnlineInfoVO.setStatus(0);
        }
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TeacherInfoVO getTeacherInfoVO() {
        return teacherInfoVO;
    }

    public void setTeacherInfoVO(TeacherInfoVO teacherInfoVO) {
        this.teacherInfoVO = teacherInfoVO;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public UserOnlineInfoVO getUserOnlineInfoVO() {
        return userOnlineInfoVO;
    }

    public void setUserOnlineInfoVO(UserOnlineInfoVO userOnlineInfoVO) {
        this.userOnlineInfoVO = userOnlineInfoVO;
    }
}
