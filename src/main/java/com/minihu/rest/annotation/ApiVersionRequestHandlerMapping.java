package com.minihu.rest.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhfang
 * @Date: 2020/4/7 14:37
 */
@Slf4j
public class ApiVersionRequestHandlerMapping extends RequestMappingHandlerMapping {

    private boolean useSuffixPatternMatch = true;

    private boolean useTrailingSlashMatch = true;

    private final List<String> fileExtensions = new ArrayList<String>();

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return buildCondition(AnnotationUtils.findAnnotation(handlerType, ApiVersion.class));
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return buildCondition(AnnotationUtils.findAnnotation(method, ApiVersion.class));
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = this.createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = this.createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
            info =  createRequestMappingInfo(info);
        }
        return info ;
    }

    @Nullable
    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = element instanceof Class ? this.getCustomTypeCondition((Class)element) : this.getCustomMethodCondition((Method)element);
        return requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null;
    }


    protected RequestMappingInfo createRequestMappingInfo(RequestMappingInfo info) {
        ApiVersionRequestCondition apiVesrsionCondition = null;
        if (info == null) {
            return null;
        }
        Object[] patterns = info.getPatternsCondition().getPatterns().toArray();
        apiVesrsionCondition = (ApiVersionRequestCondition) info.getCustomCondition();
        String[] url = null;
        if (apiVesrsionCondition != null && patterns.length > 0) {
            url = new String[]{patterns[0].toString().replace("{version}", "v" + apiVesrsionCondition.getCurrentVersion())};
        }
        if(url==null) {
            url=new String[patterns.length];
            for (int i=0;i< patterns.length;i++) {
                url[i]=patterns[i].toString();
            }
        }
        return new RequestMappingInfo(info, apiVesrsionCondition);
    }

    private ApiVersionRequestCondition buildCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionRequestCondition(apiVersion.value());
    }
}
