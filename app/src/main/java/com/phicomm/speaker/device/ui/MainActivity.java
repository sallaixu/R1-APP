package com.phicomm.speaker.device.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.baidu.mobstat.StatService;
import com.phicomm.speaker.device.R;
import com.phicomm.speaker.device.R2;
import com.unisound.vui.util.LogMgr;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.sallai.r1.service.and.AndService;
import xyz.sallai.r1.service.scrcpy.MediaVideoEncoder;
import xyz.sallai.r1.service.socket.SocketService;
import xyz.sallai.r1.service.time.HiTimeService;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @SuppressLint("StaticFieldLeak")
    public static Context context;
//    private ProjectionUtils projectionUtils;
    private MediaVideoEncoder mVideoEncoder;
    private boolean enable;

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
        //上下文
        context = getApplication();
        //and web service
        AndService serverManager = new AndService(getApplicationContext());
        serverManager.startServer();
        SocketService.getInstance();
        //启动报时
        HiTimeService.startHiTime();
//        initClick();
    }

//    public void initClick() {
//        projectionUtils = new ProjectionUtils();
//        ScrcpyObservable.getInstance().register(this);
//        scrcpyBtn.setOnClickListener((listen)->{
//            if(!enable) {
//                projectionUtils.start(this);
//            }else {
//                if (projectionUtils != null) {
//                    projectionUtils.stop();
//                }
//            }
//            enable = !enable;
//
//        });
//    }

//    // 2.处理录屏申请
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 2. 处理录屏申请 -> 到onChange(MediaProjection mediaProjection)
//        projectionUtils.onActivityResult(requestCode, resultCode, data);
//    }


    public void onResume() {
        super.onResume();
        LogMgr.d(TAG, "onResume");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LogMgr.d(TAG, "--->>onDestroy");
        super.onDestroy();
//        if (projectionUtils != null) {
//            projectionUtils.stop();
//        }
//        if (mVideoEncoder != null){
//            mVideoEncoder.stop();
//        }
//        // 反注册录屏回调
//        ScrcpyObservable.getInstance().unregister(this);
    }

//    @Override
//    public void onChange(MediaProjection mediaProjection) {
//        if (mVideoEncoder != null){
//            mVideoEncoder.stop();
//        }
//        // 录屏H264编码
//        mVideoEncoder = new MediaVideoEncoder(mediaProjection);
//        // 开启录屏编码
//        mVideoEncoder.start();
//
//    }

}
