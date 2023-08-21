package com.zhi.demo3;


public class MyDogServlet extends MyHttpServlet {
    @Override
    public void doGet(MyHttpRequest request, MyHttpResponse response) {
        response.write("<h1>我是dog</h1>");
    }

    @Override
    public void doPost(MyHttpRequest request, MyHttpResponse response) {
        this.doGet(request,response);
    }
}