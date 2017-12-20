package Helper;


import com.choice.servlet.MyJson;

import java.io.*;
import java.util.*;

/**
 * 修改配置文件
 */

public class ModityConfigHelper {
    static String pathName = PathHelper.getPath();
    /**
     *将修改保存到配置文件
     */
    public static boolean update(Map<String, String> maps){
        Properties prop = new Properties();
        InputStream inputFile;
        OutputStream outputFile;
        try {
            inputFile = new FileInputStream(new File(pathName));
            prop.load(inputFile);
            outputFile = new FileOutputStream(new File(pathName));
            //设值-保存
            for (String key : maps.keySet()) {
                String value = maps.get(key);
                prop.setProperty(key, value);

            }
            prop.store(outputFile, "Update" );
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 得到value(先从缓存里取，再从文件中取)
     */
    public static Map<String, String> getValue(Map<String, String> parameterMap) {
        Properties prop = new Properties();
        InputStream inputFile;
        Map<String, String> returnValues = new HashMap<>();
        try {
            inputFile = new FileInputStream(new File(pathName));
            prop.load(inputFile);
            for (String keyInitial : parameterMap.keySet()) {
                //处理有ping的
                String keyInitialCopy = keyInitial;
                String[] arg0 = keyInitial.split(";##;");
                keyInitial = arg0[0];

                String key;
                int number = 0;
                String head = "";
                String rule = "";
                String[] keyArg0 = keyInitial.split(";##;");
                keyInitial = keyArg0[0];
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

                Object object = prop.get(key);
                String values = (object == null) ? "" : (String) object;
                if (keyInitial.contains(";::;") &&  !values.equals("")) {
                    String v;
                    if (head.equals("null")) {
                        v = values.substring(0, values.length());
                    } else {
                        v = values.substring(head.length(), values.length());
                    }
                    String[] vs = v.split(rule);
                    values = vs[number];

                }
                returnValues.put(keyInitialCopy, values);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnValues;

    }

    /**
     * 处理存数据时候的参数
     */
    public static Map<String, String> processData(Map<String, String> initial) {
        Map<String, String> trueData = new HashMap<>();


        //处理有ping的
        Map<String, String> ini = new HashMap<>();
        for (String key : initial.keySet()) {
            String value = initial.get(key);
            String[] arg0 = key.split(";##;");
            ini.put(arg0[0], value);
        }

        initial = ini;

        /**
         * 因为HashMap无序，为保证拼接时候正确，所以得先排序
         */
        List<String> keyList = new ArrayList(initial.keySet());
        //对key键值按字典升序排序
        Collections.sort(keyList);

        for (int keyi = 0; keyi < keyList.size(); keyi++) {
            String key = keyList.get(keyi);
            System.out.println(key+"hheh");
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
                System.out.println(key + "hahaha");
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


    //参数校验
    public static MyJson isPing(Map<String, String> map) {
        Map<String, PingHelper> map1=new HashMap<>();
        for(String key : map.keySet()) {
            if (key.contains(";##;")) {
                System.out.println("包含");
                String[] ks = key.split(";##;");
                String name = ks[1];
                String no = ks[2];
                PingHelper inner = null;
                inner = (map1.get(name) == null) ? new PingHelper() : map1.get(name);
                if (no.equals("0")) {
                    inner.setIp(map.get(key));
                } else {

                    inner.setPort(map.get(key));
                }
                map1.put(name, inner);
            }
        }

        MyJson myJson;
        for (String key : map1.keySet()) {
            PingHelper pingUtil = map1.get(key);
            if (!pingUtil.isHostConnectable()) {
                //ping失败,发送false字符串
                myJson = new MyJson("0", pingUtil.getIp() + "和" + pingUtil.getPort() + "ping不成功");
                return myJson;
            }
        }
        myJson = new MyJson("1", "");
        return myJson;
    }


    public static void main(String[] args) {
        // ModityConfigHelper.processData();
        //System.out.println(ModityConfigHelper.getValues("jdbcurl;::;:;::;jdbc:oracle:thin:@;::;0"));
    }


}
