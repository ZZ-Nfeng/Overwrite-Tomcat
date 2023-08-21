package com.zhi.demo3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class MyHttpRequestImpl implements MyHttpRequest {

    //定义成员属性
    private String method;
    private String uri;
    private Map<String, String> parameters = new HashMap<>();
    private InputStream inputStream;

    public MyHttpRequestImpl(InputStream inputStream) {
        this.inputStream = inputStream;
        //对成员属性进行初始化
        init();
    }

    public void init() {
        try {
            //定义一个字节数组
            byte[] bytes = new byte[1024];
            int len = 0;
            //定义一个StringBuilder来对http请求进行存储
            StringBuilder sb = new StringBuilder();
            //读取内容
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
                if (len != bytes.length) break;
            }
            //获得请求行并进行解码
            String requestLine = URLDecoder.decode(sb.substring(0, sb.indexOf("\r")), "utf-8");
            requestLine = URLDecoder.decode(requestLine, "utf-8");
            //得到请求行第一个空格位置
            int firstSpace = requestLine.indexOf(" ");
            //得到请求方法
            method = requestLine.substring(0, firstSpace);
            //通过方法调用不同方法
            if ("GET".equals(method)) {
                setGetParamsAndUri(requestLine);
            } else if ("POST".equals(method)) {
                setPostParamsAndUri(requestLine, sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGetParamsAndUri(String requestLine) {
        //得到最后一个空格索引
        int lastSpace = requestLine.lastIndexOf(" ");
        //通过第一个空格索引和最后一个空格索引就能得到uri和参数
        String s = requestLine.substring(requestLine.indexOf(" ")+1, lastSpace);
        //判断是否含有？
        if (s.contains("?")) {
            //得到第一个？索引
            int firstFlag = s.indexOf("?");
            //？前面的就是uri
            uri = s.substring(0, firstFlag);
            //后面的就是参数
            String params = s.substring(firstFlag + 1);
            //将字符串传给setParams进行初始化
            setParams(params);
        } else {
            //没有?直接赋值
            uri = s;
        }
    }

    public void setPostParamsAndUri(String requestLine, StringBuilder sb) {
        try {
            //得到uri
            uri = requestLine.substring(requestLine.indexOf(" ") + 1, requestLine.lastIndexOf(" "));
            //URLDecoder是对游览器的请求进行解码
            //传入的sb就是请求头+请求体
            //我们通过寻找最后一个换行符就能得到请求体
            String params = URLDecoder.decode(sb.substring(sb.lastIndexOf("\n")+1), "utf-8");
            //和get一样传入参数字符串，然后对参数进行初始化
            setParams(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setParams(String params) {
        //通过&进行分割
        String[] kvs = params.split("&");
        for (String kv : kvs) {
            //通过=进行分割
            String[] keyVal = kv.split("=");
            //如果keyVal是正确写法就将参数和值存入HashMap中进行保存
            if (keyVal.length == 2) {
                String key = keyVal[0];
                String value = keyVal[1];
                parameters.put(key, value);
            }
        }
    }

    @Override
    public String getParameter(String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name);
        }
        return "";
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "MyHttpRequestImpl{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}