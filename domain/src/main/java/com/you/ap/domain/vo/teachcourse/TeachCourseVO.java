package com.you.ap.domain.vo.teachcourse;


import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.schema.teachcourse.TeachCourseDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.user.TeacherInfoVO;
import com.you.ap.domain.vo.user.UserOnlineInfoVO;

public class TeachCourseVO {
	
	private int teachCourseId;
	private int startTeachDay;
	private String created;
	private int status;

	private CourseVO courseVO;
	private TeacherInfoVO teacherInfoVO;
    private UserOnlineInfoVO userOnlineInfoVO;

	public TeachCourseVO(){}

	public TeachCourseVO of(TeachCourseDO teachCourseDO){
		if (null == teachCourseDO){
			return this;
		}
		this.setTeachCourseId(teachCourseDO.getId());
		this.setCreated(teachCourseDO.getCreated());
		this.setStartTeachDay(teachCourseDO.getStartTeachDay());
		this.setStatus(teachCourseDO.getStatus());
		return this;
	}

	public TeachCourseVO of(CourseDO courseDO){
		if (null == courseDO){
			return this;
		}
		this.courseVO = new CourseVO(courseDO);
		return this;
	}

	public TeachCourseVO of(TeacherInfoDO teacherInfoDO){
		if (null == teacherInfoDO){
			return this;
		}
		this.teacherInfoVO = new TeacherInfoVO(teacherInfoDO);
		return this;
	}

	public TeachCourseVO of(UserOnlineInfoDO userOnlineInfoDO){
		if (null == userOnlineInfoDO){
			return this;
		}
		this.userOnlineInfoVO = new UserOnlineInfoVO(userOnlineInfoDO);
		return this;
	}


	public int getTeachCourseId() {
		return teachCourseId;
	}

	public void setTeachCourseId(int teachCourseId) {
		this.teachCourseId = teachCourseId;
	}

	public int getStartTeachDay() {
		return startTeachDay;
	}

	public void setStartTeachDay(int startTeachDay) {
		this.startTeachDay = startTeachDay;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public CourseVO getCourseVO() {
		return courseVO;
	}

	public void setCourseVO(CourseVO courseVO) {
		this.courseVO = courseVO;
	}

	public TeacherInfoVO getTeacherInfoVO() {
		return teacherInfoVO;
	}

	public void setTeacherInfoVO(TeacherInfoVO teacherInfoVO) {
		this.teacherInfoVO = teacherInfoVO;
	}

	public UserOnlineInfoVO getUserOnlineInfoVO() {
		return userOnlineInfoVO;
	}

	public void setUserOnlineInfoVO(UserOnlineInfoVO userOnlineInfoVO) {
		this.userOnlineInfoVO = userOnlineInfoVO;
	}
}
