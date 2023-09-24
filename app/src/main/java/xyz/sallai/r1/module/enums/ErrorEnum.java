package xyz.sallai.r1.module.enums;

import lombok.Getter;

/**
 * Description: 错误状态枚举
 * Author: sallai
 * Date: 2023/9/23
 * Email: sallai@aliyun.com
 */
@Getter
public enum  ErrorEnum {

    UNKNOW("未知错误!",1000),
    NOT_FOUND("文件未找到异常",1001),
    INVALID_PARAM("参数非法",1002),
    UNAUTHROIZED("未授权异常",1003),
    SERVER_ERROR("服务端错误",1004);


    private final String msg;
    private final Integer code;

    private ErrorEnum(String name,Integer code) {
        this.msg = name;
        this.code = code;
    }
}
