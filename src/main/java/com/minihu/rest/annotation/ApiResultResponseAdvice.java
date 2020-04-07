package com.minihu.rest.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minihu.rest.annotation.ApiLink;
import com.minihu.rest.annotation.NotCastApiResult;
import com.minihu.rest.dto.ApiResult;
import com.minihu.rest.exception.ApiException;
import com.minihu.rest.annotation.ApiProcessInterceptor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * @Author: zhfang
 * @Date: 2020/4/7 10:23
 */
@ControllerAdvice
public class ApiResultResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 1. 验证接口返回的结果类型是否为 {@link ApiResult}
     * 2. 增加注解支持，当存在{@link NotCastApiResult}注解时不进行类型包装
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        NotCastApiResult apiResultAnno = (NotCastApiResult) request.getAttribute(ApiProcessInterceptor.API_RESULT_ANNO);
        if (apiResultAnno != null){
            // 存在禁止转换注解时，直接返回false，不执行beforeBodyWrite
            return false;
        }

        // 验证接口返回的结果类型
        Type resultType = returnType.getGenericParameterType();
        // 若接口返回的结果类型正好为ApiResult，则直接返回false,不执行beforeBodyWrite
        return !resultType.equals(ApiResult.class);
    }

    /**
     * 重写返回体
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
        ApiLink apiLink = (ApiLink) req.getServletRequest().getAttribute(ApiProcessInterceptor.API_LINK_ANNO);
        ApiResult apiResult = new ApiResult<>().success();
        if (apiLink != null){
            ApiResult.Link link = apiResult.new Link();
            link.setTitle(apiLink.title());
            link.setMethod(apiLink.method());
            link.setUrl(apiLink.url());
            link.setType(apiLink.type());
            apiResult.setLink(link);
        }

        /* 如果返回的是String类型，将其包装为JSON格式再返回
           若直接包装String类型，将导致ClassCastException
           java.lang.ClassCastException: com.minihu.rest.dto.ApiResult cannot be cast to java.lang.String */
        if (returnType.getGenericParameterType().equals(String.class)) {
            try {
                return new ObjectMapper().writeValueAsString(apiResult.data(body));
            } catch (JsonProcessingException e) {
                throw new ApiException("系统异常，转换String返回类型至ApiResult失败。");
            }
        }
        // 其他类型直接包装为ApiResult统一报文直接返回
        return apiResult.data(body);
    }
}
