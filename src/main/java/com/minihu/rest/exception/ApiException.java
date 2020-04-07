package com.minihu.rest.exception;

import lombok.Getter;

/**
 * @Author: zhfang
 * @Date: 2020/4/7 10:28
 */
@Getter
public class ApiException extends RuntimeException {

    private int code;
    private String msg;

    public ApiException() {
        this(1001, "接口错误");
    }

    public ApiException(String msg) {
        this(1001, msg);
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
