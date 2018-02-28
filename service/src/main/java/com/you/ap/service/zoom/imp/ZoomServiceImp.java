package com.you.ap.service.zoom.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.you.ap.common.helper.GetUTCTimeUtil;
import com.you.ap.common.helper.SendMessage;
import com.you.ap.service.zoom.IZoomService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomServiceImp implements IZoomService {
    @Override
    public JSONObject getInfo(int userId, int type) {
        JSONObject outJson = new JSONObject();
        Map map = new HashMap();
        map.put("type", "1");
        if (type == 0) {
            map.put("email", "stu" + userId + "@wooyostudy.com");
        } else {
            map.put("email", "tea" + userId + "@wooyostudy.com");
        }
        String url = "user/custcreate";
        try {
            String outcome = SendMessage.zoomPost(map, url);
            Map<String, String> getMap = new HashMap<>();
            JSONObject idjson = JSON.parseObject(outcome);
            getMap.put("id", idjson.getString("id"));
            String url2 = "user/get";
            outcome = SendMessage.zoomPost(getMap, url2);
            JSONObject infoJson = JSON.parseObject(outcome);
            outJson.put("id", infoJson.getString("id"));
            outJson.put("token", infoJson.getString("token"));
            outJson.put("zpk", infoJson.getString("zpk"));
            return outJson;
        } catch (IOException e) {
            outJson.put("failed",e.getMessage());
            return outJson;
        }
    }
    @Override
    public JSONObject getMeetingInfo(int userId, int type,String host_id) {
        JSONObject   jsonObject=getInfo(userId,type);
        String hostId=jsonObject.getString("id");
        if("".equals(hostId)||hostId==null){
            jsonObject.put("failed","no teacher zoom ID");
            return jsonObject;
        }
        Map<String, String> getMap = new HashMap<>();
        getMap.put("type","2");
        getMap.put("start_time", GetUTCTimeUtil.getUTCTimeStr());
        getMap.put("option_auto_record_type","true");
        getMap.put("option_cn_meeting","true");
        getMap.put("option_jbh","true");
        getMap.put("option_registration","true");
        getMap.put("host_id",host_id);
        getMap.put("topic","wooyo study");
        String url="meeting/create";
        try {
            String outcome =SendMessage.zoomPost(getMap, url);
            JSONObject jsonObject1=JSONObject.parseObject(outcome);
            jsonObject.put("meeting_id",jsonObject1.getString("id"));
        } catch (IOException e) {
            jsonObject.put("failed",e.getMessage());
            return jsonObject;
        }
        return jsonObject;
    }
}
