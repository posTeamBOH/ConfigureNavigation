package com.choice.http;
import com.choice.utils.ServletMappingUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
public class HttpRequestImpl implements HttpRequest{
	//客户端的Socket
	private Socket s;
	private InputStream is=null;//输入流
	private BufferedReader br=null;
	private HashMap<String,String> hmHeader=new HashMap<String,String>();//消息报头
	private HashMap<String,String> hmparameters=new HashMap<String, String>();//参数集合
	private boolean isNullRequest=false;//是否为空请求，默认false
	private String protocol=null;//请求协议
	private String requestMethod=null;//请求方法
	private String requestPath=null;//请求路径
	public HttpRequestImpl(Socket s) {
		this.s=s;
		getInfos();//调用方法
	 }
	private void getInfos()//定义一个方法，用于处理获取的客户端信息
	{
		try {
			is=s.getInputStream();
			br=new BufferedReader(new InputStreamReader(is));
			//解析第一行
			String str;
			str=br.readLine();//readLine使用回车换行判断一行是否结束
			if(str==null)
			{
				isNullRequest=true;
				return;
			}
			System.out.println(str + "hahahahhh++++++++");
			parseRequestMethodPathProtocol(str);//调用自己创建在本类里边的方法处理第一行信息，方法在后面
		//解析第二行---第八行
			String header=null;                      
                       //判断是否结束，如果结束就退出，这里的判断按较为饶人
                     //首先应该明确br.readLine()的内容，当为true是对应的情况
                     //也就是说当“”（中间没有空格）与br.readLine()相等时，就进入到while中
			while(!"".equals((header=br.readLine()))){
				System.out.println(header + "  表头————————————————————+++++");
				parseRequestHeader(header);
			}
		//post和get
		if(br.ready())//post//POST提交方式判断，如果还有下一行就继续读取信息
		{
			System.out.println("还有正文");
			char[] buf=new char[1024];
			int len=br.read(buf);//使用字节进行读取，因为这一行没有回车换行，readLine无法判断是否结束
			String parameter=new String(buf,0,len);
			parseRequestParamByPost(parameter);//调用自己创建在本类里边的方法处理POST方式提交的正文信息	
		}
		else
		{//get,get参数的位置在第一行连接处
			parseRequestParamByGet(requestPath);//调用自己创建在本类里边的方法处理GET方式提交的正文信息	
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
         //GET方法处理
        private void parseRequestParamByGet(String requestPath2) {
		String []str1=requestPath2.split("[?]");//使用“？”分割字符串
		if(str1.length==2)
		{
			parseRequestParamByPost(str1[1]);
		}
		this.requestPath=str1[0];
			System.out.println(this.requestPath + "get提交");
	}
        //POST方法处理
        private void parseRequestParamByPost(String parameter)  {
			System.out.println(parameter + "@@@@@@post方式的参数");
		 String[] strs=parameter.split("&");
		 if(strs.length>=1)
		 {
			 for(String str:strs)
			 {
				 System.out.println(str + "post方式的参数");
				 String [] sp=str.split("=");
				 if (sp.length > 1){
					 hmparameters.put(sp[0],sp[1]);
				 } else {
					 hmparameters.put(sp[0],"");
				 }

			 }
		 }
	}
	//解析第二行到第八行
	private void parseRequestHeader(String header)  throws Exception{
		String[]  headHost=header.split(": ");
		 if(headHost.length!=2)
	         {
			 throw new Exception("消息报头异常，请重新提交");		 
		 }
		 hmHeader.put(headHost[0],headHost[1]);
	}
	//解析第一行
	private void parseRequestMethodPathProtocol(String str) throws Exception {
		 String[] protocolMethodPath=new String[3];//由于第一行含有三个内容，分割后需要三个String存储
		 protocolMethodPath=str.split(" ");//使用空格分割
		 if(protocolMethodPath.length==3)
		 {
		 requestMethod=protocolMethodPath[0];
		 requestPath=protocolMethodPath[1];
		 protocol=protocolMethodPath[2];
		 }
		 else
		 {
			 throw new Exception("首行参数不合适，请重新提交");
		 }
	}
	//获得请求的协议
	public String getProtocol()
	{
		return protocol;
	}
	//获得请求的方法
	public String getRequestMethod(){
		return requestMethod;
	}
	//获得请求的路径
	public String getRequestPath(){
		return requestPath;
	}
	//获得请求的消息报头
	public Map<String,String> getRequestHeader(){
		return this.hmHeader;
	}
	//根据参数的名字获得请求带过来的参数值
	public String getParameter(String parameterName){
		return hmparameters.get(parameterName);
	}
	// 判断当前请求的否是静态资源
    public boolean isStaticResource() {
        // 存在？？文件？？静态？？
        System.out.println("进入isStaticResource()方法");      
        if (requestPath != null) {
            String parent = System.getProperty("user.dir")+File.separator+"navigationHtml";
			System.out.println(parent+File.separator+requestPath.substring(1));

            File file = new File(parent+File.separator+requestPath.substring(1));

            return file.exists() && file.isFile();
        }
        return false;
    }
	   // 判断当前请求的否是动态资源
    public boolean isDynamicResource() {
        // 存在？？文件？？动态？？
        System.out.println("进入isDynamicResource()方法");
		System.out.println(requestPath + "&*&*&*&");
		if (requestPath == null) return false;
		String path = ServletMappingUtils.getMappingValue(requestPath);
        /*
         * //使用路径判断，当时我不知道还有一个叫做isContains(key)的方法，如果知道的话 就可以使用了，请参见测试类
         * if(isContainers(requestPath())) { return *; }
         */
        System.out.println("ServletMapping中根据requestPath获取的映射：" + path);
        if (path != null) {
            return true;
        }
        return false;
    }
	//判断当前请求的否是为空请求(有些浏览器会自动发送空请求)
	public boolean isNullRequest(){
  return isNullRequest;
	}

	@Override
	public Map<String, String> getParameterMap() {
		return hmparameters;
	}
}