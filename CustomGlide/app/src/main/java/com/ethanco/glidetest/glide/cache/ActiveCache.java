package com.ethanco.glidetest.glide.cache;

import android.graphics.Bitmap;

import com.ethanco.glidetest.glide.Tool;
import com.ethanco.glidetest.glide.resource.Value;
import com.ethanco.glidetest.glide.resource.ValueCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动缓存 -- 真正被使用的资源
 * 职责:给正在使用的资源存储
 *
 * @author EthanCo
 * @since 2019/10/6
 */
public class ActiveCache {
    // 容器
    private Map<String, CustomoWeakReference> mapList = new HashMap<>();
    private Map<String, Bitmap> mapValueList = new HashMap<>();

    private ReferenceQueue<Value> queue; // 目的：为了监听这个弱引用 是否被回收了
    private boolean isCloseThread;
    private Thread thread;
    private boolean isShoudonRemove; //为了区分手动Remove和自动Remove
    private ValueCallback valueCallback;

    public ActiveCache(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    /**
     * TODO 添加 活动缓存
     *
     * @param key
     * @param value
     */
    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);

        // 没有问题
        // Log.d("activeCache", "put: Inputkey:" + key + " --- value:" + value.getmBitmap() + "对应 key:" + value.getKey());

        // 绑定Value的监听 --> Value发起来的（Value没有被使用了，就会发起这个监听，给外界业务需要来使用）
        value.setCallback(valueCallback);

        // 存储 --》 容器
        mapList.put(key, new CustomoWeakReference(value, getQueue(), key));
        mapValueList.put(key, value.getmBitmap());
    }

    /**
     * 给外界获取Value
     *
     * @param key
     * @return
     */
    public Value get(String key) {
        CustomoWeakReference valueWeakRefrence = mapList.get(key);
        if (valueWeakRefrence != null) {
            return valueWeakRefrence.get();
        }
        return null;
    }

    /**
     * 手动删除 (移除)，被动移除
     *
     * @param key
     * @return
     */
    public Value remove(String key) {
        isShoudonRemove = true;
        WeakReference<Value> valueWeakReference = mapList.remove(key);
        isShoudonRemove = false; //状态还原，目的是为了让GC自动移除，继续工作
        if (null != valueWeakReference) {
            return valueWeakReference.get();
        }
        return null;
    }

    /**
     * 释放 关闭线程
     */
    public void closeThread(){
        isCloseThread = true;
        /*if (null != thread) {
            thread.interrupt(); //中断线程
            try {
                thread.join(5000);
                if (thread.isAlive()) {
                    throw new IllegalStateException("活动缓存中 关闭线程 线程没有停止下来...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        mapList.clear();
        System.gc();
    }

    /**
     * 为了监听 弱引用被回收，被动移除的
     *
     * ---> 回收机制:GC扫描的时候回收 (弱引用)
     * ReferenceQueue即这样的一个对象，当一个obj被gc掉之后，其相应的包装类，即ref对象会被放入queue中。
     * 详见https://www.jianshu.com/p/73260a46291c
     *
     * @return
     */
    private ReferenceQueue<Value> getQueue() {
        if (queue == null) {
            queue = new ReferenceQueue<>();

            // 监听这个弱引用 是否被回收了
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();

                    while (!isCloseThread) {
                        try {
                            if (!isShoudonRemove) {
                                // queue.remove(); 阻塞式的方法，只有当弱引用被回收的时候，才会执行

                                Reference<? extends Value> remove = queue.remove(); // 如果已经被回收了，就会执行到这个方法
                                CustomoWeakReference weakReference = (CustomoWeakReference) remove;
                                // 移除容器     !isShoudonRemove：为了区分手动移除 和 被动移除
                                if (mapList != null && !mapList.isEmpty() && !mapValueList.isEmpty()) {
                                    mapList.remove(weakReference.key);
                                    mapValueList.remove(weakReference.key);
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            };
            thread.start();
        }
        return queue;
    }

    /**
     * 监听弱引用 成为弱引用的子类  为什么要成为弱引用的子类（目的：为了监听这个弱引用 是否被回收了）
     */
    public static final class CustomoWeakReference extends WeakReference<Value> {

        private String key;
        private Value value;

        public CustomoWeakReference(Value value, ReferenceQueue<? super Value> queue, String key) {
            super(value, queue);
            this.key = key;
            this.value = value;

            // Log.d("activeCache", "构造 put: Inputkey:" + key + " --- value:" + this.value.getmBitmap() + "对应 key:" + this.value.getKey());
        }

        public Value getValue() {
            return this.value;
        }
    }
}
