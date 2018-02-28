package com.you.ap.dao;

import com.you.ap.domain.schema.book.LocationDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;



public interface ILocationDAO {
    @Select("select id,parent_id,location_name from location where parent_id=#{parentId}")
    public List<LocationDO> getLocationByParent(@Param("parentId") int parentId);

    @Select("select id,parent_id,location_name from location where id=#{id}")
    LocationDO getLocationById(@Param("id") int id);

    @Select("select id,parent_id,location_name from location where parent_id is null")
    public List<LocationDO> getRootLocation();
}
