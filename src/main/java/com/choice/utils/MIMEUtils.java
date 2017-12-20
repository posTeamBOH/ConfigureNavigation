package com.choice.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class MIMEUtils {
	private static Properties p;
	static 
	{
		InputStream in=null;
		p=new Properties();
		try {
			//读了xx.properties文件
			in=MIMEUtils.class.getResourceAsStream("mime.properties");
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
	public static String getMimeValue(String mime)
	{ 
		return p.getProperty(mime);
	}
	public static void main(String[] args) {//输出测试
		System.out.println(getMimeValue("mp4"));
	}
}
