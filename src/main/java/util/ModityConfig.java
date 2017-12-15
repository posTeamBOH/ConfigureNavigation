package util;


import java.io.*;
import java.util.*;

/**
 * 修改配置文件
 */

public class ModityConfig {
    static String pathName = PathUtils.getPath();
    static HashMap<String, String> MAPS = new HashMap<String,String>();
    public static void updateMaps(String key, String values) {
        MAPS.put(key, values);
    }

    public static String getPathName() {
        return pathName;
    }
    /**
     *将修改保存到配置文件
     */
    public static void update(){
        Properties prop = new Properties();
        InputStream inputFile;
        OutputStream outputFile;
        try {
            inputFile = new FileInputStream(new File(pathName));
            prop.load(inputFile);
            outputFile = new FileOutputStream(new File(pathName));
            //设值-保存
            for (String key : MAPS.keySet()) {
                String value = MAPS.get(key);
                prop.setProperty(key, value);

            }
            prop.store(outputFile, "Update" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearMaps();
    }

    /**
     * 清楚map缓存
     */
    public static void clearMaps(){
        MAPS.clear();
    }

    public static void prinMaps(){
        for (String key : MAPS.keySet()) {
            System.out.println(key + " : " + MAPS.get(key));
        }
    }

    /**
     * 得到value(先从缓存里取，再从文件中取)
     */
    public static Map<String, String> getValue(Map<String, String> parameterMap) {
        Properties prop = new Properties();
        InputStream inputFile;
        int i = 0;
        Map<String, String> returnValues = new HashMap<String,String>();
        try {
            inputFile = new FileInputStream(new File(pathName));
            prop.load(inputFile);
            for (String keyInitial : parameterMap.keySet()) {
                String key;
                int number = 0;
                String head = "";
                String rule = "";
                if (keyInitial.contains(";::;")) {
                    String[] keys = keyInitial.split(";::;");
                    key = keys[0];
                    rule = keys[1];
                    head = keys[2];
                    number = Integer.parseInt(keys[3]);
                } else if(keyInitial.contains(";**;")){
                    String[] keys = keyInitial.split(";[*][*];");
                    key = keys[0];
                }else {
                    key = keyInitial;

                }

                String values = "";
                if (MAPS.get(key) != null) {
                    values = MAPS.get(key);
                }else{
                    Object object = prop.get(key);
                    values = (object == null) ? "" : (String) object;
                }
                if (keyInitial.contains(";::;") &&  !values.equals("")) {
                    String v;
                    if (head.equals("null")) {
                        v = values.substring(0, values.length());
                    } else {
                        v = values.substring(head.length(), values.length());
                    }
                    System.out.println("v090909   " + v);
                    String[] vs = v.split(rule);
                    if(vs.length>number)
                        values = vs[number];

                }
                returnValues.put(keyInitial, values);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnValues;

    }

    /**
     * 处理参数
     */
    public static Map<String, String> processData(Map<String, String> initial) {
        Map<String, String> trueData = new HashMap<String,String>();


        /**
         * 因为HashMap无序，为保证拼接时候正确，所以得先排序
         */
        List<String> keyList = new ArrayList(initial.keySet());
        //对key键值按字典升序排序
        Collections.sort(keyList);

        for (int keyi = 0; keyi < keyList.size(); keyi++) {
            String key = keyList.get(keyi);
            if(key.contains(";**;")) {
                String[] k = key.split(";[*][*];");
                Map<String, String> m=new HashMap<String, String>();
                for(int i=0;i<k.length;i++) {
                    m.put(k[i], null);
                }
                Map<String, String> m2= getValue(m);
                String base=m2.get(k[0]);//原先base的值
                for(int i=0;i<k.length;i++) {
                    trueData.put(k[i], m2.get(k[i]).replace(base, initial.get(key)));
                }
            } else if (key.contains(";::;")) {
                String[] keys = key.split(";::;");
                String id = keys[0];
                String rule = keys[1];
                String head = keys[2];
                String number = keys[3];
                if (number.equals("0")) {
                    if (head.equals("null")) {
                        head = "";
                    }
                    trueData.put(id, head + initial.get(key));
                } else {
                    trueData.put(id, trueData.get(id) +  rule + initial.get(key));
                }
            } else {
                trueData.put(key, initial.get(key));
            }
        }
        for (String i : trueData.keySet()) {
            System.out.println(i + "====" + trueData.get(i));
        }
        return trueData;
    }


    public static void main(String[] args) {
        // ModityConfig.processData();
        //System.out.println(ModityConfig.getValues("jdbcurl;::;:;::;jdbc:oracle:thin:@;::;0"));
    }
}
