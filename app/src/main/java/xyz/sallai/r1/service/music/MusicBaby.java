package xyz.sallai.r1.service.music;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import xyz.sallai.r1.bean.MusicInfoBean;
import xyz.sallai.r1.bean.MusicListVo;
import xyz.sallai.r1.service.BaseMusicInterface;
import xyz.sallai.r1.utils.MyThreadPool;
import xyz.sallai.r1.utils.okhttp.Http;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author: 歌曲宝网址解析
 * Date: 2023/8/23
 * url: https://www.gequbao.com
 */
public class MusicBaby extends AbstractMusic{
    public static final String searchUrl = "https://www.gequbao.com/s/";
    public static final String getPlayUrl = "https://www.gequbao.com";
    public static final ExecutorService pool = MyThreadPool.getThreadPool();
    private static final String TAG = "MusicBaby";

    @Override
    public MusicListVo searchMusic(String keyword, int size) {
        String url = searchUrl + keyword;
        String data = Http.sendHttp(url);
        List<MusicInfoBean> musicInfoBeans = parseHtml(data, size);
        return MusicListVo.builder().count(musicInfoBeans.size()).musicinfo(musicInfoBeans)
                .totalTime(1000 * 60 * 10).build();
    }

    private List<MusicInfoBean> parseHtml(String html,int size) {
        CountDownLatch countDownLatch = new CountDownLatch(size);
        Document parse = Jsoup.parse(html);
        Elements elementsByClass = parse.body().getElementsByClass("card mb-1");
//        Log.i(TAG,elementsByClass.toString());
        Element container = Objects.requireNonNull(elementsByClass.first()).getElementsByClass("card-text").first();
        assert container != null;
        Elements musicElements = container.children();
        int count = Math.min(size, (musicElements.size() - 2));
        List<MusicInfoBean> musicList = new ArrayList<>();
        for (int i = 1 ; i <= count; i++ ) {
            Element element = musicElements.get(i);
            Element aTag = element.getElementsByTag("a").first();
            assert aTag != null;
            String id = aTag.attr("href");
            System.out.println(id + " " + aTag.text());
            Element authorDiv = element.getElementsByClass("text-success col-4 col-content").first();
            String author = "无名氏";
            if(null != authorDiv) {
                author = authorDiv.text();
            }
            MusicInfoBean musicBeanVo = MusicInfoBean.builder().duration(1000 * 60 * 3).title(aTag.text())
                    .artist(author).id(id).build();
            getPlayUrl(id,musicBeanVo,countDownLatch);
            musicList.add(musicBeanVo);
        }
        waitRequestOkForTime(countDownLatch, musicList);
        return musicList;
    }


    private void getPlayUrl(final String urlPath, final MusicInfoBean musicInfoBean, final CountDownLatch countDownLatch) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "send url");
                String html = Http.sendHttp(getPlayUrl + urlPath);
                String flagStr = "$('#btn-download-mp3').attr('href', '";
                int start = html.indexOf(flagStr);
                int stop = html.indexOf("');", start);
                String playUrl = html.substring(start + flagStr.length(), stop);
                System.out.println(playUrl);
                musicInfoBean.setUrl(playUrl);
                countDownLatch.countDown();
            }
        });
    }

    public static void main(String[] args) {
        MusicBaby musicBaby = new MusicBaby();
        long start = System.currentTimeMillis();

        musicBaby.searchMusic("周杰伦",6);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}
