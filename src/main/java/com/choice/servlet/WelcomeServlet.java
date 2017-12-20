package com.choice.servlet;

import com.choice.http.HttpRequest;
import com.choice.http.HttpResponse;

public class WelcomeServlet implements Servlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {

        String path = "/knn.html";
        response.printResponseData(path);
    }
}
