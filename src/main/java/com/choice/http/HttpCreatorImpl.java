package com.choice.http;

import java.net.Socket;

//负责创建http协议访问过程中使用到的对象
public class HttpCreatorImpl  implements HttpCreator{
	private Socket s;
	HttpRequestImpl request;
	HttpResponseImpl response;
	HttpAccessProcessorImpl hapi;
	public HttpCreatorImpl(Socket s) {
			this.s=s;
	}
	//返回创建好的request对象
	public HttpRequest getHttpRequest()
	{
         return request=new HttpRequestImpl(s);
	}
	
	//返回创建好的response对象
	public HttpResponse getHttpResponse()
	{
		  return response=new HttpResponseImpl(s);
	}
	
	//返回创建好的HttpAccessProcessor对象
	public HttpAccessProcessor getHttpAccessProcessor()
	{
		 return hapi=new HttpAccessProcessorImpl();
	}
}
