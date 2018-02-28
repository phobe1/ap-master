package com.you.ap.common.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

public class StringUtil {



    public static Set<Integer> splitStr(String source){
        try{
            Set<Integer> result = Sets.newHashSet();
            if (StringUtils.isEmpty(source)){
                return result;
            }
            for(String s:source.split(",")){
                try{
                    if (StringUtils.isEmpty(s)){
                        continue;
                    }
                    result.add(Integer.valueOf(s));
                }catch (Exception e){
                    continue;
                }
            }
            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String formate2Str(Set<Integer> nums){
        try{
            StringBuffer stringBuffer = new StringBuffer();
            if (CollectionUtils.isEmpty(nums)){
                return null;
            }
            nums.forEach(n->stringBuffer.append(n+","));
            return stringBuffer.toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
