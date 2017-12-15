package util;

import org.choice.dom4j.*;
import org.choice.dom4j.io.SAXReader;
import org.choice.dom4j.io.XMLWriter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * xml解析
 */

public class XmlToHtmlJs {
    public void analysisXml() {
        //写首页和尾页
        String urlPath = System.getProperty("user.dir")+File.separator+"navigationHtml"+File.separator;
        new File(urlPath).mkdirs();
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(new File(urlPath + "welcome.html"));
            String firstString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    " <meta charset=\"UTF-8\">\n" +
                    " <style>\n" +
                    "  .clearfix:after {\n" +
                    "   content: \"\";\n" +
                    "   clear: both;\n" +
                    "   display: block;\n" +
                    "  }\n" +
                    "\n" +
                    "  .container {\n" +
                    "   width: 300px;\n" +
                    "   margin: 30px auto;\n" +
                    "  }\n" +
                    "\n" +
                    "  .title {\n" +
                    "   text-align: center;\n" +
                    "\n" +
                    "  }\n" +
                    "\n" +
                    "  .row {\n" +
                    "   margin: 10px 0;\n" +
                    "  }\n" +
                    "\n" +
                    "  .row span {\n" +
                    "   float: left;\n" +
                    "  }\n" +
                    "\n" +
                    "  .row input {\n" +
                    "   float: right;\n" +
                    "  }\n" +
                    "\n" +
                    "  .bottom {\n" +
                    "   margin-top: 20px;\n" +
                    "   text-align: center;\n" +
                    "  }\n" +
                    "\n" +
                    "  .bottom button {\n" +
                    "   width: 60px;\n" +
                    "  }\n" +
                    " </style>\n" +
                    " </meta>\n" +
                    " <title>欢迎配置</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<div class=\"container\">\n" +
                    " <h2 class=\"title\">欢迎来到配置页面</h2>\n" +
                    "\n" +
                    " <div class=\"bottom\">\n" +
                    "  <button type=\"button\" id=\"next\">开始</button>\n" +
                    " </div>\n" +
                    "\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>\n" +
                    "<script>\n" +
                    "    function init() {\n" +
                    "\n" +
                    "        let next = document.getElementById('next');\n" +
                    "        let param = '';\n" +
                    "        //开始\n" +
                    "        function nextPage(param) {\n" +
                    "            let xmlhttp;\n" +
                    "            if (window.XMLHttpRequest) {\n" +
                    "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                    "                xmlhttp = new XMLHttpRequest();\n" +
                    "            } else {\n" +
                    "                // IE6, IE5 浏览器执行代码\n" +
                    "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                    "            }\n" +
                    "            xmlhttp.onreadystatechange = function() {\n" +
                    "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                    "\n" +
                    "                    let data = xmlhttp.responseText;\n" +
                    "                    console.log(data);\n" +
                    "                    window.location.href = data;\n" +
                    "                }\n" +
                    "            }\n" +
                    "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/welcome\", true);\n" +
                    "            xmlhttp.send(param);\n" +
                    "        }\n" +
                    "\n" +
                    "        //开始\n" +
                    "        next.onclick = function() {\n" +
                    "            nextPage(param);\n" +
                    "            console.log(param);\n" +
                    "        };\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    window.addEventListener(\"load\", init, false);\n" +
                    "</script>\n";
            fout.write(firstString.getBytes());
            fout.flush();

            fout = new FileOutputStream(new File(urlPath + "last.html"));
            firstString = "<!DOCTYPE html>\n" +
                    "<html >\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>配置结束</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>配置成功</h1>\n" +
                    "</body>\n" +
                    "</html>";
            fout.write(firstString.getBytes());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fout= null;
        }

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

            List stepsElement = document.selectNodes("/steps/step");
            //生成每一步页面
            for (int i = 0; i < stepsElement.size(); i++) {
                Element stepElement = (Element) stepsElement.get(i);

                String stepNameString = ((Attribute) stepElement.selectNodes("@name").get(0)).getValue();

                Document documentNewEach = DocumentHelper.createDocument();
                Element rootElement = documentNewEach.addElement("html");
                Element headElement = rootElement.addElement("head");
                Element metaElement = headElement.addElement("meta");
                //加入css样式
                Element styleElement = metaElement.addElement("style");
                styleElement.setText("\n.clearfix:after {\n" +
                        "            content: \"\";\n" +
                        "            clear: both;\n" +
                        "            display: block;\n" +
                        "        }\n" +
                        "\n" +
                        "        .container {\n" +
                        "            width: 300px;\n" +
                        "            margin: 30px auto;\n" +
                        "        }\n" +
                        "\n" +
                        "        .title {\n" +
                        "            text-align: center;\n" +
                        "\n" +
                        "        }\n" +
                        "\n" +
                        "        .row {\n" +
                        "            margin: 10px 0;\n" +
                        "        }\n" +
                        "\n" +
                        "        .row span {\n" +
                        "            float: left;\n" +
                        "        }\n" +
                        "\n" +
                        "        .row input {\n" +
                        "            float: right;\n" +
                        "        }\n" +
                        "\n" +
                        "        .bottom {\n" +
                        "            margin-top: 20px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "\n" +
                        "        .bottom button {\n" +
                        "            width: 60px;\n" +
                        "        }\n" +
                        "        .radioMain span{\n" +
                        "            float: left;\n" +
                        "        }\n" +
                        "        .radioMainRow div{\n" +
                        "            float: right;\n" +
                        "        }\n" +
                        "        .radioMainRow div input{\n" +
                        "            margin: 0 8px 0 8px;\n" +
                        "        }\n");
                metaElement.addAttribute("charset", "UTF-8");
                Element titleElement = headElement.addElement("title");
                titleElement.addText("配置第" + (i  + 1) + "步");

                Element bodyElement = rootElement.addElement("body");
                Element formElement = bodyElement.addElement("div");
                formElement.addAttribute("class", "container");
                Element h2Element = formElement.addElement("h2");
                h2Element.addAttribute("class", "title");
                h2Element.setText("配置第" + (i + 1) + "步：" + stepNameString);
                Element tableElement = formElement.addElement("div");

                tableElement.addAttribute("class", "main");
                //添加每一个输入框
                List textFieldElements = stepElement.selectNodes("textField");

                for (int texti = 0; texti < textFieldElements.size(); texti++){
                    Element trElement = tableElement.addElement("div");
                    trElement.addAttribute("class", "row clearfix");
                    Element textField = (Element) textFieldElements.get(texti);
                    String nameString = textField.getText();

                    Attribute id = (Attribute) textField.selectNodes("@id").get(0);
                    String idString = id.getValue();

                    //添加一行
                    Element tdElement = trElement.addElement("span");
                    tdElement.setText(nameString);
                    Element input = trElement.addElement("input");
                    input.addAttribute("type", "text");
                    input.addAttribute("id", idString);

                    //每一行的默认值
                    List valueTextFieldAttribute = textField.selectNodes("@value");
                    if (valueTextFieldAttribute.size() > 0) {
                        String valueTextField = ((Attribute) valueTextFieldAttribute.get(0)).getValue();
                      MAPS.put(idString, valueTextField);
                    }
                }
                //添加拼接
                List textsElement = stepElement.selectNodes("textFields");
                for (Object object : textsElement) {
                    Element text = (Element) object;
                    //得到拼接后名字和拼接规则
                    Attribute afterSpliceAttribute = (Attribute) text.selectNodes("@id").get(0);
                    String afterSplice = (afterSpliceAttribute != null) ? afterSpliceAttribute.getValue() : "";
                    Attribute ruleAttribute = (Attribute) text.selectNodes("@rule").get(0);
                    String rule = (ruleAttribute != null) ? ruleAttribute.getValue() : "";
                    Attribute headAttribute = (Attribute) text.selectNodes("@head").get(0);
                    String headString = (headAttribute != null && !headAttribute.getValue().equals("")) ? headAttribute.getValue() : "null";
                    List t = text.selectNodes("Each");

                    //拼接子项目
                    for (int ooi = 0; ooi < t.size(); ooi++) {

                        Element trElement = tableElement.addElement("div");
                        trElement.addAttribute("class", "row clearfix");
                        Element textField = (Element) t.get(ooi);
                        String nameString = textField.getText();

                        //添加一行
                        Element tdElement = trElement.addElement("span");
                        tdElement.setText(nameString);
                        Element input = trElement.addElement("input");
                        input.addAttribute("type", "text");
                        String idString = afterSplice + ";::;" + rule + ";::;" + headString + ";::;" + ooi;
                        input.addAttribute("id", idString);


                        //每一行的默认值
                        List valueTextFieldAttribute = textField.selectNodes("@value");
                        if (valueTextFieldAttribute.size() > 0) {
                            String valueTextField = ((Attribute) valueTextFieldAttribute.get(0)).getValue();
                           MAPS.put(idString, valueTextField);
                        }
                    }
                }

                //添加影响其他标签类型
                List affectList = stepElement.selectNodes("Affect");
                for (Object affectListObject : affectList) {

                    Element affectElement = (Element) affectListObject;
                    Attribute affectIdAttribute = (Attribute) affectElement.selectNodes("@id").get(0);
                    String affectId = affectIdAttribute.getValue();
                    Attribute attributeTextAttribute = (Attribute) affectElement.selectNodes("@text").get(0);
                    String affectText = attributeTextAttribute.getText();
                    Attribute attributeValueAttribute = (Attribute) affectElement.selectNodes("@value").get(0);
                    String affectValue = attributeValueAttribute.getText();

                    //影响项的初始值
                   MAPS.put(affectId, affectValue);

                    List affectedList = affectElement.selectNodes("Affected");
                    for (Object affectedi : affectedList) {
                        Element affectedElement = (Element) affectedi;
                        Attribute affectedIdAttribute = (Attribute) affectedElement.selectNodes("@id").get(0);
                        String affectedId = affectedIdAttribute.getValue();

                        Attribute affectedValueAttribute = (Attribute) affectedElement.selectNodes("@value").get(0);
                        String affectedValue = affectedValueAttribute.getText();

                        //被影响值的初始值
                       MAPS.put(affectedId, affectedValue);
                        affectId += (";**;" + affectedId);
                    }
                    Element trElement = tableElement.addElement("div");
                    trElement.addAttribute("class", "row clearfix");

                    //添加一行
                    Element tdElement = trElement.addElement("span");
                    tdElement.setText(affectText);
                    Element input = trElement.addElement("input");
                    input.addAttribute("type", "text");
                    input.addAttribute("id", affectId);
                }

                //添加radio
                List radioList = stepElement.selectNodes("radio");
                Element radioMainElement = formElement.addElement("div");
                radioMainElement.addAttribute("class", "radioMain");
                for (Object object : radioList) {
                    Element radio = (Element) object;

                    //得到显示的名字和要写入文件名字
                    /**
                     * 注意每一个radio的name = 要写入文件名字,
                     *                value = 要写入文件的值,
                     *                id = name + value
                     */
                    Attribute idRadio = (Attribute) radio.selectNodes("@id").get(0);
                    String idRadioString = idRadio.getValue();
                    Attribute textRadio = (Attribute) radio.selectNodes("@text").get(0);
                    String textRadioString = textRadio.getValue();

                    Element radioMainRow = radioMainElement.addElement("div");
                    radioMainRow.addAttribute("class", "radioMainRow clearfix");
                    Element radioEachSpan = radioMainRow.addElement("span");
                    radioEachSpan.setText(textRadioString);

                    Element radioEachDiv = radioMainRow.addElement("div");
                    //每一组按钮的每一个按钮项
                    List eachRadioList = radio.selectNodes("eachradio");
                    for (int eachRadioListi = 0; eachRadioListi < eachRadioList.size(); eachRadioListi++) {
                        Element eachRadio = (Element) eachRadioList.get(eachRadioListi);
                        String eachRadioText = eachRadio.getText();
                        Attribute eachRadioValue = (Attribute) eachRadio.selectNodes("@value").get(0);
                        String eachRadioValueString = eachRadioValue.getValue();

                        Element radioEachInput =  radioEachDiv.addElement("input");
                        radioEachInput.setText(eachRadioText);
                        radioEachInput.addAttribute("type", "radio");
                        radioEachInput.addAttribute("id", idRadioString + eachRadioValueString);
                        radioEachInput.addAttribute("name", idRadioString);
                        radioEachInput.addAttribute("value", eachRadioValueString);
                    }

                }

                //添加按钮
                Element trButton = formElement.addElement("div");
                trButton.addAttribute("class", "bottom");


                if (i != 0) {
                    Element inputButton = trButton.addElement("button");
                    inputButton.addAttribute("type", "button");
                    inputButton.addAttribute("id", "last");
                    inputButton.setText("上一步");
                }
                Element resetButton = trButton.addElement("button");
                resetButton.addAttribute("type", "button");
                resetButton.addAttribute("id", "reset");
                resetButton.setText("重置");

                Element submitButton = trButton.addElement("button");
                submitButton.addAttribute("type", "button");
                submitButton.addAttribute("id", "next");
                if (i < stepsElement.size() - 1){
                    submitButton.setText("下一步");
                }else {
                    submitButton.setText("完成");
                }

                //写入文件
                XMLWriter writer = new XMLWriter(new FileWriter(new File(urlPath , "modify" + (i + 1) + ".html")));
                writer.write(documentNewEach);
                writer.flush();

                fout = new FileOutputStream(new File(urlPath , "modify" + (i + 1) + ".html"), true);
                String firstPage = "\n<script>\n" +
                        "    function init() {\n" +
                        "\n" +
                        "        let infos = document.querySelectorAll('.main input');\n" +
                        "        let radioInfos = document.querySelectorAll('.radioMain input');\n" +
                        "        let reset = document.getElementById('reset');\n" +
                        "        let next = document.getElementById('next');\n" +
                        "        let param = '';\n" +
                        "        for (let i = 0; i < infos.length; i++) {\n" +
                        "            param = param + infos[i].id + '=&'\n" +
                        "        }\n" +
                        "        //处理radio\n" +
                        "        let temporary = '';\n" +
                        "        for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "            if (temporary != radioInfos[i].name){\n" +
                        "                temporary = radioInfos[i].name;\n" +
                        "                param = param + radioInfos[i].name + '=&'\n" +
                        "            }\n" +
                        "        }\n" +
                        "        param = param.slice(0, param.length - 1)\n" +
                        "        //基础信息获取\n" +
                        "        console.log(param);\n" +
                        "\n" +
                        "\n" +
                        "        //初始值的获取\n" +
                        "        function getInfo(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function () {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    data = JSON.parse(data);\n" +
                        "                    console.log(data);\n" +
                        "                    for (let i = 0; i < infos.length; i++) {\n" +
                        "                        infos[i].value = data[infos[i].id];\n" +
                        "                    }\n" +
                        "\n" +
                        "                    //radio配置\n" +
                        "                    let t = '';\n" +
                        "                    for (let i = 0; i < radioInfos.length; i++){\n" +
                        "                        if (radioInfos[i].name != t){\n" +
                        "                            if (data[radioInfos[i].name] == null || data[radioInfos[i].name] == '') {\n" +
                        "                                radioInfos[i].checked = true;\n" +
                        "                            }else {\n" +
                        "                                document.getElementById(radioInfos[i].name + data[radioInfos[i].name]).checked = true;\n" +
                        "                            }\n" +
                        "                            t = radioInfos[i].name;\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                    //\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/getdata\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "\n" +
                        "        }\n" +
                        "        getInfo(param);\n" +
                        "        console.log(param);\n" +
                        "\n" +
                        "        //下一步\n" +
                        "        function nextPage(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function () {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    console.log(data);\n" +
                        "                    window.location.href = data;\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/total\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "        }\n" +
                        "\n" +
                        "        //重置\n" +
                        "        reset.onclick = function () {\n" +
                        "            let param = '';\n" +
                        "            for (let i = 0; i < infos.length; i++) {\n" +
                        "                param = param + infos[i].id + '=&'\n" +
                        "            }\n" +
                        "            //处理radio\n" +
                        "            let temporary = '';\n" +
                        "            for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "                if (temporary != radioInfos[i].name){\n" +
                        "                    temporary = radioInfos[i].name;\n" +
                        "                    param = param + radioInfos[i].name + '=&'\n" +
                        "                }\n" +
                        "            }\n" +
                        "            param = param.slice(0, param.length - 1);\n" +
                        "            getInfo(param);\n" +
                        "        };\n" +
                        "        //下一步\n" +
                        "        next.onclick = function () {\n" +
                        "            let param = '';\n" +
                        "            for (let i = 0; i < infos.length; i++) {\n" +
                        "                param = param + infos[i].id + '=' + infos[i].value + '&'\n" +
                        "            }\n" +
                        "            //处理radio\n" +
                        "            for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "                if (radioInfos[i].checked == true){\n" +
                        "                    param = param + radioInfos[i].name + '=' + radioInfos[i].value + '&'\n" +
                        "                }\n" +
                        "\n" +
                        "            }\n" +
                        "            param = param.slice(0, param.length - 1);\n" +
                        "            console.log(param);\n" +
                        "            nextPage(param);\n" +
                        "            console.log(param);\n" +
                        "        };\n" +
                        "    }\n" +
                        "    window.addEventListener(\"load\", init, false);\n" +
                        "\n" +
                        "</script>";
                String middlePage = "\n<script>\n" +
                        "    function init() {\n" +
                        "\n" +
                        "        let infos = document.querySelectorAll('.main input');\n" +
                        "        let radioInfos = document.querySelectorAll('.radioMain input');\n" +
                        "        let last = document.getElementById(\"last\");\n" +
                        "        let reset = document.getElementById('reset');\n" +
                        "        let next = document.getElementById('next');\n" +
                        "        let param = '';\n" +
                        "        for (let i = 0; i < infos.length; i++) {\n" +
                        "            param = param + infos[i].id + '=&'\n" +
                        "        }\n" +
                        "        //处理radio\n" +
                        "        let temporary = '';\n" +
                        "        for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "            if (temporary != radioInfos[i].name){\n" +
                        "                temporary = radioInfos[i].name;\n" +
                        "                param = param + radioInfos[i].name + '=&'\n" +
                        "            }\n" +
                        "        }\n" +
                        "        param = param.slice(0, param.length - 1)\n" +
                        "        //基础信息获取\n" +
                        "        console.log(param);\n" +
                        "\n" +
                        "\n" +
                        "        //初始值的获取\n" +
                        "        function getInfo(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function () {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    data = JSON.parse(data);\n" +
                        "                    console.log(data);\n" +
                        "                    for (let i = 0; i < infos.length; i++) {\n" +
                        "                        infos[i].value = data[infos[i].id];\n" +
                        "                    }\n" +
                        "\n" +
                        "                    //radio配置\n" +
                        "                    let t = '';\n" +
                        "                    for (let i = 0; i < radioInfos.length; i++){\n" +
                        "                        if (radioInfos[i].name != t){\n" +
                        "                            if (data[radioInfos[i].name] == null || data[radioInfos[i].name] == '') {\n" +
                        "                                radioInfos[i].checked = true;\n" +
                        "                            }else {\n" +
                        "                                alert(radioInfos[i].name + data[radioInfos[i].name]);\n" +
                        "                                document.getElementById(radioInfos[i].name + data[radioInfos[i].name]).checked = true;\n" +
                        "                            }\n" +
                        "                            t = radioInfos[i].name;\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                    //\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/getdata\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "\n" +
                        "        }\n" +
                        "        getInfo(param);\n" +
                        "        console.log(param);\n" +
                        "\n" +
                        "        //上一步\n" +
                        "        function lastPage(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function() {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    console.log(data);\n" +
                        "                    window.location.href = data;\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/lastpage\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "        }\n" +
                        "\n" +
                        "        //下一步\n" +
                        "        function nextPage(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function () {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    console.log(data);\n" +
                        "                    window.location.href = data;\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/total\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "        }\n" +
                        "\n" +
                        "        //重置\n" +
                        "        reset.onclick = function () {\n" +
                        "            let param = '';\n" +
                        "            for (let i = 0; i < infos.length; i++) {\n" +
                        "                param = param + infos[i].id + '=&'\n" +
                        "            }\n" +
                        "            //处理radio\n" +
                        "            let temporary = '';\n" +
                        "            for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "                if (temporary != radioInfos[i].name){\n" +
                        "                    temporary = radioInfos[i].name;\n" +
                        "                    param = param + radioInfos[i].name + '=&'\n" +
                        "                }\n" +
                        "            }\n" +
                        "            param = param.slice(0, param.length - 1);\n" +
                        "            getInfo(param);\n" +
                        "        };\n" +
                        "\n" +
                        "        //上一步\n" +
                        "        last.onclick = function() {\n" +
                        "            lastPage(param);\n" +
                        "            console.log(param);\n" +
                        "        };\n" +
                        "        //下一步\n" +
                        "        next.onclick = function () {\n" +
                        "            let param = '';\n" +
                        "            for (let i = 0; i < infos.length; i++) {\n" +
                        "                param = param + infos[i].id + '=' + infos[i].value + '&'\n" +
                        "            }\n" +
                        "            //处理radio\n" +
                        "            for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "                if (radioInfos[i].checked == true){\n" +
                        "                    param = param + radioInfos[i].name + '=' + radioInfos[i].value + '&'\n" +
                        "                }\n" +
                        "\n" +
                        "            }\n" +
                        "            param = param.slice(0, param.length - 1);\n" +
                        "            console.log(param);\n" +
                        "            nextPage(param);\n" +
                        "            console.log(param);\n" +
                        "        };\n" +
                        "    }\n" +
                        "    window.addEventListener(\"load\", init, false);\n" +
                        "\n" +
                        "</script>";
                String finalPage = "\n<script>\n" +
                        "    function init() {\n" +
                        "\n" +
                        "        let infos = document.querySelectorAll('.main input');\n" +
                        "        let radioInfos = document.querySelectorAll('.radioMain input');\n" +
                        "        let last = document.getElementById(\"last\");\n" +
                        "        let reset = document.getElementById('reset');\n" +
                        "        let next = document.getElementById('next');\n" +
                        "        let param = '';\n" +
                        "        for (let i = 0; i < infos.length; i++) {\n" +
                        "            param = param + infos[i].id + '=&'\n" +
                        "        }\n" +
                        "        //处理radio\n" +
                        "        let temporary = '';\n" +
                        "        for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "            if (temporary != radioInfos[i].name){\n" +
                        "                temporary = radioInfos[i].name;\n" +
                        "                param = param + radioInfos[i].name + '=&'\n" +
                        "            }\n" +
                        "        }\n" +
                        "        param = param.slice(0, param.length - 1)\n" +
                        "        //基础信息获取\n" +
                        "        console.log(param);\n" +
                        "\n" +
                        "\n" +
                        "        //初始值的获取\n" +
                        "        function getInfo(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function () {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    data = JSON.parse(data);\n" +
                        "                    console.log(data);\n" +
                        "                    for (let i = 0; i < infos.length; i++) {\n" +
                        "                        infos[i].value = data[infos[i].id];\n" +
                        "                    }\n" +
                        "\n" +
                        "                    //radio配置\n" +
                        "                    let t = '';\n" +
                        "                    for (let i = 0; i < radioInfos.length; i++){\n" +
                        "                        if (radioInfos[i].name != t){\n" +
                        "                            if (data[radioInfos[i].name] == null || data[radioInfos[i].name] == '') {\n" +
                        "                                radioInfos[i].checked = true;\n" +
                        "                            }else {\n" +
                        "                                alert(radioInfos[i].name + data[radioInfos[i].name]);\n" +
                        "                                document.getElementById(radioInfos[i].name + data[radioInfos[i].name]).checked = true;\n" +
                        "                            }\n" +
                        "                            t = radioInfos[i].name;\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                    //\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/getdata\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "\n" +
                        "        }\n" +
                        "        getInfo(param);\n" +
                        "        console.log(param);\n" +
                        "\n" +
                        "        //上一步\n" +
                        "        function lastPage(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function() {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    console.log(data);\n" +
                        "                    window.location.href = data;\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/lastpage\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "        }\n" +
                        "\n" +
                        "        //下一步\n" +
                        "        function nextPage(param) {\n" +
                        "            let xmlhttp;\n" +
                        "            if (window.XMLHttpRequest) {\n" +
                        "                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                        "                xmlhttp = new XMLHttpRequest();\n" +
                        "            } else {\n" +
                        "                // IE6, IE5 浏览器执行代码\n" +
                        "                xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                        "            }\n" +
                        "            xmlhttp.onreadystatechange = function () {\n" +
                        "                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                        "\n" +
                        "                    let data = xmlhttp.responseText;\n" +
                        "                    console.log(data);\n" +
                        "                    window.location.href = data;\n" +
                        "                }\n" +
                        "            }\n" +
                        "            xmlhttp.open(\"POST\", \"http://120.27.19.38:7893/final\", true);\n" +
                        "            xmlhttp.send(param);\n" +
                        "        }\n" +
                        "\n" +
                        "        //重置\n" +
                        "        reset.onclick = function () {\n" +
                        "            let param = '';\n" +
                        "            for (let i = 0; i < infos.length; i++) {\n" +
                        "                param = param + infos[i].id + '=&'\n" +
                        "            }\n" +
                        "            //处理radio\n" +
                        "            let temporary = '';\n" +
                        "            for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "                if (temporary != radioInfos[i].name){\n" +
                        "                    temporary = radioInfos[i].name;\n" +
                        "                    param = param + radioInfos[i].name + '=&'\n" +
                        "                }\n" +
                        "            }\n" +
                        "            param = param.slice(0, param.length - 1);\n" +
                        "            getInfo(param);\n" +
                        "        };\n" +
                        "\n" +
                        "        //上一步\n" +
                        "        last.onclick = function() {\n" +
                        "            lastPage(param);\n" +
                        "            console.log(param);\n" +
                        "        };\n" +
                        "        //下一步\n" +
                        "        next.onclick = function () {\n" +
                        "            let param = '';\n" +
                        "            for (let i = 0; i < infos.length; i++) {\n" +
                        "                param = param + infos[i].id + '=' + infos[i].value + '&'\n" +
                        "            }\n" +
                        "            //处理radio\n" +
                        "            for (let i = 0; i < radioInfos.length; i++) {\n" +
                        "                if (radioInfos[i].checked == true){\n" +
                        "                    param = param + radioInfos[i].name + '=' + radioInfos[i].value + '&'\n" +
                        "                }\n" +
                        "\n" +
                        "            }\n" +
                        "            param = param.slice(0, param.length - 1);\n" +
                        "            console.log(param);\n" +
                        "            nextPage(param);\n" +
                        "            console.log(param);\n" +
                        "        };\n" +
                        "    }\n" +
                        "\n" +
                        "    window.addEventListener(\"load\", init, false);\n" +
                        "</script>\n" +
                        "\n";
                if (i == 0){
                    fout.write(firstPage.getBytes());
                } else if (i == stepsElement.size() - 1) {
                    fout.write(finalPage.getBytes());
                }else {
                    fout.write(middlePage.getBytes());
                }
                fout.flush();

            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //存储初始值
        MAPS = ModityConfig.processData(MAPS);
        Properties prop = new Properties();
        InputStream inputFile;
        OutputStream outputFile;
        try {
            inputFile = new FileInputStream(pathName);
            prop.load(inputFile);
            outputFile = new FileOutputStream(new File(pathName));
            //设值-保存
            for (String key : MAPS.keySet()) {
                String value = MAPS.get(key);
                prop.setProperty(key, value);
                System.out.println(key + ": " + value);
            }
            prop.store(outputFile, "Update" );
            System.out.println("update完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSystem() {
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

    public static void main(String[] args) {
        new XmlToHtmlJs().analysisXml();
    }
}
