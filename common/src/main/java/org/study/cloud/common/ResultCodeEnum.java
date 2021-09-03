package org.study.cloud.common;

/**
 * Created by Administrator on 2019\8\17 0017.
 */
public enum ResultCodeEnum {
    /**/
    SUCCESS(0, "成功"),

    FAILURE(1, "操作失败"),

    HYSTRIX(10, "响应失败"),

    ;



    private Integer code;
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
