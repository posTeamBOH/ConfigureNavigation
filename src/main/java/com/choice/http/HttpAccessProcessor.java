package com.choice.http;

//Http资源处理器
//负责处理http协议的资源请求
public interface HttpAccessProcessor {
	
	//处理静态资源  页面/文件/图片等等
	public void processStaticResource(HttpRequest request, HttpResponse response);
	
	//处理动态资源  java代码 浏览器通过路径发送请求可以访问调用java代码
	public void processDynamicResource(HttpRequest request, HttpResponse response);
	
	//向浏览器返回错误信息及其页面
	public void sendError(int statusCode, HttpRequest request, HttpResponse response);
	
}
