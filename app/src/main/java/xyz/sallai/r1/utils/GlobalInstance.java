package xyz.sallai.r1.utils;

import com.phicomm.speaker.device.ExampleApp;
import com.phicomm.speaker.device.custom.api.CustomApiManager;
import com.phicomm.speaker.device.custom.music.PhicommMusicController;
import com.phicomm.speaker.device.custom.music.PhicommPlayer;
import com.phicomm.speaker.device.ui.MainActivity;
import com.unisound.ant.device.controlor.DefaultVolumeOperator;
import com.unisound.vui.engine.ANTEngine;
import com.unisound.vui.engine.NativeANTEngine;

import lombok.Builder;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/7/30
 */
public class GlobalInstance {
    public static ANTEngine nativeANTEngine;  // 本地ant引擎
    public static CustomApiManager customApiManager;
    public static PhicommPlayer playerManager;
    public static DefaultVolumeOperator volumeOperator = DefaultVolumeOperator.getInstance(ExampleApp.context);
}
