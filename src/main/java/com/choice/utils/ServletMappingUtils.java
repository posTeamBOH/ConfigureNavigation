package com.choice.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class ServletMappingUtils {
	private static Properties p;
	static 
	{
		InputStream in=null;
		p=new Properties();
		try {
			//读了xx.properties文件
			in=ServletMappingUtils.class.getResourceAsStream("servlet_mapping.properties");
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
	public static String getMappingValue(String mapping)
	{ 
		return p.getProperty(mapping);
	}
	public static  boolean isContainsKey(String key) {
          return p.containsKey(key);
	}
	
}
