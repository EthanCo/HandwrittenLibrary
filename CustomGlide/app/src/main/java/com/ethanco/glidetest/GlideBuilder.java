package com.ethanco.glidetest;

/**
 * GlideBuilder
 *
 * @author Heiko
 * @date 2019/10/22
 */
class GlideBuilder {
    public Glide build() {
        RequestManagerRetriver requestManagerRetriver = new RequestManagerRetriver();

        Glide glide = new Glide(requestManagerRetriver);
        return glide;
    }
}
