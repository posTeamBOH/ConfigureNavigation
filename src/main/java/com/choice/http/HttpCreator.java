package com.choice.http;

//负责创建http协议访问过程中使用到的对象
public interface HttpCreator {
	//返回创建好的request对象
	public HttpRequest getHttpRequest();
	
	//返回创建好的response对象
	public HttpResponse getHttpResponse();
	
	//返回创建好的HttpAccessProcessor对象
	public HttpAccessProcessor getHttpAccessProcessor();
}
