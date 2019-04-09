package com.example.demo;

import android.util.Log;

abstract class LifecycleServiceBase {
    private static final String TAG = "LifecycleServiceBase";
    private String mServiceName;
    private LifecycleManager mLifecycleManager;

    /*LifecycleServiceBase(String name, LifecycleServiceBase service) {
        mServiceName = name;
        mLifecycleManager = new LifecycleManager(name, service);
    }*/

    LifecycleServiceBase(String name) {
        mServiceName = name;
        init();
    }

    private void init() {
        print2();

        //如果new子类的话, 这个this 是父类还是子类,还是说是一样的
        mLifecycleManager = new LifecycleManager(mServiceName, this);

    }

    void print2() {
        Log.d(TAG, "in LifecycleServiceBase: this is " + this);
    }

    void print22() {

        Log.d(TAG, "in LifecycleServiceBase: mLifecycleManager is " + mLifecycleManager);
    }



}
