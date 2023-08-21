package com.zhi.demo3;


public abstract class MyHttpServlet implements MyServlet{

    @Override
    public void init() {
        System.out.println(getClass().getName()+"调用init方法");
    }

    @Override
    public void service(MyHttpRequest request, MyHttpResponse response) {
        if ("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else if ("POST".equalsIgnoreCase(request.getMethod())){
            doPost(request,response);
        }else {
            System.out.println("其他方法不做处理");
        }
    }

    public abstract void doGet(MyHttpRequest request, MyHttpResponse response);
    public abstract void doPost(MyHttpRequest request, MyHttpResponse response);
    @Override
    public void destroy() {
        System.out.println(getClass().getName()+"被销毁了");
    }
}
