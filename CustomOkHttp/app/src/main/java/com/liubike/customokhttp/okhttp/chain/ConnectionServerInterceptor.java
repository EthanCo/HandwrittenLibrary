package com.liubike.customokhttp.okhttp.chain;

import com.liubike.customokhttp.okhttp.Request2;
import com.liubike.customokhttp.okhttp.Response2;
import com.liubike.customokhttp.okhttp.SocketRequestServer;

import java.io.IOException;
import java.net.Socket;

/**
 * 连接服务器的拦截器
 *
 * @author Heiko
 * @date 2019/10/8
 */
public class ConnectionServerInterceptor implements Interceptor2 {

    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {
        SocketRequestServer srs = new SocketRequestServer();

        Request2 request = chain2.getRequest();
        Socket socket = new Socket(srs.getHost(request), srs.getPort(request));

        // todo 请求


        // todo 响应

        return null;
    }
}
