package com.liubike.customokhttp.okhttp;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/30
 */
public class SocketRequestServer {

    /**
     * 通过Request对象，寻找到域名HOST
     *
     * @param request2
     * @return
     */
    public String getHost(Request2 request2) {
        try {
            URL url = new URL(request2.getUrl());
            return url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 端口
     *
     * @param request2
     * @return
     */
    public int getPort(Request2 request2) {
        try {
            URL url = new URL(request2.getUrl());
            int port = url.getPort();
            return port == -1 ? url.getDefaultPort() : port;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取请求头的所有信息
     *
     * @param request2
     * @return
     */
    public String getRequestHeaderAll(Request2 request2) {
        //得到请求方式
        try {
            URL url = new URL(request2.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // 拼接 请求头的请求行 GET /v3/weather HTTP/1.1
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        return stringBuffer.toString();
    }
}
