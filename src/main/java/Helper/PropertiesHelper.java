package Helper;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ResourceBundle;
/**
 * 对资源文件的修改，添加
 */
public class PropertiesHelper {
    static String path;

    public PropertiesHelper() {}
    public PropertiesHelper(String path2) {
        this.path=path2;
    }



    public static String getPath() {
        return path;
    }
    public static void setPath(String path) {
        PropertiesHelper.path = path;
    }
    public static void main(String[] args) {
        //System.out.println(getMessage("dbport"));
        updateProperies("jdbcurl;::;:;::;jdbc:oracle:thin:@;::;1", "12221.111.111.111");
        //updateProperies("jdbcurl;::;:;::;jdbc:oracle:thin:@;::;2", "1522");
    }
    /**
     * 修改/添加AutoAnalysisTime.properties资源文件中键值对;
     * 如果K值原先存在则，修改该K值对应的value值;
     * 如果K值原先不存在则，添加该键值对到资源中.
     * @param key
     * @param value
     */
    public static void updateProperies(String key,String value){
        System.out.println("no");
        System.out.println(key+":    "+value);
        System.out.println("path:"+"C:\\Users\\mengq\\Desktop\\CSDN_WebServer\\src\\com\\choice\\utils\\Test.properties");
        File file = new File("src\\test\\Test.properties");
        Properties prop = new Properties();
        InputStream inputFile = null;
        OutputStream outputFile = null;
        try {
            inputFile = new FileInputStream(file);
            prop.load(inputFile);
            inputFile.close();//一定要在修改值之前关闭inputFile
            outputFile = new FileOutputStream(file);
            //设值-保存
            prop.setProperty(key, value);
            //添加注释
            prop.store(outputFile, "Update '" + key + "'+ '"+value);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String getMessage(String configName) {
        ResourceBundle resource= ResourceBundle.getBundle("com\\choice\\utils\\Test");//test为属性文件名，放在包com.mmq下，如果是放在src下，直接用test即可

        return resource.getString(configName);

    }

}



