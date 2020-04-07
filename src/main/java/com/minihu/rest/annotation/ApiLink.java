package com.minihu.rest.annotation;


import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * API 规范：下一个接口调用地址信息
 * @Author: zhfang
 * @Date: 2020/4/7 13:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ApiLink {

    /**
     * 表示 API 的标题
     */
    String title();

    /**
     * 请求方式
     */
    RequestMethod method() default RequestMethod.GET;

    /**
     * URI
     */
    String url();

    /**
     * 表示返回类型
     */
    String type() default "JSON";
}
