package com.you.ap.dao.user;

import com.you.ap.domain.pojo.AppInfoDO;
import com.you.ap.domain.pojo.Student;
import com.you.ap.domain.schema.user.StudentInfoDO;
import org.apache.ibatis.annotations.*;

public interface IStudentDAO {
	
	final String table="student";
	
	@Select("select * from "+table+" where loginname=#{loginname} and password=#{password}")
	
	public Student loginin(@Param("loginname") String loginname, @Param("password") String password);

	@Select("select * from "+table+" where phone=#{phone} and password=#{password}")
	public Student logininbyPhone(@Param("phone") String phone, @Param("password") String password);

	@Select("select * from "+table+" where phone=#{phone}")
	public Student getDOByPhone(String phone);

	@Select("select * from "+table+" where openid=#{openid}")
	public Student loginByWechat(String openid);

	@Select("select count(1) from "+table+" where openid=#{openid}")
	public int existWechat(String openid);

	@Select("select count(1) from "+table+" where phone=#{phone}")
	public int existPhone(String phone);

	@Select("select count(1) from "+table+" where loginname=#{loginname}")
	public int existName(String loginname);

	@Select("select count(1) from "+table+" where password=#{password} and id=#{id}")
	public int existpassword(Integer id, String password);

	@Insert("insert into "+table+" (name, loginname,grade,phone,password,deviceId,school,openid,imageurl,type) "
			+ "values (#{name},#{loginname},#{grade},#{phone},#{password},#{deviceId},#{school},#{openid},#{imageurl},#{type})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Student student);

	@Update("update "+table+" set name=#{name},grade=#{grade},phone=#{phone},"
			+ "deviceId=#{deviceId},"
			+ "school=#{school},openid=#{openid},imageurl=#{imageurl},type=#{type} "
			+ "where id=#{id}")
	public int update(Student student);

	@Update("update "+table+" set "
			+ " password=#{password} "
			+ "where id=#{id}")
	public int updatePassword(@Param("id") Integer id, @Param("password") String newpassword);

	@Update("update "+table+" set "
			+ " password=#{password} "
			+ "where phone=#{phone}")
	public int updatePasswordByphone(@Param("phone") String phone, @Param("password") String newpassword);


	@Update("update "+table+" set "
			+ " phone=#{phone} "
			+ "where id=#{id}")
	public int updateBandPhone(@Param("id") Integer id, @Param("phone") String phone);

	@Delete(value = { "delete from "+table+" where id=#{id}" })
	public int delete(int id);

	@Insert("insert into feedback (student_id,feedback,created,modified) values (#{studentId},#{feedback},now(),now())")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int addfeedback(@Param("studentId") int studentId,@Param("feedback") String feedback);

	@Select("select id,name,loginname,grade,phone,school,imageurl,identify_url,isIdentity,type from student where id=#{studentId}")
	public StudentInfoDO getStudentInfo(@Param("studentId")int studentId);

		@Update("update student set name=#{name},grade=#{garde},college=#{college},identify_url=#{identifyUrl},isIdentity=1 where id=#{infostudentId}")
	public int realnameIdentify(@Param("name")String name,@Param("infostudentId")int studentId,@Param("college")String college,@Param("garde")int grade,@Param("identifyUrl")String identifyUrl);

	@Select("select * from appinfo where version=#{version}")
	public AppInfoDO getAppInfo(@Param("version")String version);

	@Update("update student " +
			"  set imageurl=#{imageurl}," +
			"      identify_url=#{identifyUrl}," +
			"      grade=#{grade}," +
			"      school=#{school} " +
			"   where id=#{id}")
	void updateInfo(
			StudentInfoDO studentInfoDO);
}
