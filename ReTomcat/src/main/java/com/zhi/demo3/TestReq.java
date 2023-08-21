package com.zhi.demo3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestReq {

    public static void main(String[] args) {
        try {
            //得到ServerSocket，在8080端口进行监听
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("---------服务器启动成功----------");
            while (!serverSocket.isClosed()) {
                //等待连接
                Socket socket = serverSocket.accept();
                System.out.println("连接成功.....");
                //得到输入流
                InputStream inputStream = socket.getInputStream();
                MyHttpRequest myHttpRequest = new MyHttpRequestImpl(inputStream);
                System.out.println(myHttpRequest);
                //写回数据
                socket.getOutputStream().write("HTTP/1.1 200\r\n\r\nhello".getBytes());
                System.out.println("断开连接.....");
                //关闭流
                inputStream.close();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("服务器启动失败");
        }
    }
}