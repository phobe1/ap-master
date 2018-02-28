package com.you.ap.dao.book;

import com.you.ap.domain.schema.book.BookDayDetailDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IBookTeachDAO {

	@Insert("INSERT IGNORE" +
			" INTO book_teach_detail(" +
			"   book_day," +
			"   status," +
			"   teacher_id," +
			"   created," +
			"   modified" +
			" ) " +
			" VALUES(" +
			"   #{bookDay}," +
			"   #{status},#{teacherId}," +
			"   NOW()," +
			"   NOW()" +
			" )")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void addBookDay(BookDayDetailDO bookDayDO);

	@Select("SELECT MAX(book_day) " +
			" FROM book_teach_detail " +
			" WHERE " +
			"    teacher_id=#{teacherId}"
	)
    Integer getMaxShowDay(
    		@Param("teacherId") int teacherId
	);

	@Select("SELECT " +
			" id, " +
			" book_day," +
			" status," +
			" location_id," +
			" booked_hour_box " +
			" FROM book_teach_detail " +
			" WHERE book_day>=#{startDay} and book_day<=#{endDay} and teacher_id=#{teacherId}")
	List<BookDayDetailDO> getBookDayListByTeacherId(
			@Param("teacherId") int teacherId,
			@Param("startDay") int startDay,
			@Param("endDay") int endDay);

	@Select("SELECT * " +
			" FROM " +
			"    book_teach_detail " +
			" WHERE " +
			"       id=#{id} "
	)
	BookDayDetailDO getById(
			@Param("id") int id);



	@Update("UPDATE " +
			" book_teach_detail " +
			" SET " +
			"    book_day=#{bookDay}," +
			"    teacher_id=#{teacherId}," +
			"    location_id=#{locationId}," +
			"    pub_book_hour_box=#{pubBookHourBox}," +
			"    booked_hour_box=#{bookedHourBox}," +
			"    status=#{status}," +
			"    modified=NOW() " +
			" WHERE id=#{id}")
	void updateBookDayDetail(BookDayDetailDO bookDayDetailDO);

	@Select("SELECT distinct location_id " +
			" FROM  book_teach_detail " +
			" WHERE " +
			"    book_day>=#{startDay} " +
			"    AND book_day<=#{endDay} " +
			"    AND teacher_id=#{teacherId}")
	List<Integer> getLocationListOfTeacher(
			@Param("teacherId") int teacherId,
			@Param("startDay") int startDay,
			@Param("endDay") int endDay
	);



//	@Select("SELECT " +
//			" id," +
//			" book_day_id," +
//			" student_id, " +
//			" teacher_id, " +
//			" status," +
//			" hour_box," +
//			" location_id " +
//			" FROM " +
//			" book_teach_detail " +
//			" WHERE " +
//			"     book_day_id=#{bookDayId} order by hour_box ")
//	List<BookTeachTimeDO> getBookTimeListByBookDay(
//            @Param("bookDayId") int bookDayId);


//	@Select("select * from book_time_teach where book_day_id=#{bookDayId} and "
//			+ "(start_hour>=#{startHour} and end_hour<=#{endHour}) or "
//			+ "(start_hour<#{startHour}  and end_hour>#{endHour}) or "
//			+ "(start_hour>=#{startHour} and start_hour<#{endHour}) or "
//			+ "(end_hour<#{endHour} and end_hour>#{startHour}) and status in (0,1) limit 1")
//	BookTeachDayDO checkBookTimeIsExit(
//            @Param("bookDayId") int bookDayId,
//            @Param("startHour") int startHour,
//            @Param("endHour") int endHour);

	@Update("<script>" +
			"UPDATE " +
			"  book_teach_detail " +
			" SET status =#{status},location_id=#{locationId},modified=now() " +
			" WHERE " +
			" book_day_id=#{bookDayId} " +
			"AND status!=2 AND id in " +
			"<foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>"
			+ "#{item}"
			+"</foreach>"
			+ "</script>")
	void updatePublish(
			@Param("bookDayId") int bookDayId,
			@Param("idList") List<Integer> idList,
			@Param("locationId") int locationId,
			@Param("status") int status);

	@Select("<script>" +
			"SELECT count(1) " +
			" FROM " +
			"  book_teach_detail " +
			" WHERE " +
			" book_day_id=#{bookDayId} " +
			" AND status = 1 " +
			" AND id in " +
			"<foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>"
			+ "#{item}"
			+"</foreach>"
			+ "</script>")
	int checkBookTimeCanBook(
			@Param("bookDayId") int bookDayId,
			@Param("idList") List<Integer> idList
	);



}