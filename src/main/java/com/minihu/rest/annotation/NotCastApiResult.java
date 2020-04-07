package com.minihu.rest.annotation;

import java.lang.annotation.*;

/**
 * 禁止将结果集转换为 {@link com.minihu.rest.dto.ApiResult}
 * @Author: zhfang
 * @Date: 2020/4/7 10:44
 */
@Retention(RetentionPolicy.RUNTIME)
// 标注在类上，整个类下的方法不做统一包装
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface NotCastApiResult {
}
