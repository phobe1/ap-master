package com.you.ap.domain.vo.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.you.ap.domain.model.VideoOrderMessageModel;
import com.you.ap.domain.pojo.Student;
import com.you.ap.domain.schema.order.VideoOrderDO;
import com.you.ap.domain.schema.user.StudentInfoDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.user.StudentLoginVO;
import com.you.ap.domain.vo.user.TeacherInfoVO;

public class VideoOrderVO {

    private VideoOrderMessageModel videoOrderMessageModel;
    private TeacherInfoVO teacherInfoVO;

    public VideoOrderVO(){}

    public VideoOrderVO(VideoOrderDO videoOrderDO, TeacherInfoDO teacherInfoDO){
        this.videoOrderMessageModel = new VideoOrderMessageModel(videoOrderDO);
        this.teacherInfoVO= new TeacherInfoVO(teacherInfoDO);
    }

    public VideoOrderVO(VideoOrderDO videoOrderDO, TeacherInfoDO teacherInfoDO,boolean isCollection){
        this.videoOrderMessageModel = new VideoOrderMessageModel(videoOrderDO);
        this.teacherInfoVO= new TeacherInfoVO(teacherInfoDO,isCollection);
    }

    public VideoOrderMessageModel getVideoOrderMessageModel() {
        return videoOrderMessageModel;
    }

    public void setVideoOrderMessageModel(VideoOrderMessageModel videoOrderMessageModel) {
        this.videoOrderMessageModel = videoOrderMessageModel;
    }

    public TeacherInfoVO getTeacherInfoVO() {
        return teacherInfoVO;
    }

    public void setTeacherInfoVO(TeacherInfoVO teacherInfoVO) {
        this.teacherInfoVO = teacherInfoVO;
    }

    @Override
    public String toString() {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }catch (Exception e){
            return super.toString();
        }
    }
}
