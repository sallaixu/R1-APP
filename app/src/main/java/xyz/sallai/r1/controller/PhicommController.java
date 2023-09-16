package xyz.sallai.r1.controller;

import android.util.Log;

import com.phicomm.speaker.device.custom.api.CustomApiManager;
import com.phicomm.speaker.device.ui.MainActivity;
import com.phicomm.speaker.device.utils.PhicommUtils;
import com.phicomm.speaker.device.utils.TTSUtils;
import com.unisound.vui.engine.ANTEngine;
import com.yanzhenjie.andserver.annotation.CrossOrigin;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RestController;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import xyz.sallai.r1.bean.system.DiskVo;
import xyz.sallai.r1.bean.system.MemeryVo;
import xyz.sallai.r1.bean.system.SystemInfoBean;
import xyz.sallai.r1.module.enums.MusicServiceEnum;
import xyz.sallai.r1.module.enums.TTSUserEnum;
import xyz.sallai.r1.utils.GlobalInstance;
import xyz.sallai.r1.utils.RR;
import xyz.sallai.r1.utils.okhttp.SystemInfoUtils;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author: sallai
 * Date: 2023/5/13
 */
@RestController
@RequestMapping("/_api/phicomm")
@CrossOrigin
public class PhicommController {

    private static final String TAG = "PhicommController";

    @GetMapping("/change/wakeup/{keyword}")
    public RR changeWakeUpWord(@PathVariable(name = "keyword") String keyword) {
        GlobalInstance.customApiManager.handleModifyWakeUpWord(keyword);
//        List<String> newKeyWord = Arrays.asList(keyword);
//        GlobalInstance.nativeANTEngine.updateWakeupWord(newKeyWord);
        GlobalInstance.nativeANTEngine.playTTS("你可以叫我" + keyword + "啦！");
        return RR.ok();
    }

    @GetMapping("/change/tts/{type}")
    public RR changeTTSType(@PathVariable(name = "type") String type) {

        TTSUserEnum ttsUserEnum = TTSUserEnum.valueOf(type);
        List<TTSUserEnum> ttsUserEnums = Arrays.asList(TTSUserEnum.values());
        List<String> typeList = new ArrayList<>();
        for (TTSUserEnum userEnum : ttsUserEnums) {
            typeList.add(userEnum.ename);
        }
//        String join = StringUtils.join(typeList, ",");
//        if(null != ttsUserEnum) return RR.error(201,"发音人不存在！请在以下选择:"+join);
        ANTEngine nativeANTEngine = GlobalInstance.nativeANTEngine;
        TTSUtils.switchSpeaker(MainActivity.context,nativeANTEngine,ttsUserEnum.ename);
        GlobalInstance.nativeANTEngine.playTTS("嗨哎，我是"+ttsUserEnum.cname +"见到你很高兴");
        return RR.ok();
    }


    @GetMapping("/music/source/{type}")
    public RR changeMusicSource(@PathVariable(name = "type") String type) {
        MusicServiceEnum music = MusicServiceEnum.valueOf(type);
        PhicommUtils.musicServiceEnum = music;
        GlobalInstance.nativeANTEngine.playTTS("设置音乐源为：" + music.getName());
        return RR.ok();
    }



}
