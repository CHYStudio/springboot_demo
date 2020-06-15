package com.springboot_demo.common.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultMap
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/11
 * @Version V1.0
 **/
public class DefaultMap {

    private static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
    public static void setParameter(String key, Object o) {
        map.put(key, o);
    }

    public static ConcurrentHashMap<String, Object> getMap() {
        return DefaultMap.map;
    }

    public static Object get(String key) {
        return map.get(key);
    }

}
