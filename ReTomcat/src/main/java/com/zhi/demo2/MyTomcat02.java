package com.zhi.demo2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyTomcat02 {

    public static void main(String[] args) {
        try {
            //得到ServerSocket，在8080端口进行监听
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("---------服务器启动成功----------");
            while (!serverSocket.isClosed()) {
                System.out.println("-------------------------------------------------");
                System.out.println("等待连接");
                //等待连接
                Socket socket = serverSocket.accept();
                System.out.println("连接成功");
                //创建一个线程来处理请求
                RequestHandler requestHandler = new RequestHandler(socket);
                new Thread(requestHandler).start();
            }
        } catch (IOException e) {
            System.out.println("服务器启动失败");
        }
    }
}