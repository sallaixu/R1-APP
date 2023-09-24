package xyz.sallai.r1.service;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import xyz.sallai.r1.bean.MusicInfoBean;
import xyz.sallai.r1.bean.MusicListVo;

import static xyz.sallai.r1.utils.AppConstant.RQUEST_URL_TIMEOUT;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/2
 */
public interface BaseMusicInterface {

    //搜索音乐接口
    MusicListVo searchMusic(String keyword, int size);

}
