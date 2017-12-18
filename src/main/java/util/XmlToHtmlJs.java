package util;

import org.choice.dom4j.*;
import org.choice.dom4j.io.OutputFormat;
import org.choice.dom4j.io.SAXReader;
import org.choice.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class XmlToHtmlJs {
    public void analysisXml() {
        try {
            String pathName = "";
            Map<String, String> MAPS = new HashMap<String,String>();
            SAXReader reader = new SAXReader();
            //读入开发者的配置xml文件
            InputStream urlabs = Object.class.getResourceAsStream("/ChoiceNavigation.xml");
            initSystem();
            Document document = reader.read(urlabs);
            List location = document.selectNodes("/steps/location");
            //得到开发人员配置文件位置
            pathName = ((Element) location.get(0)).getText();


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
            MAPS = ModityConfig.processData(MAPS);
            ModityConfig.update(MAPS);


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
