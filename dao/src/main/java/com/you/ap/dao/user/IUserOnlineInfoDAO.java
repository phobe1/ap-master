package com.you.ap.dao.user;

import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface IUserOnlineInfoDAO {
	
	@Insert("INSERT INTO "
			+" user_online_info(" +
			"    user_id," +
			"    type,token," +
			"    status," +
			"    address," +
			"    access_time," +
			"    created," +
			"    modified" +
			"  ) "
			+" VALUES(" +
			"    #{userId}," +
			"    #{type}," +
			"    #{token}," +
			"    #{status}," +
			"    point(${lat},${lng})," +
			"    NOW()," +
			"    NOW()," +
			"    NOW()" +
			" ) " +
			" ON DUPLICATE KEY " +
			" UPDATE " +
			"   token=#{token}," +
			"   status =#{status}," +
			"   address = point(${lat},${lng})," +
			"   access_time = NOW()," +
			"   modified = NOW()")
	void addWithLogin(UserOnlineInfoDO userOnlineInfoDO);
		
	@Select("SELECT " +
			"   id id," +
			"   type type," +
			"   token token," +
			"   created created," +
			"   modified modified," +
			"   access_time access_time," +
			"   status status," +
			"   channel channel," +
			"   x(address) lat," +
			"   y(address) lng  " +
			"  FROM " +
			"   user_online_info " +
			" WHERE " +
			"   type=#{userType} " +
			"   AND user_id=#{userId} "
	)
	UserOnlineInfoDO getByUser(
            @Param("userId") int userId,
            @Param("userType") int userType);

	@Select("SELECT " +
			"   id id, " +
			"   type type, " +
			"   token token, " +
			"   created created, " +
			"   modified modified , " +
			"   access_time access_time, " +
			"   status status, " +
			"   channel channel, " +
			"   x(address) lat, " +
			"   y(address) lng  " +
			" FROM user_online_info " +
			" WHERE " +
			"   token = #{token} "
	)
	UserOnlineInfoDO getByToken(@Param("token") String token);

	@Update(" UPDATE " +
			"    user_online_info " +
			"  SET " +
			"    access_time = NOW()," +
			"    modified = NOW() " +
			" WHERE id = #{id}")
	void refreshAccessTime(int id);

	@Update("UPDATE " +
			"  user_online_info " +
			" SET " +
			"    status=#{status}," +
			"    modified = NOW() " +
			" WHERE " +
			"       user_id=#{userId} " +
			"       AND type=#{type}")
	void updateStatusByUser(
			@Param("userId") int userId,
			@Param("type") int type,
			@Param("status") int status);

	@Update("UPDATE " +
			"   user_online_info " +
			" SET " +
			"    status=#{status}," +
			"    modified = NOW() " +
			" WHERE " +
			"    access_time<#{accessTime}")
	void offlineByTime(
			@Param("status") int stauts,
			@Param("accessTime") String accessTime);

	@Update("UPDATE" +
			"    user_online_info " +
			" SET " +
			"    channel=#{channel}," +
			"    modified = NOW() " +
			" WHERE " +
			"   user_id=#{userId} " +
			"   AND type=#{type}")
	int addChannelByToken(
			@Param("userId") int userId,
			@Param("type") int type,
			@Param("channel") String channel);

	@Select("select channel from user_online_info where user_id=#{userId} and type=#{type}")
	String getChannelByUser(
            @Param("userId") int userId,
            @Param("type") int type);
	
}
