package xyz.sallai.r1.module.vo.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: sallai
 * Date: 2023/9/23
 * Email: sallai@aliyun.com
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VolumeInfoVo {
    //媒体音量
    private int mediaVol;
    //闹钟音量
    private int alarmVol;
    //媒体最大音量
    private int mediaMax;
    //媒体最小音量
    private int meidaMin = 0;
}
