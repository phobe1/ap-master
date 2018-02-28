package com.you.ap.service.zoom;


import com.alibaba.fastjson.JSONObject;
import com.you.ap.domain.vo.ApiResponse;

public interface IZoomService {

	JSONObject getInfo(int userId, int type);



	JSONObject getMeetingInfo(int userId, int type,String host_id);


}
