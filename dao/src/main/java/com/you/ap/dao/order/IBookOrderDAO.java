package com.you.ap.dao.order;

import com.you.ap.domain.schema.order.BookOrderDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * Created by liangjielin on 2017/12/15.
 */
public interface IBookOrderDAO {
    @Insert("INSERT INTO book_order " +
            " (" +
            "  student_id," +
            "  teacher_id," +
            "  course_id," +
            "  book_day_id," +
            "  book_day," +
            "  hour_boxs," +
            "  location_id," +
            "  price_per_minute," +
            "  money," +
            "  status," +
            "  finish_book_order_time," +
            "  created," +
            "  modified" +
            " ) " +
            " values(" +
            "  #{studentId}," +
            "  #{teacherId}," +
            "  #{courseId}," +
            "  #{bookDayId}," +
            "  #{bookDay}," +
            "  #{hourBoxs}," +
            "  #{locationId}," +
            "  #{pricePerMinute}," +
            "  #{money}," +
            "  #{status}," +
            "  #{finishBookOrderTime}," +
            "  now()," +
            "  now()" +
            " )"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addBookOrder(BookOrderDO bookOrderDO);

    @Select("SELECT id " +
            " FROM book_order " +
            " WHERE " +
            "   created <= #{end} " +
            "   AND status = #{status} ")
    List<Integer> getOrderIdListByTime(
            @Param("end") String end,
            @Param("status") int status
    );

    @Select("SELECT id " +
            " FROM book_order " +
            " WHERE " +
            "   finish_book_order_time <= #{end} " +
            "   AND status = #{status} ")
    List<Integer> getOrderIdListByFinishTime(
            @Param("end") String end,
            @Param("status") int status
    );


    @Select("SELECT * " +
            " FROM book_order " +
            " WHERE id=#{id}")
    BookOrderDO getById(int bookId);

    @Select("<script>" +
            "SELECT * " +
            " FROM book_order " +
            " WHERE student_id=#{studentId} " +
            "       AND status in " +
            "<foreach item='item' index='index' collection='statusSet' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            " ORDER BY id DESC " +
            " LIMIT #{index},#{limit} " +
            "</script>"
    )
    List<BookOrderDO> getOrderListByStudent(
            @Param("studentId") int studentId,
            @Param("statusSet") Set<Integer> statusSet,
            @Param("index") int index,
            @Param("limit") int limit
            );

    @Select("<script>" +
            "SELECT * " +
            " FROM book_order " +
            " WHERE teacher_id=#{teacherId} " +
            "       AND status in " +
            "<foreach item='item' index='index' collection='statusSet' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            " ORDER BY id DESC " +
            " LIMIT #{index},#{limit} " +
             "</script>"
    )
    List<BookOrderDO> getOrderListByTeacher(
            @Param("teacherId") int teacherId,
            @Param("statusSet") Set<Integer> statusSet,
            @Param("index") int index,
            @Param("limit") int limit);


    @Update("UPDATE" +
            "   book_order " +
            " SET " +
            "    status=#{status}," +
            "    modified = NOW() " +
            " WHERE id=#{id}")
    void updateStatus(
            @Param("id") int id,
            @Param("status") int status);



}
