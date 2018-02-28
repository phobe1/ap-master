package com.you.ap.dao.money;

import com.you.ap.domain.schema.order.TradeDO;
import org.apache.ibatis.annotations.*;

/**
 * Created by liangjielin on 2017/12/29.
 */
public interface ITradeDAO {

    @Insert("insert into dealorder (order_number,student_id,teacher_id,note,money,coin,created,modified) values (#{orderNumber},#{studentId},#{teacherId},#{note},#{money},#{coin},now(),now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int addDealOrder(@Param("orderNumber")String orderNumber, @Param("studentId") int studentId, @Param("teacherId") int teacherId,
                            @Param("note") String note, @Param("money") double money, @Param("coin")int coin);

    @Insert("insert into dealorder (order_number,student_id,teacher_id,note,money,coin,created,modified) values (#{orderNumber},#{studentId},#{teacherId},#{note},#{money},#{coin},now(),now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int addTradeOrder(TradeDO tradeDO);

    @Select("select * from dealorder where order_number=#{orderNumber}")
    public TradeDO getTradeOrderByOrderNumber(@Param("orderNumber")String orderNumber);

    @Update("update dealorder set status=#{status} where order_number=#{orderNumber}")
    public int updateByPrimaryKeySelective(TradeDO tradeDO);

}
