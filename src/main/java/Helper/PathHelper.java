package Helper;

import org.choice.dom4j.Document;
import org.choice.dom4j.DocumentException;
import org.choice.dom4j.Element;
import org.choice.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathHelper {

	  public static String getPath() {
		  String pathName = "";
		  Map<String, String> MAPS = new HashMap<String,String>();
		  SAXReader reader = new SAXReader();
		  try {
			  //读入开发者的配置xml文件
			  InputStream urlabs = Object.class.getResourceAsStream("/ChoiceNavigation.xml");
			  XmlToHtmlJsResolver.initSystem();
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



}
