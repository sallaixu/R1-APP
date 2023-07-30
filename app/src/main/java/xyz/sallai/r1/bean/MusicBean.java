package xyz.sallai.r1.bean;



public class MusicBean {
    static final String start = "{\"semantic\":{\"intent\":{\"artist\":\"sallai\",\"keyword\":\"sallai\"}},\"code\":\"SEARCH_ARTIST\",\"originIntent\":{\"nluSlotInfos\":[]},";
    static final String end = "\"history\":\"cn.yunzhisheng.music\",\"source\":\"nlu\",\"uniCarRet\":{\"result\":{},\"returnCode\":609,\"message\":\"aios-home.hivoice.cn\"},\"asr_recongize\":\"为你播放歌曲。\",\"rc\":0,\"returnCode\":0,\"audioUrl\":\"http://192.168.199.124:3333/trafficRouter/r/UFD4wK\",\"retTag\":\"nlu\",\"service\":\"cn.yunzhisheng.music\",\"nluProcessTime\":\"160\",\"taskName\":\"search\",\"text\":\"为您播放歌曲\",\"responseId\":\"e306fceb9aca480ca3b324525fef9230\"}";


    public static String getMusicJson(String musicData){
        return start+musicData+end;
    }

}