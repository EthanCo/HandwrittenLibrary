package com.liubike.customokhttp.okhttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求体
 *
 * @author Heiko
 * @date 2019/10/8
 */
public class RequestBody2 {
    // 表单提交Type application/x-www-form-urlencoded
    public static final String TYPE = "application/x-www-form-urlencoded";

    private final String UTC = "utf-8";

    //请求体集合 a=123&b=666
    Map<String, String> bodys = new HashMap<>();

    /**
     * 添加请求体信息
     *
     * @param key
     * @param value
     */
    public void addBody(String key, String value) {
        // 需要URL编码
        try {
            bodys.put(URLEncoder.encode(key, UTC), URLEncoder.encode(value, UTC));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到请求体信息
     */
    public String getBody() {
        StringBuffer stringBuffer = new StringBuffer();
        //a=123&b=666
        for (Map.Entry<String, String> entry : bodys.entrySet()) {
            stringBuffer.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        // a=123&b=666 & 删除&
        if (stringBuffer.length() != 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}
