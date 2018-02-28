package com.you.ap.service.book.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.you.ap.dao.ILocationDAO;
import com.you.ap.domain.model.book.LocationModel;
import com.you.ap.domain.schema.book.LocationDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.book.LocationVO;
import com.you.ap.domain.vo.book.ReverLocation;
import com.you.ap.service.book.IBookLocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookLocationServiceImp implements IBookLocationService {

    private Logger logger= Logger.getLogger(BookLocationServiceImp.class);

    @Autowired
    private ILocationDAO locationDAO;

    @Override
    public LocationModel getLocationByChild(int childId) {
        LocationDO locationDO = locationDAO.getLocationById(childId);
        if (locationDO == null){
            return null;
        }
        LocationModel locationModel = new LocationModel(locationDO);
        LocationModel locationModel1 = locationModel;
        while(locationDO!= null && locationDO.getParentId()>0){
            locationDO = locationDAO.getLocationById(locationDO.getParentId());
            locationModel=locationModel.addParent(locationDO);
        }
        return locationModel1;
    }

    @Override
    public  JSONObject getLocationJson() {
        List<LocationDO> roots = locationDAO.getRootLocation();
        JSONObject jsonObject = new JSONObject();
        HashMap<String, HashMap> map = new HashMap<String, HashMap>();
        for (LocationDO root : roots) {

            List<LocationDO> middles = locationDAO.getLocationByParent(root.getId());
            HashMap<String, HashMap> middlemap = new HashMap<String, HashMap>();
            for (LocationDO middle : middles) {
                HashMap<String,Integer> endmap = new HashMap<String, Integer>();;
                List<LocationDO> ends = locationDAO.getLocationByParent(middle.getId());
                for (LocationDO end : ends) {

                    endmap.put(end.getLocationName(),end.getId());

                }

                middlemap.put(middle.getLocationName(), endmap);
            }

            map.put(root.getLocationName(), middlemap);

        }
        jsonObject.putAll(map);
        return jsonObject;
    }

    @Override
    public JSONObject getReversonJson(List<LocationModel> locationDOList) {

        Map<LocationModel,Set<LocationModel>> localtion1=new HashMap<LocationModel,Set<LocationModel>>();
        Map<Integer,Set<LocationModel>> localtion2=new HashMap<Integer,Set<LocationModel>>();

        for(LocationModel locationDO:locationDOList){
            if(localtion2.containsKey(locationDO.getParent().getId())){
                localtion2.get(locationDO.getParent().getId()).add(locationDO);

            }else{
                Set set=new HashSet();
                set.add(locationDO);
                localtion2.put(locationDO.getParent().getId(),set);
            }
            if(localtion1.containsKey(locationDO.getParent().getParent())){
                localtion1.get(locationDO.getParent().getParent()).add(locationDO.getParent());
            }else {
                Set set=new HashSet();
                set.add(locationDO.getParent());
                localtion1.put(locationDO.getParent().getParent(),set);
            }

        }

        Map<String,Map<LocationModel,Set<LocationModel>>> finalNap=new HashMap<>();

        for(LocationModel locationModel:localtion1.keySet()){
            Set<LocationModel> set1=localtion1.get(locationModel);
            if(!finalNap.containsKey(locationModel.getName())){
                finalNap.put(locationModel.getName(),new HashMap<LocationModel,Set<LocationModel>>());
            }
            for(LocationModel locationModel2:set1){
                Set<LocationModel> set2=localtion2.get(locationModel2.getId());
                Map<LocationModel,Set<LocationModel>> map=finalNap.get(locationModel.getName());
                if(map==null){
                    map=new HashMap<>();
                }
                map.put(locationModel2,set2);
                finalNap.put(locationModel.getName(),map);
            }
        }
        ReverLocation[] out=new ReverLocation[finalNap.size()];
        int k=0;
        for(String key:finalNap.keySet()){
            ReverLocation rs=new ReverLocation();
            rs.setName(key);
            rs.setId(0);
            ReverLocation[] array=new ReverLocation[finalNap.size()];
            Map<LocationModel,Set<LocationModel>> map=finalNap.get(key);
            int i=0;
            for(LocationModel locationModel:map.keySet()){
                ReverLocation rl=new ReverLocation();
                rl.setId(locationModel.getId());
                rl.setName(locationModel.getName());
                ReverLocation[] inarray=new ReverLocation[map.size()];
                int j=0;
                for(LocationModel ll:map.get(locationModel)){
                    ReverLocation l=new ReverLocation();
                    l.setName(ll.getName());
                    l.setId(ll.getId());
                    inarray[j++]=l;
                }
                rl.setSon(inarray);
                array[i++]=rl;
            }
            rs.setSon(array);
            out[k++]=rs;
        }

        String s= JSON.toJSONString(out);

        JSONObject js=new JSONObject();

        js.put("data",s);

        return js;
    }

}

