package com.example.service;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;

import java.util.List;


public class ServiceTestActivity extends AppCompatActivity {
    private static final String TAG = "ServiceTestActivity";

    ServiceConnection mConn;
    ServiceConnection mConn2;
    Class<?> mCls;
    String mServiceName;
    Intent mStartServiceIntent;
    Intent mBindServiceIntent;
    Intent mBindServiceIntent2;

    public enum ServiceType{
        LOCAL_SERVICE,
        AIDL_SERVICE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_main);

        /*find view */
        Button startServiceBtn = findViewById(R.id.startServiceBtn);
        Button stopServiceBtn = findViewById(R.id.stopServiceBtn);

        Button bindServiceBtn = findViewById(R.id.bindServiceBtn);
        Button unbindServiceBtn = findViewById(R.id.unbindServiceBtn);

        Button bindServiceBtn2 = findViewById(R.id.bindServiceBtn2);
        Button unbindServiceBtn2 = findViewById(R.id.unbindServiceBtn2);

        Button serviceListBtn = findViewById(R.id.serviceListBtn);

        /*set click listener */
        startServiceBtn.setOnClickListener(clickListener);
        stopServiceBtn.setOnClickListener(clickListener);

        bindServiceBtn.setOnClickListener(clickListener);
        unbindServiceBtn2.setOnClickListener(clickListener);

        bindServiceBtn2.setOnClickListener(clickListener);
        unbindServiceBtn.setOnClickListener(clickListener);

        serviceListBtn.setOnClickListener(clickListener);

        //init conn data
        Intent intent = getIntent();
        int serviceType = intent.getIntExtra("ServiceType", ServiceType.LOCAL_SERVICE.ordinal());
        initConn(ServiceType.values()[serviceType]);
    }

    class LocalServiceConnection implements ServiceConnection {
        private LocalService mService;
        private boolean mBound;

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mService = ((LocalService.LocalBinder)binder).getService();
            mBound = true;
            Log.d(TAG, "call onServiceConnected");
            //mService.doSomething1();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.d(TAG, "call onServiceDisconnected");
        }
    }

    class AIDLServiceConnection implements ServiceConnection {
        private IAIDLService mService;
        private boolean mBound;

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {  //可多次,一个conn一次
            mService = IAIDLService.Stub.asInterface(binder);
            mBound = true;
            Log.d(TAG, "call onServiceConnected");

            try {
                mService.setValue(100);
                mService.getValue();
            } catch (RemoteException e) {
                Log.e(TAG, "getValue exception");
            }
        }

        /**
         * 在连接正常关闭的情况下是不会被调用的, 该方法只在Service 被破坏了或者被杀死的时候调用.
         * 例如, 系统资源不足, 要关闭一些Services, 刚好连接绑定的 Service 是被关闭者之一,  这个时候onServiceDisconnected() 就会被调用。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) { //
            mBound = false;
            Log.d(TAG, "call onServiceDisconnected");
        }
    }

    private void initConn(ServiceType type) {
        switch (type) {
            case LOCAL_SERVICE:
                mConn = new LocalServiceConnection();
                mConn2 = new LocalServiceConnection();
                mCls = LocalService.class;
                mServiceName = "com.example.service.LocalService";
                break;

            case AIDL_SERVICE:
                mConn = new AIDLServiceConnection();
                mConn2 = new AIDLServiceConnection();
                mCls = AIDLService.class;
                mServiceName = "com.example.service.AIDLService";
                break;
        }
    }

    MyClickListener clickListener = new MyClickListener();

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.startServiceBtn:
                    /*如果服务正在运行，直接return*/
                    if (isServiceRunning(mServiceName)){
                        Log.d(TAG, "服务正在运行 return");
                        return;
                    }
                    /*启动服务*/
                    mStartServiceIntent = new Intent(ServiceTestActivity.this, mCls);
                    try {
                        startService(mStartServiceIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "startService exception");
                    }
                    break;

                case R.id.stopServiceBtn:
                    /*停止服务*/
                    if (mStartServiceIntent != null) {
                        try {
                            stopService(mStartServiceIntent);
                        } catch (Exception e) {
                            Log.e(TAG, "stopService exception");
                        }
                    }
                    break;

                case R.id.bindServiceBtn:
                    /*绑定服务*/
                    mBindServiceIntent = new Intent(ServiceTestActivity.this, mCls);
                    try {
                        bindService(mBindServiceIntent, mConn, BIND_AUTO_CREATE);
                    } catch (Exception e) {
                        Log.e(TAG, "bindService exception");
                    }
                    break;

                case R.id.bindServiceBtn2:
                    /*绑定服务2*/
                    mBindServiceIntent2 = new Intent(ServiceTestActivity.this, mCls);
                    try {
                        bindService(mBindServiceIntent2, mConn2, BIND_AUTO_CREATE);
                    } catch (Exception e) {
                        Log.e(TAG, "bindService2 exception");
                    }
                    break;

                case R.id.unbindServiceBtn:
                    /*解绑服务*/
                    if (mConn != null) {
                        try {
                            unbindService(mConn);
                        } catch (Exception e) {
                            Log.e(TAG, "unbindService exception");
                        }
                    }
                    break;

                case R.id.unbindServiceBtn2:
                    /*解绑服务2*/
                    if (mConn2 != null) {
                        try {
                            unbindService(mConn2);
                        } catch (Exception e) {
                            Log.e(TAG, "unbindService2 exception");
                        }
                    }
                    break;

                case R.id.serviceListBtn:
                    isServiceRunning("com.example.service.LocalService");
                    break;
            }
        }
    }



    /**
     * 判断服务是否运行
     */
    private boolean isServiceRunning(final String className) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) {
            Log.d(TAG, "Empty list");
            return false;
        }
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            Log.d(TAG, "RunningServiceInfo: ");
            Log.d(TAG, aInfo.service.getClassName());

            if (className.equals(aInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
