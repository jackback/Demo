package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class LocalService extends Service {
    private static final String TAG = "LocalService";

    @Override
    public void onCreate() { //只会调用一次  startService/bindService
        Log.d(TAG, "onCreate called");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { //可多次调用 startService
        Log.d(TAG, "onStartCommand called");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() { //只一次调用, 没有client绑定时,或者最后一次解绑时
        Log.d(TAG, "onDestroy called");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) { //只调用一次,第一次bindService时
        Log.d(TAG, "onBind called");
        //return null; //not support bind
        return mLocalBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) { //??? 可多次
        Log.d(TAG, "onUnbind called");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind called");
        super.onRebind(intent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    /*************** LocalService Interface *****/
    public void doSomething1() {
        Log.d(TAG, "call LocalService.doSomething1() ");
    }

    public void doSomething2() {
        Log.d(TAG, "call LocalService.doSomething2() ");
    }



    /******************* LocalBinder *****************************/

    class LocalBinder extends Binder { //提供的服务由内部类实现
        public LocalService getService() {
            return LocalService.this; //call LocalService.doSomething1/2
        }

        /*************** LocalBinder Interface *****/

        //interface1?
        public void doSomething1() {
            Log.i(TAG, "call LocalBinder.doSomething1() ");
        }

        //interface2
        public void doSomething2() {
            Log.i(TAG, "call LocalBinder.doSomething2() ");
        }

    }

    private LocalBinder mLocalBinder = new LocalBinder();

}
