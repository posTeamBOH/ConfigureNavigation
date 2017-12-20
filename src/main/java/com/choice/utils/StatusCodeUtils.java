package com.choice.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class StatusCodeUtils {
	private static Properties p;
	static 
	{
		InputStream in=null;
		p=new Properties();
		try {
			//读了xx.properties文件
			in=StatusCodeUtils.class.getResourceAsStream("status_code.properties");
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
	public static String getStatusCodeValue(String status)
	{ 
		return p.getProperty(status);
	}
	public static String getStatusCodeValue(int status)
	{ 
		return getStatusCodeValue(status+"");//没有空格
	}
	/*public static void main(String[] args) {//输出测试
	//Properties p=new Properties();
	// p.setProperty("rootPath","ddd");
	//System.out.println(p.get("rootPath"));
		System.out.println(getStatusCodeValue("304"));
		System.out.println(getStatusCodeValue("200"));
	}*/
}