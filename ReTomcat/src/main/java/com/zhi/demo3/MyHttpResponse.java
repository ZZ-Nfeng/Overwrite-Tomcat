package com.zhi.demo3;

public interface MyHttpResponse {
    void setContentType(String type);

    void write(String mess);

    void setStatus(int status);

    void setHeader(String s, String s1);

    void sendRedirect(String s);
}
