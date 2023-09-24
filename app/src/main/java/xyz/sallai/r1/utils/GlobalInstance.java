package xyz.sallai.r1.utils;

import com.phicomm.speaker.device.ExampleApp;
import com.phicomm.speaker.device.custom.api.CustomApiManager;
import com.phicomm.speaker.device.custom.music.PhicommPlayer;
import com.unisound.ant.device.controlor.DefaultVolumeOperator;
import com.unisound.vui.engine.ANTEngine;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/7/30
 */
public class GlobalInstance {
    public static ANTEngine nativeANTEngine;  // 本地ant引擎
    public static CustomApiManager customApiManager; // 自定义api
    public static PhicommPlayer playerManager; // 播放管理
    public static DefaultVolumeOperator volumeOperator = DefaultVolumeOperator.getInstance(ExampleApp.context);// 获取音量管理器
}
