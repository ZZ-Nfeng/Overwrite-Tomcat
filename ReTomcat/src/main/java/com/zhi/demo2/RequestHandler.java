package com.zhi.demo2;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable{

    //定义一个socket对象
    private Socket socket = null;
    //在初始化对象时传入socket
    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"线程开启成功，开始处理数据");
            //得到输入流
            InputStream inputStream = socket.getInputStream();
            //将字节输入流转为字符输入流。并将编码设置为utf-8
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = null;
            //循环读取
            System.out.println("开始读取内容");
            while ((line = bufferedReader.readLine()) != null) {
                //如果为空就结束读取
                if (line.length() == 0) break;
                System.out.println(line);
            }
            System.out.println("读取内容结束");
            //得到输出流
            OutputStream outputStream = socket.getOutputStream();
            //设置响应头
            String responseHead = "HTTP/1.1 200\r\n" +
                    "Content-Type: text/html;charset=utf-8\r\n\r\n";
            //设置响应体
            String responseBody = "<h1>这是我自己写的Tomcat</h1>";
            //http响应消息内容
            String response = responseHead + responseBody;
            //写回数据
            outputStream.write(response.getBytes());
            System.out.println("响应成功");
            //关闭流
            outputStream.close();
            inputStream.close();
            socket.close();
            System.out.println("本次连接结束，正常关闭socket");
            System.out.println("-------------------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //保证一定关闭socket
            if (!socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("socket关闭失败");
                }
            }
        }
    }
}