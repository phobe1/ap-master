package com.you.ap.domain.schema.teachcourse;

import java.io.Serializable;

public class StudentCollectionDO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer studentId;
	private Integer teacherId;
	private String collectionTime;
	
	
	public StudentCollectionDO(){
		
	}
	
    public StudentCollectionDO(int studentId, int teacherId){
		this.studentId=studentId;
		this.teacherId=teacherId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
}
