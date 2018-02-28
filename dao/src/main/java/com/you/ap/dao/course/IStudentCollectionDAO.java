package com.you.ap.dao.course;

import com.you.ap.domain.schema.teachcourse.StudentCollectionDO;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface IStudentCollectionDAO {

	@Insert("INSERT IGNORE INTO " +
			"  student_collection("
			+ "student_id,teacher_id,collection_time) "
			+ " values(#{studentId},#{teacherId},NOW())"
	)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int collect(StudentCollectionDO studentCollectionDO);
	
	@Select("SELECT * " +
			" FROM student_collection " +
			" WHERE student_id=#{studentId} " +
			" LIMIT #{index},#{pageSize}"
	)
	List<StudentCollectionDO> getStudentCollectionList(
			@Param("studentId") int studentId,
			@Param("index") int index,
			@Param("pageSize") int pageSize);
	
	@Delete("DELETE FROM student_collection " +
			" WHERE teacher_id=#{teacherId} " +
			" AND student_id=#{studentId}"
	)
	int deleteById(
			@Param("studentId") int studentId,
			@Param("teacherId") int teacherId);

	@Select("SELECT COUNT(1) " +
			" FROM " +
			" student_collection " +
			" WHERE student_id=#{studentId} " +
			"  AND  teacher_id=#{teacherId}"
	)
	int checkCollect(
			@Param("studentId") int studentId,
			@Param("teacherId") int teacherId
	);

}
