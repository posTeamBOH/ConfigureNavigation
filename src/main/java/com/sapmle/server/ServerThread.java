package com.sapmle.server;

import java.net.Socket;

import com.sample.http.HttpAccessProcessor;
import com.sample.http.HttpCreatorImpl;
import com.sample.http.HttpRequest;
import com.sample.http.HttpResponse;
public class ServerThread extends Thread {
	private Socket s;
	public ServerThread(Socket s) {
		this.s=s;
	}
        @Override
        public void run() {
        	HttpCreatorImpl hci=new HttpCreatorImpl(s);
			HttpRequest request=hci.getHttpRequest();
			System.out.println(request.getProtocol());
			HttpResponse response=hci.getHttpResponse();
			HttpAccessProcessor hapi=hci.getHttpAccessProcessor();
			if(request.isStaticResource())
			{
				hapi.processStaticResource(request, response);
			}
			else if(request.isDynamicResource())
			{
				hapi.processDynamicResource(request, response);
			}
			else
			{
				System.out.println("无法处理");
				hapi.sendError(404, request, response);
			}
        }
}
