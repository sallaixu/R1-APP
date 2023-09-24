package xyz.sallai.r1.utils;

import com.alibaba.fastjson.JSON;
import com.unisound.vui.util.internal.ObjectUtil;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.sallai.r1.module.enums.ErrorEnum;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/5/14
 */
@Data
@AllArgsConstructor
public class RR implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public RR setCode(int code) {
        this.code = code;
        return this;
    }

    public RR setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public RR setData(Object data) {
        this.data = data;
        return this;
    }

    public RR() {
        this.code = 200;
        this.msg = "success";
    }

    public RR(Object data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    public RR(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    // getter 和 setter 略

    public static RR ok() {
        return new RR();
    }

    public static  RR ok(Object data) {
        return new RR(data);
    }

    public static  RR error(int code, String msg) {
        return new RR(code, msg);
    }

    public static RR errorEnum(ErrorEnum errorEnum) {
        return RR.error(errorEnum.getCode(), errorEnum.getMsg());
    }

}