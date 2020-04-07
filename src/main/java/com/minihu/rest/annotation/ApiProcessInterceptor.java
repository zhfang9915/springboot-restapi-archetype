/**
 * Copyright (C), 2018-2019, 张芳
 */
package com.minihu.rest.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 禁止将结果集转换为ApiResult 结果处理拦截器：请求处理前执行
 * @Author: zhfang
 * @Date: 2020/4/7 10:44
 **/
@Slf4j
public class ApiProcessInterceptor implements HandlerInterceptor {

    public static final String API_RESULT_ANNO = "NOT_API_RESULT_ANNO";
    public static final String API_LINK_ANNO = "API_LINK_ANNO";

    /**
     * 接口执行前，在request中存入注解标记
     * 在{@link ApiResultResponseAdvice}进行相应处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod){
            final HandlerMethod handlerMethod = (HandlerMethod)handler;
            final Class<?> bean = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            // 判断类或者方法上上面是否有ApiResult注解
            NotCastApiResult beanNotCastApiResult = AnnotationUtils.findAnnotation(bean, NotCastApiResult.class);
            NotCastApiResult methodNotCastApiResult = AnnotationUtils.findAnnotation(method, NotCastApiResult.class);
            if (beanNotCastApiResult != null || methodNotCastApiResult != null){
                // 有此标识 标识此请求返回体，需要包装为ApiResult格式， 在ResponseBodyAdvice接口进行判断
                NotCastApiResult result = beanNotCastApiResult == null ? methodNotCastApiResult : beanNotCastApiResult;
                request.setAttribute(API_RESULT_ANNO, result);
            }
            // 判断类或者方法上上面是否有ApiLink注解
            ApiLink beanApiLink = AnnotationUtils.findAnnotation(bean, ApiLink.class);
            ApiLink methodApiLink = AnnotationUtils.findAnnotation(method, ApiLink.class);
            if (beanApiLink != null || methodApiLink != null){
                ApiLink link = beanApiLink == null ? methodApiLink : beanApiLink;
                request.setAttribute(API_LINK_ANNO, link);
            }
        }
        return true;
    }
}
