package com.heiko.customokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.heiko.customokhttp.network.Test01;
import com.heiko.customokhttp.okhttp.Call2;
import com.heiko.customokhttp.okhttp.Callback2;
import com.heiko.customokhttp.okhttp.OkHttpClient2;
import com.heiko.customokhttp.okhttp.Request2;
import com.heiko.customokhttp.okhttp.RequestBody2;
import com.heiko.customokhttp.okhttp.Response2;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String PATH = "https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        Test01.socketHTTP();
                    }
                }.start();
            }
        });

        findViewById(R.id.btn_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        Test01.socketHTTPPost();
                    }
                }.start();
            }
        });

        findViewById(R.id.btn_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient2 okHttpClient2 = new OkHttpClient2.Builder().build();
                final Request2 request2 = new Request2.Builder().url(PATH).build();
                Call2 call2 = okHttpClient2.newCall(request2);
                call2.enqueue(new Callback2() {
                    @Override
                    public void onFailure(Call2 call, IOException e) {
                        System.out.println("OkHttp请求失败...");
                    }

                    @Override
                    public void onResponse(Call2 call, Response2 response) throws IOException {
                        System.out.println("OkHttp请求成功:"+response.toString());
                    }
                });
            }
        });

        findViewById(R.id.btn_04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient2 okHttpClient2 = new OkHttpClient2.Builder().build();
                RequestBody2 requestBody2 = new RequestBody2();
                requestBody2.addBody("city","110101");
                requestBody2.addBody("key","13cb58f5884f9749287abbead9c658f2");
                final Request2 request2 = new Request2.Builder().url(PATH).post(requestBody2).build();
                Call2 call2 = okHttpClient2.newCall(request2);
                call2.enqueue(new Callback2() {
                    @Override
                    public void onFailure(Call2 call, IOException e) {
                        System.out.println("OkHttp请求失败...");
                    }

                    @Override
                    public void onResponse(Call2 call, Response2 response) throws IOException {
                        System.out.println("OkHttp请求成功:"+response.toString());
                    }
                });
            }
        });
    }
}
