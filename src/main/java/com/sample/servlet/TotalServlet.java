package com.sample.servlet;

import java.util.Map;

import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;
import util.ModityConfig;

public class TotalServlet implements Servlet{

		@Override
		public void service(HttpRequest request, HttpResponse response) {
			//获取处理后的参数
			System.out.println("进入获取数据");
			Map<String, String> map=request.getParameterMap();
			MyJson myJson = ModityConfig.isPing(map);
			response.printResponseData(myJson.typeAndMassageJSON());
		}
}
		
	

