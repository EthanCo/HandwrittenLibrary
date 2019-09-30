package com.liubike.customokhttp.okhttp.chain;

import com.liubike.customokhttp.okhttp.RealCall2;
import com.liubike.customokhttp.okhttp.Request2;
import com.liubike.customokhttp.okhttp.Response2;

import java.io.IOException;
import java.util.List;

/**
 * 责任节点管理器
 * 对应OKHTTP里的RealInterceptorChain
 *
 * @author Heiko
 * @date 2019/9/30
 */
public class ChainManager implements Chain2 {
    private final List<Interceptor2> interceptors;
    private final int index;
    private final Request2 request2;
    private final RealCall2 call;

    public List<Interceptor2> getInterceptors() {
        return interceptors;
    }

    public Request2 getRequest2() {
        return request2;
    }

    public RealCall2 getCall() {
        return call;
    }

    public ChainManager(List<Interceptor2> interceptors, int index, Request2 request2, RealCall2 call) {
        this.interceptors = interceptors;
        this.index = index;
        this.request2 = request2;
        this.call = call;
    }

    @Override
    public Request2 getRequest() {
        return request2;
    }

    @Override
    public Response2 getResponse(Request2 request2) throws IOException {
        //判断index++ 计数
        if (index >= interceptors.size()) {
            throw new AssertionError();
        }
        if (interceptors.isEmpty()) {
            throw new IOException("interceptors is empty");
        }

        ChainManager next = new ChainManager(interceptors, index + 1, request2, call);
        Interceptor2 interceptor = interceptors.get(index);
        Response2 response = interceptor.doNext(next);
        return response;
    }
}
