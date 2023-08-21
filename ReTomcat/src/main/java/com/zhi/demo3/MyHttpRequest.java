package com.zhi.demo3;

public interface MyHttpRequest {
    String getParameter(String name);

    String getMethod();

    String getUri();
}
