package com.choice.servlet;

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

    private String typeParm;
    private String message;


    public MyJson(String typeParm, String message) {
        this.typeParm = typeParm;
        this.message = message;
    }

    public String getTypeParm() {
        return typeParm;
    }

    public String typeAndMassageJSON(){
        String json = "";
        json += "{";
        json += "\"" + "type" + "\"" + ":" + "\"" + typeParm + "\"";
        if (!this.message.equals("")) {
            json += "," + "\"" + "message" + "\"" + ":" + "\"" + message + "\"";
        }
        json += "}";
        System.out.println(json + "&*&*&******************************************");
        return json;
    }

}
