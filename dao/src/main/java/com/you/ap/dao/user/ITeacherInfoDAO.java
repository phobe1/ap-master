package com.you.ap.dao.user;

import com.you.ap.domain.schema.user.TeacherInfoDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ITeacherInfoDAO {

	@Select("SELECT * FROM teacher_base_info "
			+ " WHERE id=#{id}")
    TeacherInfoDO getById(
            @Param("id") int id);


	@Update("UPDATE " +
			" teacher_base_info" +
			" SET " +
			"   name=#{name}," +
			"   college_name=#{collegeName}," +
			"   city_id=#{cityId}," +
			"   grade=#{grade}," +
			"   college_name=#{collegeName}," +
			"   profession=#{profession}," +
			"   score=#{score}," +
			"   teach_num=#{teachNum}," +
			"   image_url=#{imageUrl}," +
			"   money_per_minute =#{moneyPerMinute}," +
			"   modified=NOW() " +
			" WHERE " +
			"    id=#{id}"
	)
	void update(TeacherInfoDO teacherInfoDO);


}
