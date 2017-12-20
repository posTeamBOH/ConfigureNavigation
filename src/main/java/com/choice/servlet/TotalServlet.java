package com.choice.servlet;

import java.util.Map;

import com.choice.http.HttpRequest;
import com.choice.http.HttpResponse;
import Helper.ModityConfigHelper;

public class TotalServlet implements Servlet{

		@Override
		public void service(HttpRequest request, HttpResponse response) {
			//获取处理后的参数
			System.out.println("进入获取数据");
			Map<String, String> map=request.getParameterMap();
			MyJson myJson = ModityConfigHelper.isPing(map);
			response.printResponseData(myJson.typeAndMassageJSON());
		}
}
		
	

