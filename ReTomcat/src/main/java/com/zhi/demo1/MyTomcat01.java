package com.zhi.demo1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyTomcat01 {

    public static void main(String[] args) {
        try {
            //得到ServerSocket，在8080端口进行监听
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("---------服务器启动成功----------");
            while (!serverSocket.isClosed()) {
                System.out.println("-------------------------------------------------");
                System.out.println("1：等待连接");
                //等待连接
                Socket socket = serverSocket.accept();
                System.out.println("2：连接成功");
                //得到输入流
                InputStream inputStream = socket.getInputStream();
                //将字节输入流转为字符输入流。并将编码设置为utf-8
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line = null;
                //循环读取
                System.out.println("3：开始读取内容");
                while ((line = bufferedReader.readLine()) != null) {
                    //如果为空就结束读取
                    if (line.length() == 0) break;
                    System.out.println(line);
                }
                System.out.println("4：读取内容结束");
                //得到输出流
                OutputStream outputStream = socket.getOutputStream();
                //设置响应头
                String responseHead = "HTTP/1.1 200\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n\r\n";
                //设置响应体
                String responseBody = "这是我自己写的Tomcat";
                //http响应消息内容
                String response = responseHead + responseBody;
                //写回数据
                outputStream.write(response.getBytes());
                System.out.println("5：响应成功");
                //关闭流
                outputStream.close();
                inputStream.close();
                socket.close();
                System.out.println("6：本次连接结束，正常关闭socket");
                System.out.println("-------------------------------------------------\n");
            }
        } catch (IOException e) {
            System.out.println("服务器启动失败");
        }
    }
}