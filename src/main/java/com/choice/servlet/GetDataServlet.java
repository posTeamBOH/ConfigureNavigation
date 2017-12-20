package com.choice.servlet;
import Helper.ModityConfigHelper;
import com.choice.http.HttpRequest;
import com.choice.http.HttpResponse;

public class GetDataServlet implements Servlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("进入到取数据...");
        String json =MyJson.mapToJSON(ModityConfigHelper.getValue(request.getParameterMap()));
        response.printResponseData(json);
    }
}
