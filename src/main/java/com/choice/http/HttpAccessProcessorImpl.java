package com.choice.http;

import com.choice.servlet.Servlet;
import com.choice.utils.ErrorPageUtils;
import com.choice.utils.MIMEUtils;
import com.choice.utils.ServletMappingUtils;


//Http资源处理器
//负责处理http协议的资源请求
public class  HttpAccessProcessorImpl implements HttpAccessProcessor  {
	//处理静态资源  页面/文件/图片等等
	public void processStaticResource(HttpRequest request,HttpResponse response)
	{
		System.out.println("进入方法processStaticResource(HttpRequest request,HttpResponse response)");
		//获取请求中资源，并处理
        String path=request.getRequestPath();
        String[] str=path.split("[.]");
        String contentType=str[str.length-1];
        System.out.println("路径的后缀："+contentType);
		response.setStatusLine(200);
		response.setContentType(MIMEUtils.getMimeValue(contentType));
		response.setCRLF();
		response.printResponseContent(request.getRequestPath());
	}
	//处理动态资源  java代码 浏览器通过路径发送请求可以访问调用java代码
	public void processDynamicResource(HttpRequest request,HttpResponse response)
	{
		System.out.println("进入processDynamicResource");
		response.setStatusLine(200);
		response.setContentType("text/HTML");
		response.setCRLF();
        Class className=null;
		try {
			String path=ServletMappingUtils.getMappingValue(request.getRequestPath());
			System.out.println("使用反射获取的servlet路径："+path);
			//根据路径找类
			className = (Class.forName(path));
			//ServletImpl  servlet= (ServletImpl) className.newInstance();
			Servlet servlet = (Servlet) className.newInstance();
			System.out.println(servlet);
			servlet.service(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}	
	//向浏览器返回错误信息及其页面
	public void sendError(int statusCode,HttpRequest request,HttpResponse response)
	{
		  System.out.println("进入sendError()");
			//获取请求中资源，并处理
			//response.setStatusLine("OK");
			response.setStatusLine(statusCode);
			//response.setStatusLine("OK");
			response.setContentType("text/html");
			response.setCRLF();
			//response.printResponseHeader();
			//response.printResponseContent("/error/404.html");
			response.printResponseContent(ErrorPageUtils.getErrorPage(statusCode+""));
		  
	}
	
}
