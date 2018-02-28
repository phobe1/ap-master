package com.you.ap.dao.user;

import com.you.ap.domain.pojo.Teacher;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import org.apache.ibatis.annotations.*;

public interface ITeacherDAO {
	
	final String table="teacher";
	
	@Select("select * from "+table+" where loginname=#{loginname} and password=#{password}")
	public Teacher loginin(@Param("loginname") String loginname, @Param("password") String password);

	@Select("select * from "+table+" where phone=#{phone} and password=#{password}")
	public Teacher logininbyPhone(@Param("phone") String phone, @Param("password") String password);
	
	@Select("select * from "+table+" where phone=#{phone}")
	public Teacher loginByPhone(String phone);
	
	@Select("select * from "+table+" where openid=#{openid}")
	public Teacher loginByWechat(String openid);
	
	@Select("select count(1) from "+table+" where openid=#{openid}")
	public Integer existWechat(String openid);
	
	@Select("select count(1) from "+table+" where phone=#{phone}")
	public Integer existPhone(String phone);
	
	@Select("select count(1) from "+table+" where loginname=#{loginname}")
	public Integer existName(String loginname);
	
	@Insert("insert into "+table+" (name, loginname,phone,password,deviceId,openid,imageurl) "
			+ "values (#{name},#{loginname},#{phone},#{password},#{deviceId},#{openid},#{imageurl})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Teacher teacher);

	@Update("update "+table+" set name=#{name},phone=#{phone},"
			+ "deviceId=#{deviceId},"
			+ "openid=#{openid},imageurl=#{imageurl}"
			+ "where id=#{id}")
	public int update(Teacher teacher);


	@Delete(value = { "delete from "+table+" where id=#{id}" })
	public int delete(int id);
	
	@Select("select id from "+table+" where loginname=#{loginname}")
	public Integer getIdbyname(String loginname);

	@Insert("insert into feedback (teacher_id,feedback,created,modified) values (#{teacherId},#{feedback},now(),now())")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int addfeedback(@Param("teacherId") int teacherId,@Param("feedback") String feedback);


	@Select("select id,name,loginname,phone,imageurl from teacher where id=#{teacherId}")
	public Teacher getTeacher(@Param("teacherId")int teacherId);

}
