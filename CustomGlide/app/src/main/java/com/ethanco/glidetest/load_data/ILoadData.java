package com.ethanco.glidetest.load_data;

import android.content.Context;

import com.ethanco.glidetest.cache.Value;

/**
 * 加载外部资源 标准
 */
public interface ILoadData {

    // 加载外部资源的行为
    public Value loadResource(String path, ResponseListener responseListener, Context context);

}
