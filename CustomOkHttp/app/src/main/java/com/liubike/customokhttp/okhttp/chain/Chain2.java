package com.liubike.customokhttp.okhttp.chain;

import com.liubike.customokhttp.okhttp.Request2;
import com.liubike.customokhttp.okhttp.Response2;

import java.io.IOException;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/30
 */
public interface Chain2 {
    Request2 getRequest();

    Response2 getResponse(Request2 request2) throws IOException;
}
