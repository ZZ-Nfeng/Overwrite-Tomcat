package com.zhi.demo3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestResp {

    public static void main(String[] args) {
        try {
            //得到ServerSocket，在8080端口进行监听
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("---------服务器启动成功----------");
            while (!serverSocket.isClosed()) {
                //等待连接
                Socket socket = serverSocket.accept();
                System.out.println("连接成功.....");
                OutputStream outputStream = socket.getOutputStream();
                MyHttpResponse myHttpResponse = new MyHttpResponseImpl(outputStream);
                myHttpResponse.write("测试成功.....");
                System.out.println("断开连接.....");
                //关闭流
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("服务器启动失败");
        }
    }
}