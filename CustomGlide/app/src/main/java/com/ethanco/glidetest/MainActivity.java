package com.ethanco.glidetest;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2, imageView3;
    public static final String PATH = "https://b-ssl.duitang.com/uploads/item/201208/30/20120830173930_PBfJE.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    // 加载此图片：https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg
    public void t1(View view) {
        Glide.with(this).load(PATH).into(imageView1);
    }

    public void t2(View view) {
        Glide.with(this).load(PATH).into(imageView2);
    }

    public void t3(View view) {
        Glide.with(this).load(PATH).into(imageView3);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void goSecondAct(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        finish();
    }
}
