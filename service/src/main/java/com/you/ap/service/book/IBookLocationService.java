package com.you.ap.service.book;

import com.alibaba.fastjson.JSONObject;
import com.you.ap.domain.model.book.LocationModel;
import com.you.ap.domain.schema.book.LocationDO;
import com.you.ap.domain.vo.ApiResponse;

import java.util.List;

public interface IBookLocationService {

    LocationModel getLocationByChild(int childId);

    JSONObject getLocationJson();

    JSONObject getReversonJson(List<LocationModel> locationDOList);

}
