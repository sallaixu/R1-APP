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
public class WifiInfoVo {
    private String ssid;
    private String ipAddr;
    private String macAddr;
    private int linkSpeed;
}
