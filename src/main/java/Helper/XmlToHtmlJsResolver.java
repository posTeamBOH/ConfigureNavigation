package Helper;

import com.choice.utils.ServerPortUtils;
import org.choice.dom4j.*;
import org.choice.dom4j.io.OutputFormat;
import org.choice.dom4j.io.SAXReader;
import org.choice.dom4j.io.XMLWriter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class XmlToHtmlJsResolver {
    public void analysisXml() {
        produceWelcome();
        produceLast();
        produceJs();
        try {
            Map<String, String> MAPS = new HashMap<String,String>();
            SAXReader reader = new SAXReader();
            //读入开发者的配置xml文件
            InputStream urlabs = Object.class.getResourceAsStream("/ChoiceNavigation.xml");
            initSystem();
            Document document = reader.read(urlabs);


            Document documentNewEach = DocumentHelper.createDocument();
            Element rootElement = documentNewEach.addElement("html");

            //头部
            headHtml(rootElement);

            //body
            Element bodyElement = rootElement.addElement("body");
            Element containerDiv = bodyElement.addElement("div");
            containerDiv.addAttribute("class", "container");

            //生成每一页
            List stepsElement = document.selectNodes("/steps/step");
            for (int i = 0; i < stepsElement.size(); i++) {
                Element stepElement = (Element) stepsElement.get(i);

                //每一页叫啥
                String stepNameString = ((Attribute) stepElement.selectNodes("@name").get(0)).getValue();

                Element sectionElement = containerDiv.addElement("section");
                sectionElement.addAttribute("id", "modify" + (i + 1));
                sectionElement.addAttribute("style", "display: none");

                Element formElement = sectionElement.addElement("div");
                formElement.addAttribute("class", "container");

                Element h2Element = formElement.addElement("h2");
                h2Element.addAttribute("class", "title");
                h2Element.setText("配置第" + (i + 1) + "步：" + stepNameString);

                //class main
                Element tableElement = formElement.addElement("div");
                tableElement.addAttribute("class", "main");

                //添加textField
                addTextField(stepElement, tableElement, MAPS);

                //textFields
                addTextFields(stepElement, tableElement, MAPS);

                //Affect
                addAffect(stepElement, tableElement, MAPS);

                //添加radio
                Element radioMainElement = formElement.addElement("div");
                radioMainElement.addAttribute("class", "radioMain");
                addRadio(stepElement, radioMainElement, MAPS);
            }
            //添加按钮
            addButton(containerDiv);

            //js
            Element scriptElement = rootElement.addElement("script");
            scriptElement.addAttribute("type", "text/javascript");
            scriptElement.addAttribute("src", "controller.js");

            //写入文件
            //写入文件
            String urlPath = System.getProperty("user.dir")+File.separator+"navigationHtml"+File.separator;
            OutputFormat xmlFormat = new OutputFormat();
            xmlFormat.setEncoding("UTF-8");
            // 设置换行
            xmlFormat.setNewlines(true);
            // 生成缩进
            xmlFormat.setIndent(true);
            // 使用4个空格进行缩进, 可以兼容文本编辑器
            xmlFormat.setIndent("    ");
            // 不在文件头生成  XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)
            xmlFormat.setSuppressDeclaration(true);
            //扩展空元素
            xmlFormat.setExpandEmptyElements(true);
            XMLWriter writer = new XMLWriter(new FileWriter(new File(urlPath , "knn" + ".html")), xmlFormat);

            writer.write(documentNewEach);
            writer.flush();
            //存储初始值
            MAPS = ModityConfigHelper.processData(MAPS);
            ModityConfigHelper.update(MAPS);


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *<head>
     *</head>
     */
    private void headHtml(Element rootElement) {
        //head*********************************//
        Element headElement = rootElement.addElement("head");
        Element metaElement = headElement.addElement("meta");
        metaElement.addAttribute("charset", "UTF-8");

        //加入css样式
        Element styleElement = headElement.addElement("style");
        styleElement.setText("\n" +
                "        .clearfix:after {\n" +
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
                "\n" +
                "        .radioMain span {\n" +
                "            float: left;\n" +
                "        }\n" +
                "\n" +
                "        .radioMainRow div {\n" +
                "            float: right;\n" +
                "        }\n" +
                "\n" +
                "        .radioMainRow div input {\n" +
                "            margin: 0 8px 0 8px;\n" +
                "        }");
        Element titleElement = headElement.addElement("title");
        titleElement.addText("配置");
        //head*********************************//
    }

    /**
     * 添加textField
     */
    private void addTextField(Element stepElement, Element tableElement, Map<String, String> MAPS) {
        //添加每一个输入框**********************************//
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

            List textFieldPing =  textField.selectNodes("@ping");
            String textFieldPingString = (textFieldPing.size() > 0) ? ((Attribute) textFieldPing.get(0)).getValue() : "";
            List textFieldPingId = textField.selectNodes("@pingid");
            String textFieldPingIdString = (textFieldPingId.size() > 0) ? ((Attribute) textFieldPingId.get(0)).getValue() : "";

            if (!textFieldPingString.equals("") && !textFieldPingIdString.equals("")) {
                idString = idString + ";##;" + textFieldPingString + ";##;" + textFieldPingIdString;
                //带ping的默认不能为空
                input.addAttribute("data-type", "notnull");
                input.addAttribute("data-name", nameString);
            }

            input.addAttribute("id", idString);
            //input的类型
            List typeElements = textField.selectNodes("@type");
            if (typeElements.size() > 0) {
                String typeString = ((Attribute) typeElements.get(0)).getValue();
                if (typeString.equals("folder")) {
                    input.addAttribute("data-type", "folder");
                }else if(typeString.equals("notnull")) {
                    input.addAttribute("data-type", "notnull");
                    input.addAttribute("data-name", nameString);
                }

            }
            //每一行的默认值
            List valueTextFieldAttribute = textField.selectNodes("@value");
            if (valueTextFieldAttribute.size() > 0) {
                String valueTextField = ((Attribute) valueTextFieldAttribute.get(0)).getValue();
                MAPS.put(idString, valueTextField);
            }
        }
    }

    /**
     * 添加TextFields
     */
    private void addTextFields(Element stepElement, Element tableElement, Map<String, String> MAPS) {
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

                //input的类型 each每一项都不能为空
                input.addAttribute("data-type", "notnull");
                input.addAttribute("data-name", nameString);

                List typeElements = textField.selectNodes("@type");
                if (typeElements.size() > 0) {
                    String typeString = ((Attribute) typeElements.get(0)).getValue();
                    if (typeString.equals("folder")) {
                        input.addAttribute("data-type", "folder");
                    }

                }

                List textFieldPing =  textField.selectNodes("@ping");
                String textFieldPingString = (textFieldPing.size() > 0) ? ((Attribute) textFieldPing.get(0)).getValue() : "";
                List textFieldPingId = textField.selectNodes("@pingid");
                String textFieldPingIdString = (textFieldPingId.size() > 0) ? ((Attribute) textFieldPingId.get(0)).getValue() : "";

                if (!textFieldPingString.equals("") && !textFieldPingIdString.equals("")) {
                    idString = idString + ";##;" + textFieldPingString + ";##;" + textFieldPingIdString;
                }
                input.addAttribute("id", idString);

                //每一行的默认值
                List valueTextFieldAttribute = textField.selectNodes("@value");
                if (valueTextFieldAttribute.size() > 0) {
                    String valueTextField = ((Attribute) valueTextFieldAttribute.get(0)).getValue();
                    MAPS.put(idString, valueTextField);
                }
            }
        }
    }

    /**
     * 添加Affect
     */
    private void addAffect(Element stepElement, Element tableElement, Map<String, String> MAPS) {
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

            List textFieldPing =  affectElement.selectNodes("@ping");
            String textFieldPingString = (textFieldPing.size() > 0) ? ((Attribute) textFieldPing.get(0)).getValue() : "";
            List textFieldPingId = affectElement.selectNodes("@pingid");
            String textFieldPingIdString = (textFieldPingId.size() > 0) ? ((Attribute) textFieldPingId.get(0)).getValue() : "";

            if (!textFieldPingString.equals("") && !textFieldPingIdString.equals("")) {
                affectId = affectId + ";##;" + textFieldPingString + ";##;" + textFieldPingIdString;
                //带ping的默认不能为空
                input.addAttribute("data-type", "notnull");
                input.addAttribute("data-name", affectText);
            }

            input.addAttribute("id", affectId);

            //input的类型
            List typeElements = affectElement.selectNodes("@type");
            if (typeElements.size() > 0) {
                String typeString = ((Attribute) typeElements.get(0)).getValue();
                if (typeString.equals("folder")) {
                    input.addAttribute("data-type", "folder");
                }else if(typeString.equals("notnull")) {
                    input.addAttribute("data-type", "notnull");
                    input.addAttribute("data-name", affectText);
                }
            }
        }
    }

    /**
     * 添加radio
     */
    private void addRadio(Element stepElement, Element radioMainElement, Map<String, String> MAPS) {
        List radioList = stepElement.selectNodes("radio");

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

    }

    /**
     * 添加button
     */
    private void addButton(Element containerDiv) {
        //添加按钮
        Element trButton = containerDiv.addElement("div");
        trButton.addAttribute("class", "bottom");

        //上一步按钮
        Element inputButton = trButton.addElement("button");
        inputButton.addAttribute("type", "button");
        inputButton.addAttribute("id", "last");
        inputButton.setText("上一步");

        //重置按钮
        Element resetButton = trButton.addElement("button");
        resetButton.addAttribute("type", "button");
        resetButton.addAttribute("id", "reset");
        resetButton.setText("重置");

        //下一步按钮
        Element submitButton = trButton.addElement("button");
        submitButton.addAttribute("type", "button");
        submitButton.addAttribute("id", "next");
        submitButton.setText("下一步");

        //完成
        //下一步按钮
        Element finalButton = trButton.addElement("button");
        finalButton.addAttribute("type", "button");
        finalButton.addAttribute("id", "final");
        finalButton.setText("完成");

    }

    //dom4j需要的
    public static void initSystem() {
        Properties properties = new Properties();
        InputStream inputStream = XmlToHtmlJsResolver.class.getResourceAsStream("choicedom4j.properties");
        try {
            properties.load(inputStream);
            for(Map.Entry map :properties.entrySet()){
                System.setProperty((String)map.getKey(),(String)map.getValue());
            }
        } catch (IOException e) {
        }
    }
    /**
     生成欢迎页面和结束页面
     */
    private void produceWelcome(){
        String urlString = String.format("%s:%s", ServerPortUtils.getIp(), ServerPortUtils.getPort());
        String s=String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
                "            xmlhttp.open(\"POST\", \"http://%s/welcome\", true);\n" +
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
                "</script>\n",urlString);
        String parent = System.getProperty("user.dir")+File.separator+"navigationHtml"+File.separator;
        new File(parent).mkdirs();
        String path = String.format("%s%s", parent, "welcome.html");
        try {
            OutputStream out = new FileOutputStream(new File(path));
            out.write(s.getBytes());
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成last页面
     */
    private  void produceLast(){
        String s="<!DOCTYPE html>\n" +
                "<html >\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>配置结束</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1 align=\"center\">配置成功</h1>\n" +
                "</body>\n" +
                "</html>";
        String parent = System.getProperty("user.dir")+File.separator+"navigationHtml"+File.separator;
        new File(parent).mkdirs();
        String path = String.format("%s%s", parent, "last.html");
        try {
            OutputStream out = new FileOutputStream(new File(path));
            out.write(s.getBytes());
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成js
     *
     */
    private void produceJs() {
        String urlString = String.format("%s:%s", ServerPortUtils.getIp(), ServerPortUtils.getPort());
        String jsString = String.format("function init() {\n" +
                "    /*\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                "      变量区\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                "*/\n" +
                "\n" +
                "    // 全局变量\n" +
                "    class Number {\n" +
                "        constructor(nowPageNumber) {\n" +
                "            this.nowPageNumber = nowPageNumber;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    let Num = new Number(1);  //指定当前页码\n" +
                "    const allPageNumber = document.getElementsByTagName(\"section\").length;\n" +
                "\n" +
                "    // 全局 Array\n" +
                "    let infos = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input'); // now Page Info\n" +
                "    let allInfos = document.querySelectorAll('.main input'); // all Page Info\n" +
                "    let radioInfos = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input');  // now Page Info\n" +
                "    let allRadioInfos = document.querySelectorAll('.radioMain input'); // all Page Info\n" +
                "    let allPage = document.querySelectorAll('section'); //all Page\n" +
                "    // 按钮\n" +
                "    let reset = document.getElementById('reset');\n" +
                "    let last = document.getElementById(\"last\");\n" +
                "    let next = document.getElementById('next');\n" +
                "    let final = document.getElementById('final');\n" +
                "\n" +
                "    // 信息\n" +
                "    let param = '';\n" +
                "\n" +
                "    /*\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                "    功能区Func\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                " */\n" +
                "\n" +
                "    //基础信息获取函数\n" +
                "    function getParam() {\n" +
                "        param = '';\n" +
                "        console.log(Num.nowPageNumber)\n" +
                "        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {\n" +
                "            param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id + '=&'\n" +
                "        }\n" +
                "        //处理radio\n" +
                "        let temporary = '';\n" +
                "        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {\n" +
                "            if (temporary != document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name) {\n" +
                "                temporary = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name;\n" +
                "                param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + '=&'\n" +
                "            }\n" +
                "        }\n" +
                "        param = param.slice(0, param.length - 1)\n" +
                "\n" +
                "        // console.log(param,document.querySelectorAll('#modify'+Num.nowPageNumber+' .main input'));\n" +
                "        return param;\n" +
                "    }\n" +
                "\n" +
                "    // 更换按钮\n" +
                "    function updateButton(type) {\n" +
                "        if (type === 0) {\n" +
                "            //第一页\n" +
                "            final.style.display = 'none';\n" +
                "            last.style.display = 'none';\n" +
                "            next.style.display = 'inline-block';\n" +
                "        } else if (type === 1) {\n" +
                "            //中间页\n" +
                "            final.style.display = 'none';\n" +
                "            last.style.display = 'inline-block';\n" +
                "            next.style.display = 'inline-block';\n" +
                "        } else if (type === 2) {\n" +
                "            //尾页\n" +
                "            final.style.display = 'inline-block';\n" +
                "            last.style.display = 'inline-block';\n" +
                "            next.style.display = 'none';\n" +
                "        } else if (type == 3) {\n" +
                "            //第一页(只有一页)\n" +
                "            final.style.display = 'inline-block';\n" +
                "            last.style.display = 'none';\n" +
                "            next.style.display = 'none';\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /*\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                "    初始化定义区\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                " */\n" +
                "\n" +
                "    //初始值的获取\n" +
                "    function getInfo(param) {\n" +
                "        console.log(param);\n" +
                "        let xmlhttp;\n" +
                "        if (window.XMLHttpRequest) {\n" +
                "            //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                "            xmlhttp = new XMLHttpRequest();\n" +
                "        } else {\n" +
                "            // IE6, IE5 浏览器执行代码\n" +
                "            xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "        }\n" +
                "        xmlhttp.onreadystatechange = function () {\n" +
                "            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                "\n" +
                "                let data = xmlhttp.responseText;\n" +
                "                data = JSON.parse(data);\n" +
                "                console.log(data);\n" +
                "                for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {\n" +
                "                    document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].value = data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id];\n" +
                "                }\n" +
                "\n" +
                "                //radio配置\n" +
                "                let t = '';\n" +
                "                for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {\n" +
                "                    if (document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name != t) {\n" +
                "                        if (data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name] == null || data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name] == '') {\n" +
                "                            document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].checked = true;\n" +
                "                        } else {\n" +
                "                            document.getElementById(document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name]).checked = true;\n" +
                "                        }\n" +
                "                        t = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name;\n" +
                "                    }\n" +
                "                }\n" +
                "                //\n" +
                "            }\n" +
                "        }\n" +
                "        xmlhttp.open(\"POST\", \"http://%s/getdata\", true);\n" +
                "        xmlhttp.send(param);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    /*\n" +
                "        `               ``````````````````````````````````````````````````````````````````````\n" +
                "         初始化执行区\n" +
                "        `               ``````````````````````````````````````````````````````````````````````\n" +
                "     */\n" +
                "    getParam();\n" +
                "    getInfo(param);\n" +
                "    if (allPage.length == 1) {\n" +
                "        updateButton(3)\n" +
                "    } else {\n" +
                "        updateButton(0);\n" +
                "    }\n" +
                "\n" +
                "    console.log('当前页码为:', Num.nowPageNumber);\n" +
                "    console.log('当前Page的所有表单信息为：', infos, radioInfos);\n" +
                "    console.log('所有Page的表单信息为：', allInfos, allRadioInfos);\n" +
                "    console.log(param);\n" +
                "    document.getElementById(\"modify\" + Num.nowPageNumber).style.display = \"block\";\n" +
                "    /*\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                "    Impl区\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                " */\n" +
                "\n" +
                "    //上一步\n" +
                "    function lastPage(param) {\n" +
                "        if (Num.nowPageNumber != 2) updateButton(1);\n" +
                "        else updateButton(0);\n" +
                "        document.getElementById(\"modify\" + Num.nowPageNumber).style.display = \"none\";\n" +
                "        Num.nowPageNumber--;\n" +
                "        document.getElementById(\"modify\" + Num.nowPageNumber).style.display = \"block\";\n" +
                "    }\n" +
                "\n" +
                "    //下一步\n" +
                "    function nextPage(param) {\n" +
                "        let xmlhttp;\n" +
                "        if (window.XMLHttpRequest) {\n" +
                "            //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                "            xmlhttp = new XMLHttpRequest();\n" +
                "        } else {\n" +
                "            // IE6, IE5 浏览器执行代码\n" +
                "            xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "        }\n" +
                "        xmlhttp.onreadystatechange = function () {\n" +
                "            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                "                let data = xmlhttp.responseText;\n" +
                "                console.log('点击下一步时候接收到的data：', JSON.parse(data));\n" +
                "                data = JSON.parse(data);\n" +
                "                if (data.type == true) {\n" +
                "                    if (Num.nowPageNumber < allPageNumber - 1) {\n" +
                "                        updateButton(1);\n" +
                "                    } else {\n" +
                "                        updateButton(2);\n" +
                "                    }\n" +
                "                    document.getElementById(\"modify\" + Num.nowPageNumber).style.display = \"none\";\n" +
                "                    Num.nowPageNumber++;\n" +
                "                    param = getParam();\n" +
                "                    getInfo(param);\n" +
                "                    document.getElementById(\"modify\" + Num.nowPageNumber).style.display = \"block\";\n" +
                "                } else if (data.type == false) {\n" +
                "                    alert(data.message);\n" +
                "                } else {\n" +
                "                    alert(\"服务器无响应!\");\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        xmlhttp.open(\"POST\", \"http://%s/total\", true);\n" +
                "        xmlhttp.send(param);\n" +
                "    }\n" +
                "\n" +
                "    //完成\n" +
                "    function finalPage(param) {\n" +
                "        let xmlhttp;\n" +
                "        if (window.XMLHttpRequest) {\n" +
                "            //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码\n" +
                "            xmlhttp = new XMLHttpRequest();\n" +
                "        } else {\n" +
                "            // IE6, IE5 浏览器执行代码\n" +
                "            xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "        }\n" +
                "        xmlhttp.onreadystatechange = function () {\n" +
                "            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n" +
                "\n" +
                "                let a = window.confirm(\"确定保存？\");\n" +
                "                if (a == true) {\n" +
                "                    let data = xmlhttp.responseText;\n" +
                "                    console.log(JSON.parse(data));\n" +
                "                    data = JSON.parse(data);\n" +
                "                    if (data.type == \"1\") {\n" +
                "                        window.location = \"/last.html\";\n" +
                "                    } else if (data.type == \"0\"){\n" +
                "                        alert(\"保存失败：\" + data.message);\n" +
                "                    }else {\n" +
                "                        alert(\"保存失败!\")\n" +
                "                    }\n" +
                "                }\n" +
                "\n" +
                "\n" +
                "\n" +
                "            }\n" +
                "        }\n" +
                "        xmlhttp.open(\"POST\", \"http://%s/final\", true);\n" +
                "        xmlhttp.send(param);\n" +
                "    }\n" +
                "\n" +
                "    /*\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                "    实现区\n" +
                "`               ``````````````````````````````````````````````````````````````````````\n" +
                "*/\n" +
                "    //重置\n" +
                "    reset.onclick = function () {\n" +
                "        let param = '';\n" +
                "        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {\n" +
                "            param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id + '=&'\n" +
                "        }\n" +
                "        //处理radio\n" +
                "        let temporary = '';\n" +
                "        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {\n" +
                "            if (temporary != document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name) {\n" +
                "                temporary = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name;\n" +
                "                param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + '=&'\n" +
                "            }\n" +
                "        }\n" +
                "        param = param.slice(0, param.length - 1);\n" +
                "        getInfo(param);\n" +
                "    };\n" +
                "\n" +
                "    //上一步\n" +
                "    last.onclick = function () {\n" +
                "        lastPage(param);\n" +
                "        console.log(param);\n" +
                "    };\n" +
                "    //下一步\n" +
                "    next.onclick = function () {\n" +
                "        let param = '';\n" +
                "        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {\n" +
                "            var str = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i];\n" +
                "            var pattern = /^[a-zA-Z]:(([a-zA-Z]*)||([a-zA-Z]*\\\\))*/;//判断路径是否合法的正则表达式\n" +
                "\n" +
                "            if (str.getAttribute(\"data-type\") == \"folder\" && !pattern.test(str.value)) {\n" +
                "                alert(\"文件夹目录不合格\");\n" +
                "                return;\n" +
                "            }else if(str.getAttribute(\"data-type\") == \"notnull\" && str.value == \"\") {\n" +
                "                alert(str.getAttribute(\"data-name\") + \"不能为空\");\n" +
                "                return;\n" +
                "            }\n" +
                "            param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id + '=' + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].value + '&'\n" +
                "        }\n" +
                "        //处理radio\n" +
                "        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {\n" +
                "            if (document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].checked == true) {\n" +
                "                param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + '=' + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].value + '&'\n" +
                "            }\n" +
                "\n" +
                "        }\n" +
                "        param = param.slice(0, param.length - 1);\n" +
                "        console.log(param);\n" +
                "        nextPage(param);\n" +
                "        console.log(param);\n" +
                "    };\n" +
                "\n" +
                "    //完成\n" +
                "    final.onclick = () => {\n" +
                "        let param = '';\n" +
                "        for (let i = 0; i < document.querySelectorAll('.main input').length; i++) {\n" +
                "\n" +
                "            var str = document.querySelectorAll('.main input')[i];\n" +
                "            var pattern = /^[a-zA-Z]:(([a-zA-Z]*)||([a-zA-Z]*\\\\))*/;//判断路径是否合法的正则表达式\n" +
                "\n" +
                "            if (str.getAttribute(\"data-type\") == \"folder\" && !pattern.test(str.value)) {\n" +
                "                alert(\"文件夹目录不合格\");\n" +
                "                return;\n" +
                "            }else if(str.getAttribute(\"data-type\") == \"notnull\" && str.value == \"\") {\n" +
                "                alert(str.getAttribute(\"data-name\") + \"不能为空\");\n" +
                "                return;\n" +
                "            }\n" +
                "            param = param + document.querySelectorAll('.main input')[i].id + '=' + document.querySelectorAll('.main input')[i].value + '&'\n" +
                "        }\n" +
                "        console.log(document.querySelectorAll('.radioMain input'))\n" +
                "        //处理radio\n" +
                "        for (let i = 0; i < document.querySelectorAll('.radioMain input').length; i++) {\n" +
                "            if (document.querySelectorAll('.radioMain input')[i].checked == true) {\n" +
                "                param = param + document.querySelectorAll('.radioMain input')[i].name + '=' + document.querySelectorAll('.radioMain input')[i].value + '&'\n" +
                "            }\n" +
                "\n" +
                "        }\n" +
                "        param = param.slice(0, param.length - 1);\n" +
                "        console.log(param);\n" +
                "        finalPage(param);\n" +
                "        console.log(param);\n" +
                "    };\n" +
                "}\n" +
                "\n" +
                "window.addEventListener(\"load\", init, false);", urlString, urlString, urlString);
        String parent = System.getProperty("user.dir")+File.separator+"navigationHtml"+File.separator;
        new File(parent).mkdirs();
        String path = String.format("%s%s", parent, "controller.js");
        try {
            OutputStream out = new FileOutputStream(new File(path));
            out.write(jsString.getBytes());
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // XmlToHtmlJsResolver().produceJs();
        //new XmlToHtmlJsResolver().produceWelcome();
        new XmlToHtmlJsResolver().produceLast();
    }
}