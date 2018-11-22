package com.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/** 
 * Java对象和JSON字符串相互转化工具类
 */  
public final class JsonUtil {  
      
    private JsonUtil(){}  
      
    /** 
     * 对象转换成json字符串 
     * @param obj  
     * @return  
     */  
    public static String toJson(Object obj) {
        JSONObject json = new JSONObject();
        return json.toJSONString(obj);
    }

    /**
     * json字符串转成对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String str,Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }
}  