package xyz.sallai.r1.module.vo.sys;

import android.net.wifi.WifiInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.sallai.r1.bean.system.DiskVo;
import xyz.sallai.r1.bean.system.MemoryVo;

/**
 * Description:
 * Author: sallai
 * Date: 2023/9/23
 * Email: sallai@aliyun.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseSysInfoVo {
    //启动时间
    private String bootTime;
    private MemoryVo memory;
    private DiskVo disk;
    private WifiInfoVo wifi;
    private VolumeInfoVo volume;
    private BlueToothInfoVo blueTooth;
    private String loadAvg;
    private String blueToothName;

}
