package com.ethanco.glidetest.cache.disk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.ethanco.glidetest.Tool;
import com.ethanco.glidetest.cache.resource.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 磁盘缓存
 * https://github.com/JakeWharton/DiskLruCache : 实现了本地存储的LruCache
 * 回收方式:LRU算法，访问排序
 *
 * @author Heiko
 * @date 2019/10/17
 */
public class DiskCache {
    //SD/disk_lru_cache_dir/dgjhbjkbnkjhoihlliuyigdza34667g4dsf23ref3er23fwe3rt13tfedg31t534egef3e
    public static final String DISKLRU_CACHE_DIR = "disk_lru_cache_dir"; //磁盘缓存的目录
    private static final String TAG = "DiskCache";
    private DiskLruCache diskLruCache;
    private final int APP_VERSION = 1; //版本号，一但修改，之前的缓存失效
    private final int VALUE_COUNT = 1;
    private final long MAX_SIZE = 1024 * 1024 * 10; //以后修改成 使用者可以设置的

    public DiskCache() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + DISKLRU_CACHE_DIR);
        try {
            diskLruCache = DiskLruCache.open(file, APP_VERSION, VALUE_COUNT, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //put
    public void put(String key, Value value) {
        DiskLruCache.Editor editor = null;
        OutputStream outputStream = null;
        try {
            editor = diskLruCache.edit(key);
            outputStream = editor.newOutputStream(0);//index不能大于VAVLUE_COUNT
            Bitmap bitmap = value.getmBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); //把Bitmap写入到outputStream
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //失败
            try {
                editor.abort();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(TAG, "put:editor.abort() e:" + e.getMessage());
            }
        } finally {
            try {
                editor.commit(); //记得一定要提交
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "put: editor.commit(); e:" + e.getMessage());
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "put: outputStream.close(); e:" + e.getMessage());
                }
            }
        }
    }

    public Value get(String key) {
        Tool.checkNotEmpty(key);

        InputStream inputStream = null;
        Value value = Value.getInstance();
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null) {
                inputStream = snapshot.getInputStream(0); //index 不能大于VALUE_COUNT
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                value.setmBitmap(bitmap);
                value.setKey(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "get: inputStream.close(); e:" + e.getMessage());
                }
            }
        }
        return value;
    }

}
