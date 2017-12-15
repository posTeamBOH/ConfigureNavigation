package com.sample.servlet;

import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;
import util.XmlToHtmlJs;

import java.io.File;

public class WelcomeServlet implements Servlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {

        String path = "/modify1.html";
        String parent = System.getProperty("user.dir")+File.separator+"navigationHtml";
        File file = new File(parent, path);
        if(!(file.exists() && file.isFile())) {
            new XmlToHtmlJs().analysisXml();
        }
        response.printResponseData(path);
    }
}
