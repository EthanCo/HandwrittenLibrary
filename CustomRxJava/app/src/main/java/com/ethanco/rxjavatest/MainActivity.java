package com.ethanco.rxjavatest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ethanco.rxjavatest.rxjava.Observable;
import com.ethanco.rxjavatest.rxjava.ObservableOnSubscribe;
import com.ethanco.rxjavatest.rxjava.Observer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Z-Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void r01(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(Observer<? super Integer> observer) {
                Log.i(TAG, "subscribe : ");
                observer.onNext(112);
                observer.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe() {
                Log.i(TAG, "onSubscribe : ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext : ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError : ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete : ");
            }
        });
    }

    public void r02(View view) {
        Observable.just("hello", "world","!")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe() {
                        Log.i(TAG, "onSubscribe : ");
                    }

                    @Override
                    public void onNext(String string) {
                        Log.i(TAG, "onNext : " + string);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError : ");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete : ");
                    }
                });
    }
}
