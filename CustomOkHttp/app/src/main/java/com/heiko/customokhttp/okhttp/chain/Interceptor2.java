package com.heiko.customokhttp.okhttp.chain;

import com.heiko.customokhttp.okhttp.Response2;

import java.io.IOException;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/30
 */
public interface Interceptor2 {

    Response2 doNext(Chain2 chain2) throws IOException;


}
