package com.you.ap.domain.schema.teachcourse;


import com.you.ap.domain.enums.course.TeachCourseStatusEnum;

import java.io.Serializable;

public class TeachCourseDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 891545544059941592L;
	private int id;
	private int teacherId;
	private int courseId;
	private String created;	
	private String modified;
	/*
	 * 不接单：0  接单中：1  忙碌中：2
	 */
	private int status;	
	private int startTeachDay;
	
	public TeachCourseDO(){
		
	}
	
	
	public TeachCourseDO apply(int teacherId, int courseId){
		this.setTeacherId(teacherId);
		this.setCourseId(courseId);
		this.setStatus(TeachCourseStatusEnum.APPLY.getKey());
		return this;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}


	public int getCourseId() {
		return courseId;
	}


	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


	public String getCreated() {
		return created;
	}


	public void setCreated(String created) {
		this.created = created;
	}


	public String getModified() {
		return modified;
	}


	public void setModified(String modified) {
		this.modified = modified;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getStartTeachDay() {
		return startTeachDay;
	}


	public void setStartTeachDay(int startTeachDay) {
		this.startTeachDay = startTeachDay;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
