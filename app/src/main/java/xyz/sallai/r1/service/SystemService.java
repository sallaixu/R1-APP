package xyz.sallai.r1.service;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.util.Log;

import com.phicomm.speaker.device.ExampleApp;
import com.unisound.ant.device.controlor.DefaultVolumeOperator;
import com.unisound.vui.util.DeviceTool;

import org.java_websocket.WebSocket;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import xyz.sallai.r1.bean.system.DiskVo;
import xyz.sallai.r1.bean.system.MemoryVo;
import xyz.sallai.r1.module.vo.sys.BaseSysInfoVo;
import xyz.sallai.r1.module.vo.sys.BlueToothInfoVo;
import xyz.sallai.r1.module.vo.sys.VolumeInfoVo;
import xyz.sallai.r1.module.vo.sys.WifiInfoVo;
import xyz.sallai.r1.service.scrcpy.ScreenShot;
import xyz.sallai.r1.service.socket.SocketService;
import xyz.sallai.r1.utils.codec.BitMapUtil;
import xyz.sallai.r1.utils.system.SystemInfoUtils;

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

    /**
     * 设置蓝牙名字
     * @param name
     */
    public void modifyBlueToothName(String name) {

        // 获取BluetoothAdapter实例
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 检查设备是否支持蓝牙
        if (bluetoothAdapter == null) {
            Log.e(TAG, "设备不支持蓝牙");
        } else {
            // 获取当前蓝牙设备名称
            String currentName = bluetoothAdapter.getName();

            // 设置新的蓝牙设备名称
            bluetoothAdapter.setName(name);

            // 确认新名称是否已成功设置
            String updatedName = bluetoothAdapter.getName();

            if (updatedName.equals(name)) {
                Log.d(TAG,"蓝牙名字修改 " + currentName + "->" + name);
            }else{
                Log.e(TAG,"蓝牙名字修改失败 " + currentName + "->" + name);
            }
        }
    }

    /**
     * 获取系统基础运行信息，内存、存储、ip
     */
    @SuppressLint("HardwareIds")
    public synchronized BaseSysInfoVo getSystemBaseInfo() {
        DiskVo diskInfo = SystemInfoUtils.getDiskInfo();
        MemoryVo memeryInfo = SystemInfoUtils.getMemeryInfo();
        String loadAvg = SystemInfoUtils.getLoadAvg();
        DefaultVolumeOperator volume = DefaultVolumeOperator.getInstance(ExampleApp.context);
        int currentVolume = volume.getCurrentVolume();
        int maxVolume = volume.getMaxVolume();

        VolumeInfoVo volumeInfoVo = VolumeInfoVo.builder().mediaVol(currentVolume)
                .mediaMax(maxVolume).build();
        //wifi信息
        WifiInfo wifiInfo = DeviceTool.getWifiInfo(ExampleApp.context);
        WifiInfoVo wifiInfoVo = new WifiInfoVo();
        if(null != wifiInfo) {
            wifiInfoVo = WifiInfoVo.builder().macAddr(wifiInfo.getMacAddress())
                    .linkSpeed(wifiInfo.getLinkSpeed())
                    .ssid(wifiInfo.getSSID()).ipAddr(intToIp(wifiInfo.getIpAddress())).build();
        }
        //蓝牙信息
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BlueToothInfoVo bluetoothVo = BlueToothInfoVo.builder().name(bluetoothAdapter.getName())
                .state(bluetoothAdapter.isEnabled()).build();
        return BaseSysInfoVo.builder().disk(diskInfo).memory(memeryInfo).loadAvg(loadAvg)
                .blueTooth(bluetoothVo)
                .volume(volumeInfoVo).wifi(wifiInfoVo).build();
    }

    /**
     * 转化ip地址到字符串
     * @param ipAddress
     * @return
     */
    public String intToIp(int ipAddress) {
        return ((ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                ((ipAddress >> 24) & 0xFF));
    }

}
