package com.minihu.rest.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Api 接口统一响应报文格式
 * @Author: zhfang
 * @Date: 2020/4/7 10:11
 */
@Data
public class ApiResult<T> {

    private int code;

    private String message;

    private T data;

    private Link link;

    public ApiResult code(int code) {
        this.code = code;
        return this;
    }

    public ApiResult message(String message) {
        this.message = message;
        return this;
    }

    public ApiResult data(T data) {
        this.data = data;
        return this;
    }

    public ApiResult success() {
        this.code = HttpStatus.OK.value();
        return this;
    }

    public ApiResult success(T data) {
        this.code = HttpStatus.OK.value();
        this.data = data;
        return this;
    }


    public ApiResult fail() {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return this;
    }

    public ApiResult fail(String message) {
        this.message = message;
        return this.fail();
    }

    @Data
    public
    class Link{
        String title;
        RequestMethod method;
        String url;
        String type;
    }
}
