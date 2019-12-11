package com.heiko.customokhttp.okhttp;

import com.heiko.customokhttp.okhttp.chain.ChainManager;
import com.heiko.customokhttp.okhttp.chain.ConnectionServerInterceptor;
import com.heiko.customokhttp.okhttp.chain.Interceptor2;
import com.heiko.customokhttp.okhttp.chain.ReRequestInterceptor;
import com.heiko.customokhttp.okhttp.chain.RequestHeaderInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RealCall2
 *
 * @author Heiko
 * @date 2019/9/29
 */
public class RealCall2 implements Call2 {
    private boolean executed;
    private OkHttpClient2 okHttpClient2;
    private Request2 request2;

    public RealCall2(OkHttpClient2 okHttpClient2, Request2 request2) {
        this.okHttpClient2 = okHttpClient2;
        this.request2 = request2;
    }

    public OkHttpClient2 getOkHttpClient2() {
        return okHttpClient2;
    }

    @Override
    public Request2 request() {
        return null;
    }

    @Override
    public Response2 execute() throws IOException {
        return null;
    }

    @Override
    public void enqueue(Callback2 responseCallback) {
        synchronized (this) {
            if (executed) {
                //不能重复执行
                throw new IllegalStateException("Already Executed");
            }
            executed = true;
        }

        okHttpClient2.dispatcher().enqueue(new AsyncCall2(responseCallback));
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    final class AsyncCall2 implements Runnable {
        private Callback2 callback2;


        public AsyncCall2(Callback2 responseCallback) {
            this.callback2 = responseCallback;
        }

        public Request2 getRequest() {
            return RealCall2.this.request2;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response2 response = getResponseWithInterceptorChain();
                if (false) { //TODO retryAndFollowUpInterceptor.isCanceled()
                    signalledCallback = true;
                    callback2.onFailure(RealCall2.this, new IOException("Canceled"));
                } else {
                    signalledCallback = true;
                    callback2.onResponse(RealCall2.this, response);
                }
            } catch (IOException e) {
                if (signalledCallback) {
                    System.out.println("用户使用过程中，出错了...");
                } else {
                    callback2.onFailure(RealCall2.this, e);
                }
            } finally {
                okHttpClient2.dispatcher().finished(this);
            }
        }
    }

    Response2 getResponseWithInterceptorChain() throws IOException {
//        Response2 response2 = new Response2();
//        response2.setBody("流程走通...");
//        return null;

        List<Interceptor2> interceptor2List = new ArrayList<>();
        interceptor2List.add(new ReRequestInterceptor()); //重试拦截器
        interceptor2List.add(new RequestHeaderInterceptor()); //请求体拦截器
        interceptor2List.add(new ConnectionServerInterceptor()); //连接服务器的拦截器

        ChainManager chainManager = new ChainManager(interceptor2List, 0, request2, RealCall2.this);
        return chainManager.getResponse(request2); //最终返回的Response
    }
}
