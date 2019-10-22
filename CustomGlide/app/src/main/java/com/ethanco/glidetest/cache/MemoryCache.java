package com.ethanco.glidetest.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.ethanco.glidetest.cache.resource.Value;

/**
 * 内存缓存:为第二次缓存服务
 * LRU算法:最近没有使用的元素，会自动被移除掉
 * LruCache内部使用了LinkedHashMap，LinkedHashMap传入true拥有插入排序的功能
 *
 * @author Heiko
 * @date 2019/10/16
 */
public class MemoryCache extends LruCache<String, Value> {

    private boolean shoudonRemove;

    //手动remove
    public Value shoudonRemove(String key) {
        shoudonRemove = true;
        Value value = remove(key);
        shoudonRemove = false;
        return value;
    }

    // put get -> LruCache都有

    private MemoryCacheCallback memoryCacheCallback;

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        this.memoryCacheCallback = memoryCacheCallback;
    }

    /**
     * 传入元素最大值给LruCache
     *
     * @param maxSize
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Value value) {
        // return super.sizeOf(key, value);
        Bitmap bitmap = value.getmBitmap();
        /*// 最开始的时候 API 1
        int result = bitmap.getRowBytes() * bitmap.getHeight();

        //API 12 3.0
        result = bitmap.getByteCount(); //在bitmap内存复用上有区别

        //API 19 4.4
        result = bitmap.getAllocationByteCount(); //在bitmap内存复用上有区别(整个的)，复用内存更多*/

        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }

    /**
     * 1.重复的KEY
     * 2.最少使用的元素会被移除
     *
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Value oldValue, Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);

        if (memoryCacheCallback != null && !shoudonRemove) { //!shoudoRemove == 被动的
            memoryCacheCallback.entryRemovedMemoryCache(key, oldValue);
        }
    }
}
