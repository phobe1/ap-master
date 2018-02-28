package com.you.ap.domain.vo.book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.you.ap.domain.model.book.LocationModel;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.teachcourse.CourseVO;
import com.you.ap.domain.vo.user.TeacherInfoVO;
import com.you.ap.domain.vo.user.UserOnlineInfoVO;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class TeacherInfoForBookVO {

    private TeacherInfoVO teacherInfoVO;
    private UserOnlineInfoVO userOnlineInfoVO;
    private List<LocationVO> locationVOList =Lists.newArrayList();
    private List<CourseVO> courseVOList = Lists.newArrayList();

    private JSONObject js;

    public JSONObject getJs() {
        return js;
    }

    public void setJs(JSONObject js) {
        this.js = js;
    }

    public TeacherInfoForBookVO(){}

    public TeacherInfoForBookVO(List<CourseDO> courseDOS, List<LocationModel> locationModels, TeacherInfoDO teacherInfoDO,UserOnlineInfoDO userOnlineInfoDO){
        if (CollectionUtils.isNotEmpty(courseDOS)){
            this.courseVOList = courseDOS.stream().map(courseDO -> new CourseVO(courseDO)).collect(toList());
        }
        if(CollectionUtils.isNotEmpty(locationModels)){
            this.locationVOList = locationModels.stream().map(locationModel -> new LocationVO(locationModel)).collect(toList());
        }
        this.teacherInfoVO =new TeacherInfoVO(teacherInfoDO);
        this.userOnlineInfoVO = new UserOnlineInfoVO(userOnlineInfoDO);
    }

    public TeacherInfoVO getTeacherInfoVO() {
        return teacherInfoVO;
    }

    public void setTeacherInfoVO(TeacherInfoVO teacherInfoVO) {
        this.teacherInfoVO = teacherInfoVO;
    }

    public List<LocationVO> getLocationVOList() {
        return locationVOList;
    }

    public void setLocationVOList(List<LocationVO> locationVOList) {
        this.locationVOList = locationVOList;
    }

    public List<CourseVO> getCourseVOList() {
        return courseVOList;
    }

    public void setCourseVOList(List<CourseVO> courseVOList) {
        this.courseVOList = courseVOList;
    }

    public UserOnlineInfoVO getUserOnlineInfoVO() {
        return userOnlineInfoVO;
    }

    public void setUserOnlineInfoVO(UserOnlineInfoVO userOnlineInfoVO) {
        this.userOnlineInfoVO = userOnlineInfoVO;
    }
}
