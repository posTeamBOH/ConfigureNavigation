package com.choice.utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
public class ConfigUtils {
	private static Properties p;
	static 
	{
		InputStream in=null;
		OutputStream on=null;
		p=new Properties();
		try {
			//读了xx.properties文件
			in=ConfigUtils.class.getResourceAsStream("config.properties");
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
	public static String getConfigValue(String config)
	{ 
		return p.getProperty(config);
	}

}