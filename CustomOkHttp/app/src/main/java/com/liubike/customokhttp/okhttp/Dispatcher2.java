package com.liubike.customokhttp.okhttp;

import android.support.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Dispatcher2
 *
 * @author Heiko
 * @date 2019/9/29
 */
public class Dispatcher2 {
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;

    private @Nullable
    ExecutorService executorService;

    private final Deque<RealCall2.AsyncCall2> readyAsyncCalls = new ArrayDeque<>();

    private final Deque<RealCall2.AsyncCall2> runningAsyncCalls = new ArrayDeque<>();

    private final Deque<RealCall2> runningSyncCalls = new ArrayDeque<>();

    public void enqueue(RealCall2.AsyncCall2 call) {
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            readyAsyncCalls.add(call);
        }
    }

    /**
     * 线程池 - 缓存方案
     *
     * @return
     */
    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread result = new Thread(runnable);
                    result.setName("自定义线程....");
                    result.setDaemon(false); //不是守护线程
                    return result;
                }
            });
        }
        return executorService;
    }

    /**
     * 判断AsyncCall2中的Host，在运行的队列中，计数，然后放回
     *
     * @param call Request.host
     * @return
     */
    private int runningCallsForHost(RealCall2.AsyncCall2 call) {
        /*int result = 0;
        for (RealCall2.AsyncCall2 c : runningAsyncCalls) {
            if (c.get().forWebSocket) continue;
            if (c.host().equals(call.host())) result++;
        }
        return result;*/

        int count = 0;
        if (runningAsyncCalls.isEmpty()) {
            return 0;
        }

        SocketRequestServer src = new SocketRequestServer();

        for (RealCall2.AsyncCall2 runningAsyncCall : runningAsyncCalls) {
            if (src.getHost(runningAsyncCall.getRequest()).equals(call.getRequest())) {
                count++;
            }
        }

        return count;
    }

    /**
     * 1.移除运行完成的任务
     * 2.吧等待队列里面的所有任务取出来[执行]
     * @param call2
     */
    public void finished(RealCall2.AsyncCall2 call2) {
        // 当前运行的任务 回收
        runningAsyncCalls.remove(call2);

        //考虑等待队列里面是否有任务，如果有任务是需要执行的
        if (readyAsyncCalls.isEmpty()) {
            return;
        }

        //把等待队列中的任务给移动运行队列
        for (RealCall2.AsyncCall2 readyAsyncCall : readyAsyncCalls) {
            readyAsyncCalls.remove(readyAsyncCall);
            runningAsyncCalls.add(readyAsyncCall); //把刚刚删除的等待队列添加到运行队列
            //开始执行任务
            executorService().execute(readyAsyncCall);
        }
    }
}
