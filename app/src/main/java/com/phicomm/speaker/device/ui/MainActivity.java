package com.phicomm.speaker.device.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.phicomm.speaker.device.R;
import com.unisound.vui.util.LogMgr;

import butterknife.ButterKnife;
import xyz.sallai.r1.service.and.AndService;
import xyz.sallai.r1.service.time.HiTimeService;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogMgr.d(TAG, "onCreate");
        setContentView(R.layout.layout_welcome);

        ButterKnife.bind(this);

        StatService.setDebugOn(false);
        StatService.setAppKey("efe5be0e5e");
        StatService.setAppChannel(this,"tdre",true);
        StatService.setOn(this, 1);
        StatService.setSessionTimeOut(30);
        StatService.enableDeviceMac(this, true);
        StatService.setForTv(this, true) ;
        StatService.autoTrace(this, true, false);
        //上下文
        context = getApplication();
        //and web service
        AndService serverManager = new AndService(getApplicationContext());
        serverManager.startServer();
        //启动报时
        HiTimeService.startHiTime();
    }

    public void onResume() {
        super.onResume();
        LogMgr.d(TAG, "onResume");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LogMgr.d(TAG, "--->>onDestroy");
        super.onDestroy();
    }
}
