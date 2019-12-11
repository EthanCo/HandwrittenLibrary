package com.heiko.customokhttp.okhttp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/30
 */
public class SocketRequestServer {

    private final String K = " ";
    private final String VERSION = "HTTP/1.1";
    private final String GRGN = "\r\n";

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
            return port == -1 ? /*url.getDefaultPort()*/80 : port;
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
        URL url = null;
        try {
            url = new URL(request2.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String file = url.getFile();

        // 拼接 请求头的请求行 GET /v3/weather HTTP/1.1
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(request2.getRequestMethod()); //GET POST
        stringBuffer.append(K);
        stringBuffer.append(file);
        stringBuffer.append(K);
        stringBuffer.append(VERSION);
        stringBuffer.append(GRGN);

        //获取请求集 进行拼接
        /**
         * Content-Length:48
         * Host:restapi:amap.com
         * Content-Type:application/x-www-form-urlencoded
         */
        if (!request2.getHeaderList().isEmpty()) {
            Map<String, String> mapList = request2.getHeaderList();
            for (Map.Entry<String, String> entry : mapList.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append(":")
                        .append(K)
                        .append(entry.getValue())
                        .append(GRGN);
            }
            // 拼接空行，代表下面的POST，请求体了
            stringBuffer.append(GRGN);
        }

        //POST请求才有 请求体的拼接
        if ("POST".equalsIgnoreCase(request2.getRequestMethod())) {
            stringBuffer.append(request2.getRequestBody().getBody()).append(GRGN);
        }

        return stringBuffer.toString();
    }
}
