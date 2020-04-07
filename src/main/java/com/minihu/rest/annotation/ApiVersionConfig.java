package com.minihu.rest.annotation;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.Arrays;

/**
 * MVC配置
 * @Author: zhfang
 * @Date: 2020/4/7 10:55
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiVersionConfig /*extends WebMvcConfigurationSupport*/implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionRequestHandlerMapping();
    }


//    /**
//     * 注册自定义的 RequestMappingHandlerMapping
//     * 不可通过WebMvcConfigurationSupport来注册自定义RequestMappingHandlerMapping，否则所有自定义interceptors全部失效
//     * 方法getRequestMappingHandlerMapping() 也不能添加@Bean注解，否则同样会使所有自定义interceptors全部失效
//     */
////    @Override
//////    @Bean
////    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
////        RequestMappingHandlerMapping handlerMapping = new ApiVersionRequestHandlerMapping();
////        return handlerMapping;
////    }
//    @Bean
//    @Primary
//    public RequestMappingHandlerMapping requestMappingHandlerMapping(
//            @Qualifier("mvcContentNegotiationManager") ContentNegotiationManager contentNegotiationManager,
//            @Qualifier("mvcConversionService") FormattingConversionService conversionService,
//            @Qualifier("mvcResourceUrlProvider") ResourceUrlProvider resourceUrlProvider) {
//        // Must be @Primary for MvcUriComponentsBuilder to work
//        RequestMappingHandlerMapping mapping = new ApiVersionRequestHandlerMapping();
//        mapping.setOrder(0);
//        mapping.setInterceptors(getInterceptors(conversionService, resourceUrlProvider));
//        return super.requestMappingHandlerMapping(contentNegotiationManager, conversionService,
//                resourceUrlProvider);
//    }


//    @Override
//    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
//        RequestMappingHandlerMapping handlerMapping = new ApiVersionRequestHandlerMapping();
//        return handlerMapping;
//    }
}
