package com.liubike.customokhttp.okhttp;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/29
 */
public class Response2 {
    private int code;
    private String body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String string(){
        return body;
    }

    @Override
    public String toString() {
        return "Response2{" +
                "code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}
