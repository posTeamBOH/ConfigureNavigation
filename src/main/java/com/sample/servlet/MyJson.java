package com.sample.servlet;

import java.util.Map;

/**
 * 转换
 */
public class MyJson {
    public static String mapToJSON(Map<String, String> map) {
        String json = "{";
        int i = 0;
        for (String key : map.keySet()) {
            if (i == 0) {
                json += "\"" + key + "\"" + ":" + "\"" + map.get(key) + "\"";
            } else {
                json += "," + "\"" + key + "\"" + ":" + "\"" + map.get(key) + "\"";
            }
            i++;
        }
        json += "}";
        System.out.println(json + "json");
        return json;
    }
}
