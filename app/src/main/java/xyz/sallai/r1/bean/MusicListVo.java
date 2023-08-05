package xyz.sallai.r1.bean;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author: sallai
 * Date: 2023/8/2
 */

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright 2021 bejson.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicListVo {
    private int totalTime;
    private int count;
    private int errorCode;
    @Builder.Default
    private String dataType = "music";
    private int source;
    @Builder.Default
    private String dataSourceName="网易云音乐";
    @Builder.Default
    private String intent = "NAME";
    @Builder.Default
    private String dataSourceCode = "music";
    @Builder.Default
    private List<MusicInfoBean> musicinfo = new ArrayList<>();

}