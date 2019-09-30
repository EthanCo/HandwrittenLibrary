package com.liubike.customokhttp.okhttp;

import java.util.concurrent.ThreadFactory;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/30
 */
public class Util {
    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }
}
