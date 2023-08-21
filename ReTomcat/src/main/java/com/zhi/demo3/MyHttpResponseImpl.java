package com.zhi.demo3;

import java.io.IOException;
import java.io.OutputStream;

public class MyHttpResponseImpl implements MyHttpResponse {
    private String responseRow = "HTTP/1.1 ";
    private String contentType = "Content-Type: text/html;charset=utf-8\r\n\r\n";
    private String responseHead = "";
    private OutputStream outputStream;
    private int status = 200;

    public MyHttpResponseImpl(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void setContentType(String type) {
        contentType = "Content-Type:"+type+"\r\n\r\n";
    }


    @Override
    public void write(String mess){
        mess = responseRow+status+"\r\n"+responseHead+contentType+mess;
        try {
            outputStream.write(mess.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void setHeader(String s, String s1) {
        String head = s+":"+s1+"\r\n";
        responseHead += head;
    }

    @Override
    public void sendRedirect(String s){
        setStatus(302);
        setHeader("location",s);
        write("");
    }
}