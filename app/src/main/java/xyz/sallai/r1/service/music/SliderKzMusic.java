package xyz.sallai.r1.service.music;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.unisound.vui.handler.session.music.entity.Music;
import com.unisound.vui.handler.session.music.syncloud.SyncMusicListBean;

import org.apache.commons.lang3.StringUtils;

import xyz.sallai.r1.bean.MusicInfoBean;
import xyz.sallai.r1.bean.MusicListVo;
import xyz.sallai.r1.service.BaseMusicInterface;
import xyz.sallai.r1.utils.okhttp.Http;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/9/6
 */
public class SliderKzMusic implements BaseMusicInterface {
    private final String searchUrl = "http://slider.kz/vk_auth.php?q=";
    private final String baseHost = "http://slider.kz/";

    @Override
    public MusicListVo searchMusic(String keyword, int size) {
        String data = Http.sendHttp(searchUrl + keyword);
        JSONObject dataJson = JSON.parseObject(data);
        JSONObject audios = dataJson.getJSONObject("audios");
        JSONArray musicArray = audios.getJSONArray("");
        int count = Math.min(size, (musicArray.size() - 1));
        MusicListVo musicVo = MusicListVo.builder().totalTime(10000).count(count).build();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObject = musicArray.getJSONObject(i);
            String url = jsonObject.getString("url");
            url = StringUtils.contains(url,"http") ? url : baseHost + url;
            MusicInfoBean info = MusicInfoBean.builder().id(jsonObject.getString("id"))
                    .artist(jsonObject.getString("tit_art"))
                    .duration(jsonObject.getInteger("duration"))
                    .url(url).build();
            musicVo.getMusicinfo().add(info);
        }
        return musicVo;
    }

    public static void main(String[] args) {
        MusicListVo musicVo = new SliderKzMusic().searchMusic("泪蛋蛋掉在酒杯杯里", 20);
        for (MusicInfoBean musicInfoBean : musicVo.getMusicinfo()) {
            System.out.println(musicInfoBean);
        }
    }
}
