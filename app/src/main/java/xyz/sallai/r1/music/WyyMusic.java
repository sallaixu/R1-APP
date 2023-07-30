package xyz.sallai.r1.music;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.sallai.r1.bean.MusicInfoBean;
import xyz.sallai.r1.utils.okhttp.GlobalInstance;
import xyz.sallai.r1.utils.okhttp.Http;

public class WyyMusic {

//    http://music.163.com/song/media/outer/url?id=1486631924
    private static final String SEARCH_URL = "http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&offset=0&total=true&limit=30";
//    private static final String PLAY_URL = "http://api.sunweihu.com/api/wyy/api.php?id=";
    private static final String PLAY_URL = "http://music.163.com/song/media/outer/url?id=";
    private static final String SONGS_TABLE = "https://music.163.com/api/playlist/detail?id=";
//                                               https://music.163.com/api/playlist/detail?id=2412967068         2412967068
    private static final String USER_SONGS  = "http://music.163.com/api/user/playlist/?offset=0&limit=1001&uid="; // 1593331566获取用户收藏及歌单信息//2412967068 获取歌单详情
/*    offset：偏移量（翻页），offset需要是limit的倍数


    type：搜索的类型
            type=1           单曲
            type=10         专辑
            type=100        歌手
            type=1000      歌单
            type=1002      用户
            type=1004      MV
            type=1006      歌词
            type=1009      主播电台*/


    public String getMusicByName(String text){
//        解析歌单 解析单曲
        if(text.contains("歌单")){
            text = text.replaceAll("歌单", "");
            String url = SEARCH_URL+"&type=1000"+"&s="+text;
            String s = new Http().sendHttp(url);

            String id = JSON.parseObject(s).getJSONObject("result").getJSONArray("playlists").getJSONObject(0).getString("id");
            url = SONGS_TABLE+id;
            s = new Http().sendHttp(url);
            JSONArray jsonArray = JSON.parseObject(s).getJSONObject("result").getJSONArray("tracks");
            return parseJson(jsonArray);  //解析成小讯格式

        }else{
            String url = SEARCH_URL+"&type=1"+"&s="+text;
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                    .build();

            try (Response response= client.newCall(request).execute()) {

                JSONArray jsonArray = JSON.parseObject(response.body().string()).getJSONObject("result").getJSONArray("songs");
                return parseJson(jsonArray);  //解析成小讯格式

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("music","歌曲搜索失败");
            return "歌曲搜索失败";
        }


    }

    String parseJson(JSONArray jsonArray){



        ArrayList<MusicInfoBean> musicList = new ArrayList();
        for(int i=0;i<jsonArray.size();i++){
            MusicInfoBean m = new MusicInfoBean();
            JSONObject res =  jsonArray.getJSONObject(i);

            m.setArtist(res.getJSONArray("artists").getJSONObject(0).getString("name"));
            m.setAlbum(res.getString("name"));
            m.setTitle(res.getString("name"));
            m.setUrl(PLAY_URL+res.getString("id"));
            m.setDuration(res.getInteger("duration"));
            m.setId(res.getString("id"));
            m.setId(JSON.toJSONString(m));
            musicList.add(m);

        }
        Result result = new Result();
        result.setCount(musicList.size());
        result.setErrorCode(0);
        result.setMusicinfo(musicList);
        result.setTotalTime(9);
        System.out.println("解析-》"+musicList.size());
        String s = "\"data\":{\"result\":"+ JSON.toJSONString(result)+"},";

        return s;
    }



    public String getCollectionById(String id){
        ArrayList<MusicInfoBean> musicList = new ArrayList();
        String s = new Http().sendHttp(USER_SONGS + id);
        System.out.println(s);
        JSONObject playlist = JSONObject.parseObject(s).getJSONArray("playlist").getJSONObject(0);
        String id1 = playlist.getString("id");
        s = new Http().sendHttp(SONGS_TABLE + id1);
        System.out.println(s);
        JSONArray tracks = JSONObject.parseObject(s).getJSONObject("result").getJSONArray("tracks");
        JSONObject creator = JSONObject.parseObject(s).getJSONObject("result").getJSONObject("creator");
        GlobalInstance.nativeANTEngine.playTTS("请欣赏用户 "+creator.getString("nickname")+"的"+tracks.size()+"首收藏");
//        Speaker.yzsSpeaker("请欣赏用户 "+creator.getString("nickname")+"的"+tracks.size()+"首收藏");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < tracks.size() ; i++) {

            MusicInfoBean m = new MusicInfoBean();
            JSONObject res =  tracks.getJSONObject(i);

            m.setArtist(res.getJSONArray("artists").getJSONObject(0).getString("name"));
            m.setAlbum(res.getString("name"));
            m.setTitle(res.getString("name"));
            m.setUrl(PLAY_URL+res.getString("id"));
            m.setDuration(res.getInteger("duration"));
            m.setId(res.getString("id"));
            m.setId(JSON.toJSONString(m));
            musicList.add(m);

        }
        Result result = new Result();
        result.setCount(musicList.size());
        result.setErrorCode(0);
        result.setMusicinfo(musicList);
        result.setTotalTime(9);
        s = "\"data\":{\"result\":"+ JSON.toJSONString(result)+"},";

        return s;



    }


}




/**
 * Copyright 2021 bejson.com
 */


/**
 * Auto-generated: 2021-02-08 23:23:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
 class Result {

    private int totalTime;
    private int count;
    private int errorCode;
    private int source;
    private String dataSourceName="网易云音乐";

    private List<MusicInfoBean> musicinfo;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
    public int getTotalTime() {
        return totalTime;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setMusicinfo(List<MusicInfoBean> musicinfo) {
        this.musicinfo = musicinfo;
    }
    public List<MusicInfoBean> getMusicinfo() {
        return musicinfo;
    }



}
