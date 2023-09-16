package xyz.sallai.r1.controller;

import com.phicomm.speaker.device.custom.music.PhicommPlayer;
import com.phicomm.speaker.device.utils.PhicommUtils;
import com.unisound.vui.handler.session.music.outputevents.MusicOutputEvents;
import com.unisound.vui.handler.session.music.playitem.PlayItem;
import com.yanzhenjie.andserver.annotation.CrossOrigin;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RestController;

import xyz.sallai.r1.module.enums.MusicFunctionEnum;
import xyz.sallai.r1.module.enums.MusicServiceEnum;
import xyz.sallai.r1.utils.GlobalInstance;
import xyz.sallai.r1.utils.RR;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/9/8
 */
@RestController
@RequestMapping("/_api/music")
@CrossOrigin
public class MusicController {

    @GetMapping("/controller/{type}")
    public RR playNext(@PathVariable(name = "type") String type) {
        PhicommPlayer phicommPlayer = GlobalInstance.customApiManager.getmPhicommPlayer();
        if(null == phicommPlayer) return RR.error(201,"phicommPlayer is null");
        MusicFunctionEnum musicType = MusicFunctionEnum.valueOf(type);
        Object result = null;
        switch (musicType) {
            case RESUME:
                phicommPlayer.resume();break;
            case PLAY:
                phicommPlayer.play();break;
            case STOP:
                phicommPlayer.stop();break;
            case PLAY_NEXT:
                phicommPlayer.playNext();break;
            case PLAY_PRE:
                phicommPlayer.playPrev();break;
            case CURRENT_ITEM:
                result = phicommPlayer.getCurrentItem();break;
            case ITEM_LIST:
                result = phicommPlayer.getItemList();break;
            case PAUSE:
                phicommPlayer.pause();
            default:
        }
        return RR.ok().setData(result);
    }
}
