package com.zhi.demo3;

public interface MyServlet {

    public void init();

    public void service(MyHttpRequest request, MyHttpResponse response);

    public void destroy();
}
