package com.heiko.customokhttp.okhttp.chain;

import com.heiko.customokhttp.okhttp.Request2;
import com.heiko.customokhttp.okhttp.RequestBody2;
import com.heiko.customokhttp.okhttp.Response2;
import com.heiko.customokhttp.okhttp.SocketRequestServer;

import java.io.IOException;
import java.util.Map;

/**
 * RequestHeaderInterceptor
 * 请求体拦截器处理
 *
 * @author Heiko
 * @date 2019/10/8
 */
public class RequestHeaderInterceptor implements Interceptor2 {
    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {
        //拼接请求头之 请求集
        ChainManager manager = (ChainManager) chain2;
        Request2 request = manager.getRequest();
        String requestMethod = request.getRequestMethod();
        Map<String, String> headerList = request.getHeaderList();
        //无论 Get Post HostName
        headerList.put("Host", new SocketRequestServer().getHost(manager.getRequest()));

        if ("POST".equalsIgnoreCase(requestMethod)) {
            // 请求体
            /**
             * Content-Length:48
             * Content-Type:application/x-www-form-urlencoded
             */
            headerList.put("Content-Length", String.valueOf(request.getRequestBody().getBody().length()));
            headerList.put("Content-Type", RequestBody2.TYPE);
        }

        return chain2.getResponse(request);
    }
}
