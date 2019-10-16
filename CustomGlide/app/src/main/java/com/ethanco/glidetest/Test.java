package com.ethanco.glidetest;

import android.util.Log;

import com.ethanco.glidetest.glide.cache.MemoryCache;
import com.ethanco.glidetest.glide.cache.MemoryCacheCallback;
import com.ethanco.glidetest.glide.resource.Value;

/**
 * Test
 *
 * @author Heiko
 * @date 2019/10/16
 */
public class Test {

    //伪代码
    private static final String TAG = "Z-Test";

    public void test() {
        MemoryCache memoryCache = new MemoryCache(5);
        memoryCache.put("aaaaddd", new Value());

        Value v = memoryCache.get("aaaaddd");
        memoryCache.setMemoryCacheCallback(new MemoryCacheCallback() {
            @Override
            public void entryRemovedMemoryCache(String key, Value oldValue) {
                Log.i(TAG, "entryRemovedMemoryCache 内存缓存中的元素被移除了 [被动移除] value:" + oldValue + " KEY:" + key);
            }
        });
        memoryCache.shoudonRemove("aaaaddd");
    }
}
