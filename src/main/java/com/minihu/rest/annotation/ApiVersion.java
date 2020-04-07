package com.minihu.rest.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 自定义版本号注解
 * @Author: zhfang
 * @Date: 2020/4/7 14:01
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

    /** 版本号,默认是V1 */
    int value() default 1;
}
