package com.zhubch.dbmusicer.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubch on 1/18/16.
 */
public class MapUtils {
    static public Map<String,Object> asMap(Object ...objects){
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < objects.length; i+=2) {
            map.put((String)objects[i],objects[i+1]);
        }
        return map;
    }
}
