package com.sample.servlet;

import java.io.PrintWriter;

import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;

//只有实现这个接口的类,才可以被浏览器发送请求访问到
public class HelloWorldServlet implements Servlet{
	//被浏览器发请求访问到的对象会调用这个指定方法service,进行处理这次浏览器的请求
	public void service(HttpRequest request,HttpResponse response)
	{
		String name=request.getParameter("name");
		System.out.println(name);
		try {
			PrintWriter pw=response.getPrintWriter();
			pw.println("<html>");
			pw.println("<body>");
			pw.println("<center>"+name+":这是我的Servlet</center>");
			pw.println("</body>");
			pw.println("</html>");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
