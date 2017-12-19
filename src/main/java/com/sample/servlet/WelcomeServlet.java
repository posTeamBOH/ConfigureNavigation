package com.sample.servlet;

import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;
import util.XmlToHtmlJs;

import java.io.File;

public class WelcomeServlet implements Servlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {

        String path = "/knn.html";
        response.printResponseData(path);
    }
}
