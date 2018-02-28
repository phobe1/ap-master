package com.you.ap.dao.order;



import com.you.ap.domain.schema.order.VideoOrderDO;
import org.apache.ibatis.annotations.*;


public interface IVideoOrderDAO {
	
	@Select("select * from video_order where id=#{id}")
	VideoOrderDO getOrderById(@Param("id") int id);
	
	@Update("update video_order set teacher_customer_score =#{teacherCustomerScore}, "
			+ "teacher_manner_score=#{teacherMannerScore},"
			+ "teacher_skill_score=#{teacherSkillScore},modified= now() where id=#{id} ")
	void markOrder(VideoOrderDO videoOrderDO);
	
	@Insert("insert into video_order(student_id,teacher_id,teach_course_id,begin_time,"
			+ "end_time,use_minute,coin,status,teacher_customer_score,"
			+ "teacher_manner_score,teacher_skill_score,created,modified) "
			+ "values(#{studentId},#{teacherId},#{teachCourseId},#{beginTime},#{endTime},"
			+ "#{useMinute},#{coin},#{status},#{teacherCustomerScore},"
			+ "#{teacherMannerScore},#{teacherSkillScore},now(),now() )")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void add(VideoOrderDO videoOrderDO);
	
	@Update("update video_order set end_time=now(),use_minute=#{useMinute},"
			+ "coin=#{coin},status=#{status},modifed=now() where id=#{id}")
	public void commitOrder(VideoOrderDO videoOrderDO);

	@Select("select * from video_order where student_id=#{studentId} and teach_course_id=#{teachCourseId} and status=0 limit 1")
	public VideoOrderDO getOrderByStudentCourse(int studentId, int teachCourseId);
	
	@Update("update video_order set status=#{status} where id=#{id}")
	public void updateOrderStatus(VideoOrderDO videoOrderDO);
	
	@Select("select * from video_order where teach_course_id=#{teachCourseId} and type=0 and status=0  limit 1")
	public VideoOrderDO getHandleInviteOrderByTeachCourse(int teachCourseId);
	
	@Select("select * from video_order where student_id=#{studentId} and status=0  limit 1")
	public VideoOrderDO getHandleInviteOrderByStudent(int studentId);
	
	@Select("select * from video_order where type=1 and src_id=#{bookId}")
	public VideoOrderDO getByBookId(int bookId);

	@Select("select * from video_order where  student_id=#{studentId} order by id desc limit 1")
	public VideoOrderDO getLastOrderByStudentId(int studentId);

}
