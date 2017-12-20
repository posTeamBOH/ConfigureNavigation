package com.choice.utils;

import Helper.XmlToHtmlJsResolver;
import org.choice.dom4j.Document;
import org.choice.dom4j.DocumentException;
import org.choice.dom4j.Element;
import org.choice.dom4j.io.SAXReader;

import java.io.InputStream;

public class ServerPortUtils {
	private static String ip;
	private static String port;
	static 
	{
		try {
			SAXReader reader = new SAXReader();
			InputStream inputStream = Object.class.getResourceAsStream("/ChoiceNavigation.xml");
			XmlToHtmlJsResolver.initSystem();
			Document document = reader.read(inputStream);
			Element ipElement = (Element) document.selectNodes("/steps/ip").get(0);
			Element portElement = (Element) document.selectNodes("/steps/port").get(0);
			ip = ipElement.getText();
			port = portElement.getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static String getIp() {
		return ip;
	}

	public static String getPort() {
		return port;
	}

	public static void main(String[] args) {//输出测试

//		System.out.println(ServerPortUtils.getIp());
//		System.out.println(ServerPortUtils.getPort());
//		System.out.println("hah");
	}
}
