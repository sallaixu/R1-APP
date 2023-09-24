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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlueToothInfoVo {
    //蓝牙名
    private String name;
    //开启状态
    private boolean state;
}
