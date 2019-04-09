package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;


public class AIDLService extends Service {
    private static final String TAG = "AIDLService";

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
    public void onDestroy() { //只一次调用, 没有绑定时执行stopService时 或者 最后一次解绑时,且没有startService时
        Log.d(TAG, "onDestroy called");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) { //只调用一次,第一次bindService时
        Log.d(TAG, "onBind called");
        return mAIDLServiceImpl;
    }

    @Override
    public boolean onUnbind(Intent intent) { //最后一次解绑时
        Log.d(TAG, "onUnbind called");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) { //内部重连?
        Log.d(TAG, "onRebind called");
        super.onRebind(intent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }



    /******************* AIDLServiceImpl *****************************/

    public class AIDLServiceImpl extends IAIDLService.Stub { //提供的服务由内部类实现
        private int mValue;

        /*************** IAIDLService Interface *****/

        public void setValue(int value) {
            mValue = value;
            Log.i(TAG, "setValue " + mValue);
        }

        public int getValue() {
            Log.i(TAG, "getValue " + mValue);
            return mValue;
        }

    }

    private AIDLServiceImpl mAIDLServiceImpl = new AIDLServiceImpl();

}
