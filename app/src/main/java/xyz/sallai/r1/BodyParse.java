//package xyz.sallai.r1;
//
//
//import android.util.Log;
//
//import com.alibaba.fastjson.JSON;
//import com.unisound.vui.util.ShellUtils;
//
//import xyz.sallai.r1.bean.MusicBean;
//import xyz.sallai.r1.bean.ReponseBody;
//import xyz.sallai.r1.service.music.WyyMusic;
//
//public class BodyParse {
//    private static final String MUSCI_PARSE = "音乐";
//    private static final String DEFAULT_MUSCI_PARSE = "播放音乐";
//    private static final String WY_MUSIC = "网易";
//    private static final String KW_MUSIC = "酷我";
//    private static final String LIGHT_NIGHT = "小灯";
//    private static final String LIGHT_RGB = "七彩灯";
//    private static final String COLLECTION = "收藏";
//    private static final String XMLY = "喜马拉雅";
//    private static final String WY_USER_LOVE = "网易用户";
//    private static final String SWITCH_PEOPLE = "切换人物";
//    private static final String REBOOT = "重启";
//
//    /*
//     * nlu语义分辨到不同功能
//     *
//     *
//     * */
//
//    public String Parse(String body) {
//        if (!body.contains("text")) return body;
//        Log.d("return text", body);
//        String res = "";
//        String text = JSON.parseObject(body).getString("text");
//        Log.d("解析text->", text);
//        if (text.contains(XMLY)) {
//            res = xmlyParse(text);
//        } else if (text.contains(MUSCI_PARSE)) {
//            res = MusicParse(text);
//        } else if (text.contains(LIGHT_NIGHT)) {
//            res = LightNight(text);
//        } else if (text.contains(LIGHT_RGB)) {
//            res = LightRGB(text);
//        } else if (text.contains(COLLECTION)) {
//            res = collectionParse(text);
//        } else if (text.contains(WY_USER_LOVE)) {
//            res = MusicParse(text);
//        } else if(text.contains(SWITCH_PEOPLE)) {
////            res = Speaker.switchPeople();
//        } else if (text.contains(REBOOT)) {
//            ShellUtils.execCommand("reboot",true);
//            res = ReponseBody.CHAT_OK;
//        } else {
//            res = body;
//        }
//        return res;
//    }
//
//
//    /*
//     * 音乐解析
//     * */
//    public String MusicParse(String text) {
//        text = text.replaceAll(MUSCI_PARSE, "");
//        if (text.contains(WY_MUSIC)) {
//
//            if (text.contains(WY_USER_LOVE)) {      //网易用户收藏
//                text = text.replaceAll(WY_USER_LOVE, "");
//                text = text.replaceAll("\\s+", "");
//                WyyMusic wyyMusic = new WyyMusic();
//                text = wyyMusic.getCollectionById(text);
//            } else {
//
//                text = text.replaceAll(WY_MUSIC, "");
//                String keyword = "飙升榜";
//                WyyMusic wyyMusic = new WyyMusic();
//                text = wyyMusic.getMusicByName(text.equals("") ? keyword : text);
//            }
//
//
//        } else if (text.contains(KW_MUSIC)) {
////            text = text.replaceAll(KW_MUSIC, "");
////            text = text.replaceAll(DEFAULT_MUSCI_PARSE, "");
////            text = new KwMusic().getMusicByName(text);
//
//
//        } else {
////            text = new KwMusic().getMusicByName(text);
//        }
//        text = MusicBean.getMusicJson(text);
//
//
//        Log.d("music_json", text);
//        return text;
//
//    }
//
//
//    /*
//     * Rgb七彩灯解析
//     *
//     * */
//    public String LightRGB(String text) {
//
//        if (text.contains("打开")) {
//
////            ServerManger.openLightService();
//            text = ReponseBody.CHAT_OPEN;
//
//        } else if (text.contains("关闭")) {
////            ServerManger.closeLightService();
//            text = ReponseBody.CHAT_ClOSE;
//        }
//
//
//        return text;
//
//    }
//
//    /*
//     * 小夜灯解析
//     *
//     * */
//
//    public String LightNight(String text) {
//
//        if (text.contains("打开")) {
//            System.out.println("开始执行打开夜灯");
////            LedController.openLightNight();
//            text = ReponseBody.CHAT_OPEN;
//
//        } else if (text.contains("关闭")) {
////            LedController.closeLightNight();
//            text = ReponseBody.CHAT_ClOSE;
//        }
//        return text;
//
//    }
//
//
//    /*
//     * 收藏解析
//     *
//     * */
//    public String collectionParse(String text) {
//        text = text.replaceAll(COLLECTION, "");
//        if (text.contains("添加")) {
////            MusicInfoBean collection = Collection.collection;
//
////            if (collection.getId() == "" || collection.getId() == null) {
////                return ReponseBody.speak("sorry,没有获取到音乐信息");
////            }
////            CollectionService collectionService = new CollectionService();
////            try {
////                collectionService.insertMusic(collection);
////            } catch (Exception e) {
////                return ReponseBody.speak("收藏歌曲名重复");
////            }
////
//
////            text = ReponseBody.speak("已收藏" + collection.getArtist() + "的" + collection.getTitle());
//
//
//        } else if (text.contains("查看")) {
//
//
//        } else if (text.contains("播放")) {
//
////            String s = new CollectionService().queryMusic();
////            text = MusicBean.getMusicJson(s);
//
//        }
//
//
//        return text;
//
//    }
//
//    /*
//     * 新闻联播
//     *
//     * */
//    public String xmlyParse(String text) {
//
////        text = text.replaceAll(XMLY, "");
////        int flag = text.indexOf("第");
////        int index = 1;
////        if (flag != -1) {
////            String substring = text.substring(flag + 1, text.length() - 1);
////            text = text.substring(0, flag);
////            char c = substring.charAt(0);
////            if (Character.isDigit(c)) {
////                index = Integer.parseInt(substring);
////            } else {
////                index = ChineseToInteger.transferChineseNumber2ArabNumber(substring);
////            }
////
////
////        }
////        XmlyAudio xmlyAudio = new XmlyAudio();
////        String res = xmlyAudio.getSearchAudio(text, index);
////        if (text.contains("电台")) {
////            return ReponseBody.MyBroadCast(res);
////        } else {
////            return MusicBean.getMusicJson(res);
////        }
//        return "";
//
//    }
//
//
//}
//
