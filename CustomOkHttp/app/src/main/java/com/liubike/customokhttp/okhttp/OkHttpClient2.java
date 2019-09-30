package com.liubike.customokhttp.okhttp;

/**
 * OkHttpClient2
 *
 * @author Heiko
 * @date 2019/9/29
 */
public class OkHttpClient2 {
    private Dispatcher2 dispatcher;
    private Boolean isCanceled;
    private int recount = 3; //重试次数

    public Dispatcher2 dispatcher() {
        return dispatcher;
    }


    public OkHttpClient2() {
        this(new Builder());
    }

    public OkHttpClient2(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.isCanceled = builder.isCanceled;
    }


    public boolean getCanceled() {
        return isCanceled;
    }

    public int getRecount() {
        return recount;
    }

    public Call2 newCall(Request2 request) {
        return new RealCall2(this, request);
    }

    public final static class Builder {
        private Dispatcher2 dispatcher;
        private Boolean isCanceled;
        private int recount;

        public OkHttpClient2 build() {
            this.dispatcher = new Dispatcher2();
            return new OkHttpClient2(this);
        }

        public Builder dispatcher(Dispatcher2 dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        /**
         * 用户取消请求
         *
         * @return
         */
        public Builder canceled() {
            this.isCanceled = true;
            return this;
        }

        /**
         * 重试次数
         *
         * @param recount
         * @return
         */
        public Builder recount(int recount) {
            this.recount = recount;
            return this;
        }
    }
}
