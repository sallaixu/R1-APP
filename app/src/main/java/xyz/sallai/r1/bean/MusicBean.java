package xyz.sallai.r1.bean;


import com.alibaba.fastjson.JSON;

public class MusicBean {
    static final String start = "{\"source\":\"nlu\",\"general\":{\"text\":\"请稍等\",\"type\":\"T\"},\"history\":\"cn.yunzhisheng.music\",\"data\":{\"result\":";
    static final String end = "},\"originIntent\":{\"nluSlotInfos\":[]},\"service\":\"cn.yunzhisheng.music\",\"taskName\":\"search\",\"code\":\"SEARCH_SONG\",\"text\":\"请稍后\",\"asr_recongize\":\"播放歌曲\",\"semantic\":{\"intent\":{\"song\":\"请稍等\",\"keyword\":\"请稍等\"}}}";

    /**
     * 获取因为完整响应
     * @param listVo
     * @return
     */
    public static String buildMusicJson(MusicListVo listVo) {
        String json = JSON.toJSONString(listVo);
        return start+json+end;
    }

}