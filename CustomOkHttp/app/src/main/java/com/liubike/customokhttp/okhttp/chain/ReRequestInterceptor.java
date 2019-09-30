package com.liubike.customokhttp.okhttp.chain;

import android.util.Log;

import com.liubike.customokhttp.okhttp.RealCall2;
import com.liubike.customokhttp.okhttp.Response2;

import java.io.IOException;

/**
 * 重试拦截器
 *
 * @author Heiko
 * @date 2019/9/30
 */
public class ReRequestInterceptor implements Interceptor2 {
    public static final String TAG = ReRequestInterceptor.class.getSimpleName();

    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {
        Log.d(TAG, "我是重试拦截器，我要执行了");

        ChainManager chainManager = (ChainManager) chain2;
        RealCall2 realCall2 = chainManager.getCall();
        int reCount = realCall2.getOkHttpClient2().getRecount();

        IOException ioException = null;

        // 重试次数
        if (reCount != 0) {
            for (int i = 0; i < reCount; i++) {
                Response2 response2 = null;
                try {
                    Log.d(TAG, "我是重试拦截器，我要放回Response2了");

                    response2 = chain2.getResponse(chainManager.getRequest());
                    return response2;
                } catch (IOException e) {
                    ioException = e;
                }
            }
        }
        throw ioException;
    }
}
