package com.ethanco.glidetest.fragment;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * FragmentActivity 生命周期关联管理
 */
public class FragmentActivityFragmentManager extends Fragment {

    public FragmentActivityFragmentManager(){}

    private LifecycleCallback lifecycleCallback;

    @SuppressLint("ValidFragment")
    public FragmentActivityFragmentManager(LifecycleCallback lifecycleCallback) {
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
