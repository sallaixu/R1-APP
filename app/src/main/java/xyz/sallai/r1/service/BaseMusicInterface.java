package xyz.sallai.r1.service;

import xyz.sallai.r1.bean.MusicListVo;

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
