package com.you.ap.dao.money;


import com.you.ap.domain.schema.WalletDO;
import com.you.ap.domain.schema.order.TradeDO;
import org.apache.ibatis.annotations.*;

/**
 * Created by liangjielin on 2017/12/15.
 */
public interface IWalletDAO {

    @Select("select * from user_wallet where user_id=#{userId} and type=#{type}")
    public WalletDO getUserWallet(@Param("userId")int userId, @Param("type") int type);

    @Update("update user_wallet set coin=#{coin},modified=now() where user_id=#{userId} and type=#{type}")
    public int updateCoin(@Param("coin") int coin, @Param("userId") int userId,@Param("type") int type);

    @Update("update user_wallet set money=#{money} ,modified=now() where user_id=#{userId} and type=#{type}")
    public int updateMoney(@Param("money") double money, @Param("userId") int userId,@Param("type") int type);

    @Insert("insert into user_wallet (user_id,type,status,money,coin,created,modified) values (#{userId},#{type},1,0,#{coin},now(),now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int initwallet( @Param("userId") int userId,@Param("type") int type,@Param("coin") int coin);




}
