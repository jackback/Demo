package com.example.demo;

import android.util.Log;

class RadioService extends LifecycleServiceBase {
    private static final String TAG = "RadioService";
    private static final String NAME = "RadioService";

    RadioService () {
        //super(NAME, this);
        super(NAME);
        print1();
    }

    void print1() {
        Log.d(TAG, "in RadioService: this is " + this);
    }
}
