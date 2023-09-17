package xyz.sallai.r1.service;

import android.graphics.Bitmap;
import android.util.Log;

import com.phicomm.speaker.device.ExampleApp;
import com.phicomm.speaker.device.ui.MainActivity;

import org.java_websocket.WebSocket;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import xyz.sallai.r1.service.scrcpy.ScreenShot;
import xyz.sallai.r1.service.socket.SocketService;
import xyz.sallai.r1.utils.codec.BitMapUtil;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/19
 */
public class SystemService {

    private static final String TAG = "SystemService";
    private static Timer scrcpyTimer = new Timer(); // 创建定时器对象
    private static boolean scrcpyStatus = false;

    /**
     * 开启屏幕截取推送
     */
    public static void startScreenSend(long freq,int quality) {
        //检测是否存在，不存在创建，双重检验锁
        if(null == scrcpyTimer) {
            synchronized (SystemService.class) {
                if(null == scrcpyTimer) {
                    scrcpyTimer = new Timer();
                }
            }
        }
        if(scrcpyStatus) {
            Log.i(TAG, "startScreenSend: started");
            return;
        }
        //定时器执行
        scrcpyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrcpyStatus = true;
                Bitmap screenshot = ScreenShot.screenshot(ExampleApp.context);
                if(null != screenshot) {
                    // 假设bitmapData为位图的字节数组
                    byte[] bytes = BitMapUtil.convertBitmapToByteArray(screenshot,quality);
                    //检测是否存在客户端
                    Collection<WebSocket> connections = SocketService.getInstance().connections();
                    if (connections.isEmpty()) {
                        Log.i(TAG, "run: client is empty stop timer");
                        scrcpyTimer.cancel();
                        scrcpyStatus = false;
                        scrcpyTimer = null;
                    }
                    SocketService.getInstance().broadcast(bytes);
                }else {
                    Log.e(TAG, "run: screenshot is null");
                }
            }
        }, 0, freq);
        Log.i(TAG,"scrcpy start ");
    }

    /**
     * 停止屏幕截取推送
     */
    public static void stopScreenSend() {
        //检测是否存在，不存在创建，双重检验锁
        if(null != scrcpyTimer) {
            scrcpyTimer.cancel();
        }
        scrcpyStatus = false;
        scrcpyTimer = null;
        Log.d(TAG, "stopScreenSend: stopped");

    }
}
