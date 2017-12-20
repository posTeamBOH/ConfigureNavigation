package com.choice.http;
import java.util.Map;
    // http协议的请求
public interface HttpRequest {
    //获得请求的协议
    public String getProtocol();
    //获得请求的方法
    public String getRequestMethod();
    //获得请求的路径
    public String getRequestPath();
    //获得请求的消息报头
    public Map<String,String> getRequestHeader();
    //根据参数的名字获得请求带过来的参数值
    public String getParameter(String parameterName);
    //判断当前请求的否是静态资源
    public boolean isStaticResource();
    //判断当前请求的否是动态资源
    public boolean isDynamicResource();
    //判断当前请求的否是为空请求(有些浏览器会自动发送空请求)
    public boolean isNullRequest();
    public Map<String, String> getParameterMap();
}