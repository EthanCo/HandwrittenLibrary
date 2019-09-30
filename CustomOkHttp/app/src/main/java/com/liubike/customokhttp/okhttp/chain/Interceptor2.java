package com.liubike.customokhttp.okhttp.chain;

import com.liubike.customokhttp.okhttp.Response2;

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
