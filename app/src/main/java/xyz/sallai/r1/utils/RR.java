package xyz.sallai.r1.utils;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/5/14
 */
@Data
public class RR<T> {
    private int code;
    private String msg;
    private T data;

    public RR() {
        this.code = 200;
        this.msg = "success";
    }

    public RR(T data) {
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

    public static <T> RR<T> ok() {
        return new RR<>();
    }

    public static <T> RR<T> ok(T data) {
        return new RR<>(data);
    }

    public static <T> RR<T> error(int code, String msg) {
        return new RR<>(code, msg);
    }

}