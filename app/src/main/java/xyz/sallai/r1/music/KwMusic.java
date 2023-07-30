//package xyz.sallai.r1.music;
//
//import android.util.Log;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.phicomm.speaker.utils.MyThreadPool;
//import com.phicomm.speaker.web.bean.MusicInfoBean;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class KwMusic {
//
//    ExecutorService pool = MyThreadPool.getThreadPool();
//    //http://search.kuwo.cn/r.s?client=kt&pn=0&rn=10&uid=1794081532&ver=kwplay_cr_5.0.0.0&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerge=1&mobi=1&vipver=5.0.0.3&all=%E8%94%A1%E5%81%A5%E9%9B%85
//    private static final String SEARCH_URL = "http://search.kuwo.cn/r.s?client=kt&pn=0&rn=10&uid=1794081532&ver=kwplay_cr_5.0.0.0&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerge=1&mobi=1&all=";
//    public static final String PLAY_URL = "http://antiserver.kuwo.cn/anti.s?type=convert_url&format=mp3&response=url&rid=";
//
//
//
//
//
//    public String getMusicByName(String text){
//        String url = SEARCH_URL+text;
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
//                .build();
//
//        try (Response response= client.newCall(request).execute()) {
//
//            return parseJson(response.body().string());  //解析成小讯格式
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.d("music","歌曲搜索失败");
//        return "歌曲搜索失败";
//
//    }
//
//    String parseJson(String musicInfo){
//
//        AtomicInteger parse_num = new AtomicInteger(0);
//        JSONArray jsonArray = JSON.parseObject(musicInfo).getJSONArray("abslist");
//        ArrayList<MusicInfoBean> musicList = new ArrayList();
//        for(int i=0;i<jsonArray.size();i++){
//
//            int finalI = i;
//            musicList.add(finalI,null);
//
//            Log.d("size",""+musicList.size());
//            pool.execute(()->{
//                MusicInfoBean m = new MusicInfoBean();
//                JSONObject res =  jsonArray.getJSONObject(finalI);
//
//                m.setId(res.getString("MUSICRID"));
//                m.setArtist(res.getString("ARTIST"));
//                m.setAlbum(res.getString("ALBUM"));
//                m.setTitle(res.getString("NAME"));
//                m.setDuration(Integer.parseInt(res.getString("DURATION")));
//
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(PLAY_URL+res.getString("MUSICRID"))
//                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
//                        .build();
//
//                try (Response response= client.newCall(request).execute()) {
////                    Log.d("mp3url",response.body().string());
//                    String string = response.body().string();
//                    m.setUrl(string);
//
//                    m.setId(JSON.toJSONString(m));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//
//                parse_num.getAndIncrement();
//                musicList.set(finalI,m);
//                Log.d("Thread","完成解析"+ Thread.currentThread().getName());
//            });
//
//
//        }
//
//        while(parse_num.get()<10){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Result result = new Result();
//        result.setCount(musicList.size());
//        result.setErrorCode(0);
//        result.setMusicinfo(musicList);
//        result.setTotalTime(9);
//        String s = "\"data\":{\"result\":"+ JSON.toJSONString(result)+"},";
//        return s;
//    }
//
//}
//
//
//
//
//
