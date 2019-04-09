package com.example.demo;

import android.util.Log;

class LifecycleManager {
    private static final String TAG = "LifecycleManager";
    private String mServiceName;
    private LifecycleServiceBase mLifecycleServiceBase;

    LifecycleManager(String name, LifecycleServiceBase service) {
        mServiceName = name;
        mLifecycleServiceBase = service;
        print3();
    }


    void print3() {
        Log.d(TAG, "in LifecycleManager: LifecycleServiceBase service is " + mLifecycleServiceBase);
        Log.d(TAG, "in LifecycleManager: name is " + mServiceName);
    }
}
