package xyz.sallai.r1.bean.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemInfoBean {
    private MemeryVo memeryInfo;
    private DiskVo diskInfo;
}
