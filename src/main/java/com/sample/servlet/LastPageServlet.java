package com.sample.servlet;

import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;

import java.util.Map;

/**
 * 上一页负责页面的跳转
 */

public class LastPageServlet implements Servlet{


    @Override
    public void service(HttpRequest request, HttpResponse response) {
        //转发
        Map<String, String> p = request.getRequestHeader();
        String urlString = p.get("Referer");
        String[] arg0 = urlString.split("/");
        String[] arg03 = arg0[3].split("\\.");
        int i = arg03[0].charAt(arg03[0].length()-1) - '0' - 1;
        String urlNew = arg03[0].substring(0, arg03[0].length()-1) + i + "." + arg03[1];
        //response.printResponseContent(urlNew);

        //重定向
        System.out.println("重定向" + urlNew);
        //response.sendRedirect(urlNew);

        response.printResponseData(urlNew);
    }
}
