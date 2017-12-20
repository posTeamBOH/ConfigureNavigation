package com.choice.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class ErrorPageUtils {
	private static Properties p;
	static 
	{
		InputStream in=null;
		p=new Properties();
		try {
			//读了xx.properties文件
			in=ErrorPageUtils.class.getResourceAsStream("error_page.properties");
			//放置到p中，即放properties文件中的key,value
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public static String getErrorPage(String statusCode)
	{ 
		return p.getProperty(statusCode);
	}
	public static void main(String[] args) {//输出测试
	//	Properties p=new Properties();
	//	p.setProperty("rootPath","ddd");
	//	System.out.println(p.get("rootPath"));
		System.out.println(getErrorPage(404+""));
	}
}
