package com.sample.servlet;
import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;
import util.ModityConfig;

import java.util.Map;

public class GetDataServlet implements Servlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("进入到取数据...");
        String json =MyJson.mapToJSON(ModityConfig.getValue(request.getParameterMap()));
        response.printResponseData(json);
    }
}
