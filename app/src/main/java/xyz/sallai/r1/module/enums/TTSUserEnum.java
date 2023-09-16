package xyz.sallai.r1.module.enums;

import lombok.Data;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author: 发音人枚举类
 * Date: 2023/8/12
 */
public enum TTSUserEnum {

    CHILDREN("CHILDREN","小宝"),
    LZL("LZL","玲玲"),
    MALE("MALE","熊熊"),
    SWEET("SWEET","甜甜"),
    FEMALE("FEMALE","娜娜");
    public final String ename;
    public final String cname;
    TTSUserEnum(String ename,String cname) {
        this.ename = ename;
        this.cname = cname;
    }
}
