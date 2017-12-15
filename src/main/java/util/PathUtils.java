package util;

import org.choice.dom4j.Document;
import org.choice.dom4j.DocumentException;
import org.choice.dom4j.Element;
import org.choice.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PathUtils {

	  public static String getPath() {
		  String pathName = "";
		  Map<String, String> MAPS = new HashMap<String,String>();
		  SAXReader reader = new SAXReader();
		  try {
			  //读入开发者的配置xml文件
			  InputStream urlabs = Object.class.getResourceAsStream("/ChoiceNavigation.xml");
			  initSystem();
			  Document document = reader.read(urlabs);


			  List location = document.selectNodes("/steps/location");

			  //得到开发人员配置文件位置
			  //((Element) location.get(0)).getText()
			  pathName = ((Element) location.get(0)).getText();

		  } catch (DocumentException e) {
			  e.printStackTrace();
		  }
		  return pathName;
	  }
	  private static void initSystem() {
		  Properties properties = new Properties();
		  InputStream inputStream = XmlToHtmlJs.class.getResourceAsStream("choicedom4j.properties");
		  try {
			  properties.load(inputStream);
			  for(Map.Entry map :properties.entrySet()){
				  System.setProperty((String)map.getKey(),(String)map.getValue());
			  }
		  } catch (IOException e) {
		  }
	  }
}
