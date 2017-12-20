package com.choice.http;
import java.io.OutputStream;
import java.io.PrintWriter;
//http协议的响应
public interface HttpResponse {
	//获得一个指向客户端的字节流
	public OutputStream getOutputStream()throws Exception;
	//获得一个指向客户端的字符流
	public PrintWriter getPrintWriter()throws Exception;
	//设置响应的状态行 参数为String类型
	public void setStatusLine(String statusCode);
	//设置响应的状态行 参数为int类型
	public void setStatusLine(int statusCode);
	//设置响应消息报头
	public void setResponseHeader(String hName, String hValue);
	//设置响应消息报头中Content-Type属性
	public void setContentType(String contentType);
	//设置响应消息报头中Content-Type属性 并且同时设置编码
	public void setContentType(String contentType, String charsetName);
	//设置CRLF 回车换行  \r\n
	public void setCRLF();
	//把设置好的响应状态行、响应消息报头、固定空行这三部分写给浏览器
	public void printResponseHeader();
	//把响应正文写给浏览器
	public void printResponseContent(String requestPath);

	//重定向
	public void sendRedirect(String pathName);
	//传输数据
	public void printResponseData(String data);
	
}