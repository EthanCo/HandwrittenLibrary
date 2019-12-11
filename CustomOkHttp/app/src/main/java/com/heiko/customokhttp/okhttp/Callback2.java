package com.heiko.customokhttp.okhttp;

import java.io.IOException;

/**
 * TODO
 *
 * @author Heiko
 * @date 2019/9/29
 */
public interface Callback2 {

    void onFailure(Call2 call, IOException e);

    void onResponse(Call2 call, Response2 response) throws IOException;
}
