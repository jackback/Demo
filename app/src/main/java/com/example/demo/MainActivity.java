package com.example.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.service.ServiceTestActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find view
        Button browserBtn = findViewById(R.id.BrowserTestBtn);
        Button localBtn = findViewById(R.id.LocalServiceTestBtn);
        Button aidlBtn = findViewById(R.id.AIDLServiceTestBtn);

        //set click listener
        browserBtn.setOnClickListener(clickListener);
        localBtn.setOnClickListener(clickListener);
        aidlBtn.setOnClickListener(clickListener);

    } //onCreate end

    MyClickListener clickListener = new MyClickListener();

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.BrowserTestBtn:
                    Uri uri = Uri.parse("https://www.baidu.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.LocalServiceTestBtn:
                    Intent intent1 = new Intent(MainActivity.this, ServiceTestActivity.class);
                    intent1.putExtra("ServiceType",ServiceTestActivity.ServiceType.LOCAL_SERVICE.ordinal());
                    startActivity(intent1);
                    break;

                case R.id.AIDLServiceTestBtn:
                    Intent intent2 = new Intent(MainActivity.this, ServiceTestActivity.class);
                    intent2.putExtra("ServiceType",ServiceTestActivity.ServiceType.AIDL_SERVICE.ordinal());
                    startActivity(intent2);
                    break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** 涉及到一下文件
     * RadioService.java
     * LifecycleBaseService.java
     * LifecycleManager.java
     * */
    void baseClasssubClassThisTest () {
        RadioService radio = new RadioService();
        Log.d(TAG, "in MainActivity: RadioService is " + radio);
    }

    void testFloatDoubleData () {
        //数据精度测试
        long cost_time  = 123456;
        long cost_time1 = 123;
        long cost_time2 = 123456;
        long cost_time3 = 123456789;
        long cost_time4 = 1234567891;
        //long cost_time5 = 123456789123456;

        float tmp = cost_time;
        float cost_time_f = 177953231/1000000;

        double tmp2 = cost_time;
        double cost_time_d = 177953231/1000000;

        Log.v(TAG, "cost_time_f: " + 177953231f/1000000f + "s");
        Log.v(TAG, "cost_time_d: " + 177953231d/1000000d + "s");

        Log.d(TAG, String.format("cost time1: (%f ms)",(double)cost_time1/USECONDUNIT));
        Log.d(TAG, String.format("cost time2: (%f ms)",(double)cost_time2/USECONDUNIT));
        Log.d(TAG, String.format("cost time3: (%f ms)",(double)cost_time3/USECONDUNIT));
        Log.d(TAG, String.format("cost time4: (%f ms)",(double)cost_time4/USECONDUNIT));

        //Log.d(TAG, String.format("cost time5: (%f ms)",cost_time5/USECONDUNIT));

        //Log.v(TAG, "cost_time_f: " + String.format("%g", 177953231f/1000000000f) + "s");
        //Log.v(TAG, "cost_time_d: " + String.format("%g", 177953231d/1000000000d) + "s");

    }

    void xmlPareTest() {
    /*
        WEEK a = null;
        a = WEEK.AAA;

        sLevelArray.put(100, new LevelInfo(1, "CORE_LV", 5000, 1000, 0));
        sLevelArray.put(1, new LevelInfo(1, "CORE_LV", 5000, 1000, 0));
        Log.v(TAG, "size = " + sLevelArray.size());
        Log.v(TAG, "first one element's key is = " + sLevelArray.keyAt(0));
        Log.v(TAG, "first one element's key is = " + sLevelArray.keyAt(sLevelArray.size()-1));
        //Log.v(TAG, "first one element's key is = " + sLevelArray.keyAt(12));
        STATE_ID_AWAKED = 30;
        STATE_ID_STOPPED = 31;
        map.put("aaa", 100);
        Integer value = map.get("aaa");
        int valueInt = value;
        Log.v(TAG, "value = " + valueInt);

        int size = 0;
        for (int i = 0; i < size; i ++ ) {
            Log.v(TAG, "xxxxx");
        }

        String ret;
        ret = "Radio";
        Log.v(TAG, ret);

        mTimeoutTask = new StateTimerTask();
        mTimer.schedule(mTimeoutTask, 1000);
        //mTimeoutTask.cancel();

        mTimeoutTask = new StateTimerTask();
        mTimer.schedule(mTimeoutTask, 1000);
        //mTimeoutTask.cancel();
*/

    /* xml读取测试
        Log.v(TAG, "onCreate");
        InputStream in = null;
        try {
            in = getResources().getAssets().open("sysctrl_last_source.xml");
            try {
                parse(in, "Radio", 0);
            } catch (Exception e) {
                Log.e(TAG, "parse file exception");
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e(TAG, "open file exception");

        }
*/
    }


    private static final String XML_TAG_SOURCE = "source";
    private static final String XML_TAG_NAME = "name";
    private static final String XML_TAG_LEVEL = "level";
    private static final String XML_TAG_SERVICE = "service";
    private static String mLastSource = null;
    static final double USECONDUNIT = 1000000d;

    private static final int STATE_ID_INIT = 0;
    private static int STATE_ID_AWAKED  = 0;
    private static int STATE_ID_STOPPED = 0;
    int sourceFlag = 0; // last source Element flag
    String cLevel = null;    //current level
    String cService = null;  //current service

    /**
     * parse last source xml config;
     * @parm in service-level info of specified last sourceInputStream;
     * @parm lastSource specified last source, value can be is Radio, USB, etc;
     * @parm flag, init flag, can be 0 or 1
     *              0:fill sLevelTbl and init sSvrInfoMap
     *              1:fill sLevelTbl only.
     */
    private void parse(InputStream in, String lastSource, int flag) throws Exception {
        //private static final String sLvInfoFileName = "sysctrl_last_source.xml";



        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "UTF-8");
        } catch (XmlPullParserException e) {
            Log.e(TAG, "parser.setInput");
        }

        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;

                case XmlPullParser.START_TAG:
                    if (parser.getName().equals(XML_TAG_SOURCE)) {
                        if (parser.getAttributeValue(null, XML_TAG_NAME).equalsIgnoreCase(lastSource)) {
                            Log.v(TAG, lastSource);
                            sourceFlag = 1;
                        }
                    }

                    if (sourceFlag == 1) {
                        if (parser.getName().equals(XML_TAG_LEVEL)) {
                            cLevel = parser.getAttributeValue(null, XML_TAG_NAME);
                        } else if (parser.getName().equals(XML_TAG_SERVICE)) {
                            cService = parser.getAttributeValue(null, XML_TAG_NAME);

                            if (cLevel != null && cService != null) {
                                Log.d(TAG, "Service:Level " + cService + " " + cLevel);
                            } else {
                                Log.e(TAG, "parse Exception ");
                            }
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if (sourceFlag == 1) { // maybe be can be deleted
                        if (parser.getName().equals(XML_TAG_SERVICE)) {
                            cService = null;
                        } else if (parser.getName().equals(XML_TAG_LEVEL)) {
                            cLevel = null;
                        }

                        if (parser.getName().equals(XML_TAG_SOURCE)) {
                            cService = null;
                            cLevel = null;
                            sourceFlag = 0;
                            // todo
                            // break to END_DOCUMENT
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        //Document end

        Log.v(TAG, "Document end:");
    }





}
