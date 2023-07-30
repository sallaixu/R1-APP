package xyz.yhsj.kmusic;

import xyz.yhsj.kmusic.entity.MusicResp;
import xyz.yhsj.kmusic.entity.Song;
import xyz.yhsj.kmusic.impl.QQImpl;
import xyz.yhsj.kmusic.site.MusicSite;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JavaTest {

    public static void main(String args[]) throws UnsupportedEncodingException {
        /**
         * You need to write INSTANCE
         */
        QQImpl.INSTANCE.getSongTop();
        /**
         *Same as Java's static methods
         */
        MusicResp<List<Song>> tops = KMusic.search("b2y", 1, 10, MusicSite.KUWO);
        System.out.println(tops);
    }


}
