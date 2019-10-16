package com.ethanco.glidetest.glide.cache;

import com.ethanco.glidetest.glide.resource.Value;

/**
 * 内存缓存中，元素被移除的接口回调
 *
 * @author Heiko
 * @date 2019/10/16
 */
public interface MemoryCacheCallback {
    /**
     * 内存缓存中移除的KEY - VALUE
     *
     * @param key
     * @param oldValue
     */
    public void entryRemovedMemoryCache(String key, Value oldValue);
}
