package com.lewis.master.common.utils;

import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangminghua on 2016/11/10.
 */
public class MapUtil extends MapUtils {

    private MapUtil() {

    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    public static <K,V> boolean isNotEmpty(Map<K,V> map){
        return !isEmpty(map);
    }

    public static <K,V> Map<K,V> newHashMap(int size){
        return new HashMap<K, V>(size);
    }

    public static <K,V> Map<K,V> newHashMap(){
        return new HashMap<K, V>();
    }
}
