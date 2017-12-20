package com.choice.http;

import Helper.XmlToHtmlJsResolver;
import com.choice.utils.ServerPortUtils;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class ServerTest {
	public static void main(String[] args) {
		//声明变量
                ServerSocket ss=null;

                boolean flag=true;

                try {
                	int port=Integer.parseInt(ServerPortUtils.getPort());
                	System.out.println("Server Port:"+port);
					ss=new ServerSocket(port, 1024);
                    initWebapp();
                    openDefaultBrowser();
					while(flag)
					{
						//接受客户端发送过来的Socket
                        final Socket s=ss.accept();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpCreatorImpl hci=new HttpCreatorImpl(s);
                                HttpRequest request=hci.getHttpRequest();
                                HttpResponse response=hci.getHttpResponse();
                                HttpAccessProcessor hapi=hci.getHttpAccessProcessor();

                                //	用于测试收到的信息
                                if(request.isStaticResource())//处理静态信息
                                {
                                    System.out.println("静态");
                                    hapi.processStaticResource(request, response);
                                    System.out.println("静态加载完成");
                                }
                                else if(request.isDynamicResource())//处理动态请求
                                {
                                    System.out.println("动态");
                                    hapi.processDynamicResource(request, response);
                                } else {
                                    System.out.println("无法处理");
                                    hapi.sendError(404, request, response);
                                    //new XmlToHtmlJsResolver().analysisXml();
                                    //System.out.println("生成html文件");
                                }
                                try {
                                    if(s!=null) {
                                        s.close();
                                    }
                                }catch (Exception ex){
                                }
                            }
                        }).start();

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

	}

    private static void initWebapp() {
        XmlToHtmlJsResolver xmlToHtmlJsResolver = new XmlToHtmlJsResolver();
        xmlToHtmlJsResolver.analysisXml();
    }

    private static void extractHtml() {

    }

    //打开默认浏览器
    public static void openDefaultBrowser(){
        try {
            String os = System.getProperty("os.name");
            if(os.toLowerCase().startsWith("win")){
                String urlString = String.format("http://%s:%s/welcome.html", ServerPortUtils.getIp(), ServerPortUtils.getPort());
                URI uri = new URI(urlString);
                Desktop.getDesktop().browse(uri);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
