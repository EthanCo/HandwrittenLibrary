package com.liubike.customokhttp.okhttp;

import android.telecom.Call;

import java.io.IOException;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/29
 */
public interface Call2 {

    Request2 request();

    Response2 execute() throws IOException;

    void enqueue(Callback2 responseCallback);

    void cancel();

    //boolean isExecuted();

    boolean isCanceled();

    //Call clone();

    interface Factory {
        Call newCall(Request2 request);
    }
}
