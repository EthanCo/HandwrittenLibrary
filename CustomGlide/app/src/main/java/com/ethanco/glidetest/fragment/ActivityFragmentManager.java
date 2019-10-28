package com.ethanco.glidetest.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.util.Log;

/**
 * Activity 生命周期关联管理
 */
public class ActivityFragmentManager extends Fragment {

    public ActivityFragmentManager(){}

    private LifecycleCallback lifecycleCallback;

    @SuppressLint("ValidFragment")
    public ActivityFragmentManager(LifecycleCallback lifecycleCallback) {
        this.lifecycleCallback = lifecycleCallback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lifecycleCallback != null) {
            lifecycleCallback.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Z--", "onStop");
        if (lifecycleCallback != null) {
            lifecycleCallback.glideStopAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Z--", "onDestroy");
        if (lifecycleCallback != null) {
            lifecycleCallback.glideRecycleAction();
        }
    }
}
