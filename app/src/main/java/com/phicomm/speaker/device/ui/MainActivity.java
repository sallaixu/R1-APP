package com.phicomm.speaker.device.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.baidu.mobstat.StatService;
import com.phicomm.speaker.device.R;
import com.phicomm.speaker.device.R2;
import com.tencent.bugly.crashreport.CrashReport;
import com.unisound.vui.util.LogMgr;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.sallai.r1.service.and.AndService;
import xyz.sallai.r1.service.socket.SocketService;
import xyz.sallai.r1.service.time.HiTimeService;

import static xyz.sallai.r1.utils.AppConstant.BUGLY_APP_ID;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @Bind({R2.id.scrcpyBtn})
    Button scrcpyBtn;

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
//        initService();
    }

    /**
     * 初始化服务
     */
    private void initService() {
        //and web service
        AndService serverManager = new AndService(getApplicationContext());
        serverManager.startServer();
        //socket服务
        SocketService.getInstance();
        //上下文
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
