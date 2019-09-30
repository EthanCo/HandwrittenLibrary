package com.liubike.customokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liubike.customokhttp.okhttp.Call2;
import com.liubike.customokhttp.okhttp.Callback2;
import com.liubike.customokhttp.okhttp.OkHttpClient2;
import com.liubike.customokhttp.okhttp.Request2;
import com.liubike.customokhttp.okhttp.Response2;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient2 okHttpClient2 = new OkHttpClient2.Builder().build();
                final Request2 request2 = new Request2.Builder().url("http://www.baidu.com").build();
                Call2 call2 = okHttpClient2.newCall(request2);
                call2.enqueue(new Callback2() {
                    @Override
                    public void onFailure(Call2 call, IOException e) {
                        System.out.println("OkHttp请求失败...");
                    }

                    @Override
                    public void onResponse(Call2 call, Response2 response) throws IOException {
                        System.out.println("OkHttp请求成功:"+request2.toString());
                    }
                });
            }
        });
    }
}
