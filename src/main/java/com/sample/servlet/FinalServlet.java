package com.sample.servlet;

import java.util.Map;

import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;
import util.ModityConfig;

public class FinalServlet implements Servlet{
	
@Override
public void service(HttpRequest request, HttpResponse response) {
	Map<String, String> parm = ModityConfig.processData(request.getParameterMap());
	for (String key : parm.keySet()) {
		ModityConfig.updateMaps(key, parm.get(key));
	}
	ModityConfig.update();
	//ModityConfig.prinMaps();
	//重定向到结束页面
	response.printResponseData("/last.html");
}

}
