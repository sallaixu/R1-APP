package xyz.sallai.r1.bean.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemeryVo {
    private String total;
    private String available;
}
