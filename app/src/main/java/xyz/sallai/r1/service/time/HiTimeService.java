package xyz.sallai.r1.service.time;


import android.util.Log;

import com.phicomm.speaker.device.custom.music.PhicommPlayer;
import com.unisound.ant.device.controlor.DefaultVolumeOperator;

import java.util.Calendar;
import xyz.sallai.r1.utils.GlobalInstance;

public class HiTimeService {
    private static final String TAG = "HiTimeService";
    public static  int STATE = 0;
    public static  int val = 3;
    public static  int start = 6;
    public static  int end = 22;
    static Thread thread;
    private static void hiTime(){
        Calendar calendar1 = Calendar.getInstance();
        speakTime(calendar1.get(Calendar.HOUR_OF_DAY));
        while (STATE != 0) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            // 获取当前分钟
            int minute = calendar.get(Calendar.MINUTE);
            //System.out.println("小时->"+hour+"分钟->"+minute);
            int sleepTime = 60 - minute;
            if (sleepTime == 60) {
                if (hour > end || hour < start) {
                    try {
                        Thread.sleep(1000 * 30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                speakTime(hour);
                continue;
            } else if (sleepTime > 2) sleepTime -= 2;
            else {
                sleepTime = 0;
            }

            try {
                Thread.sleep(1000 * 60 * sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {
                if (STATE == 0) break;
//                System.out.println("报时密切监控");
                calendar = Calendar.getInstance();
                minute = calendar.get(Calendar.MINUTE);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println("分钟"+minute);
                if (hour > end || hour < start) break;
                if (minute == 0) {
                    speakTime(hour);
                    break;
                }
            }
        }

        thread = null;
    }

    public static void startHiTime(){
        if(STATE==0){
            STATE = 1;
            if(thread==null){
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        hiTime();
                    }
                });
                thread.start();
            }
        }
    }

    private static void speakTime(int hour){
        PhicommPlayer playerManager = GlobalInstance.playerManager;
        DefaultVolumeOperator volumeOperator = GlobalInstance.volumeOperator;
        String hi = "";
        if(hour>0&&hour<9){
            hi = "早上好鸭~";
        }else if(hour>=9&&hour< 11){
            hi = "上午好鸭~";
        }else if(hour>=11&&hour<13){
            hi = "中午好鸭~";
        }else if(hour>=13&&hour<18){
            hi = "下午好鸭~";
        }else if(hour>=18&&hour<20){
            hi = "晚上好鸭~";
        }else if(hour>=20&&hour<23) {
            hi = "很晚啦~,记得乖乖睡觉";
        }
        if(hour!=12)  hour%=12;
        Log.d(TAG, "speakTime: " + hi+",当前时间"+hour+"点整");
        int currentVolume = volumeOperator.getCurrentVolume();
        volumeOperator.setVoiceVolume(3);
        if(playerManager.isPlaying()) {
            playerManager.pause();
            GlobalInstance.nativeANTEngine.playTTS(hi+",当前时间"+hour+"点整");
            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playerManager.resume();
        }else{
            GlobalInstance.nativeANTEngine.playTTS(hi+",当前时间"+hour+"点整");
        }
        volumeOperator.setVoiceVolume(currentVolume);
        try {
            Thread.sleep(1000*120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


