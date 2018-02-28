package com.you.ap.dao.course;


import com.you.ap.domain.form.OnlineTCForm;
import com.you.ap.domain.schema.OnlineTCInfoDO;
import com.you.ap.domain.schema.teachcourse.TeachCourseDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ITeachCourseDAO {

	@Select("SELECT * " +
			" FROM " +
			"  teach_course " +
			"  WHERE " +
			"    teacher_id=#{teacherId} " +
			"    AND course_id=#{courseId}"
	)
	TeachCourseDO getTeachCourseByTIDCID(
			@Param("teacherId") int teacherId,
			@Param("courseId") int courseId);

	@Select("SELECT distinct teacher_id " +
			" FROM " +
			"  teach_course "
	)
	List<Integer> getAllTeacherIdList();


	@Select("select t_c.id as teach_course_id ,t_info.score  teach_score,t_info.money_per_minute  money_per_minute,t_c.status as teach_course_status,"
			+ "t_info.name as teacher_name,t_info.id as teacher_id, "
			+ "t_info.image_url as photo,t_info.college_name as college_name,t_info.grade as grade ,"
			+ " t_info.profession as profession, "
			+ " online.status as teacher_status, x(online.address) as lat,y(online.address) as lng "
			+ "from teach_course as t_c "
			+"inner join user_online_info as online "
			+ "on online.type=1 and  t_c.teacher_id=online.user_id "
			+ " left join teacher_base_info t_info on t_info.id=t_c.teacher_id "
			+ " where t_c.course_id=#{courseId} and online.status=1 and t_c.status=2 order by #{orderType} "
			+ " limit #{index},#{limit}")
	List<OnlineTCInfoDO> getOnlineListBySubject(OnlineTCForm form);
	
	@Select("select * from teach_course where teacher_id=#{teacherId} ")
	List<TeachCourseDO> getTCListByTId(int teacherId);

	@Select("SELECT " +
			"   course_id " +
			" FROM teach_course " +
			" WHERE teacher_id=#{teacherId} ")
	List<Integer> getCourseIdListByTId(int teacherId);

	@Delete("delete from teach_course where id=#{id} and teacher_id=#{teacherId}")
	boolean deleteTCByTeacher(
            @Param("id") int id,
            @Param("teacherId") int teacherId
    );

	@Select("select count(1) from teach_course where id=#{id} and teacher_id=#{teacherId} and status>0")
	int checkTCIsValid(
            @Param("id") int id,
            @Param("teacherId") int teacherId);

	@Update("update teach_course set status=#{status} ,modified=now()  where id=#{id} and teacher_id=#{teacherId}")
	boolean updateTCStatus(
            @Param("id") int id,
            @Param("teacherId") int teacherId,
            @Param("status") int status
    );

	@Update("update teach_course set status=#{status} ,modified=now()  where teacher_id=#{teacherId}")
	boolean closeVideoStatus(
			@Param("teacherId") int teacherId,
			@Param("status") int status
	);

	@Select("select count(1) from teach_course where teacher_id=#{teacherId} and status=#{status} limit 1")
	int checkHasVideoStatus(
			@Param("teacherId") int teacherId,
			@Param("status") int status
	);

	@Select("select * from teach_course where teacher_id=#{teacherId} and course_id=#{courseId} limit 1")
	TeachCourseDO checkTeachCourseIsExit(
            @Param("teacherId") int teacherId,
            @Param("courseId") int courseId);

	@Insert("insert ignore into teach_course(teacher_id,course_id,start_teach_day,"
			+ "created,modified,status) "
			+ "values(#{teacherId},#{courseId},now(),"
			+ "now(),now(),#{status}) ")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() as id", keyProperty = "id", before = false, resultType = Integer.class)
	boolean addTeachCourse(TeachCourseDO teacherCourseDO);

	@Select("SELECT * " +
			"  FROM " +
			"    teach_course " +
			"  WHERE id=#{id}")
	TeachCourseDO getById(@Param("id") int id);

	@Select("select teacher_id from teach_course  where id=#{id} ")
	Integer getTeacherIdById(
            @Param("id") int id);

	@Select("select id from teach_course where teacher_id=#{teacherId}")
	List<Integer> getTCIdByTeacherId(
            @Param("teacherId") int teacherId);


	
}
