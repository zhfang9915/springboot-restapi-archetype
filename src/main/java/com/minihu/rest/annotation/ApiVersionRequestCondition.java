package com.minihu.rest.annotation;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @Author: zhfang
 * @Date: 2020/4/7 14:22
 */
public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {

    /** 正则匹配request中version*/
    private static final Pattern VERSION_PATTERN = Pattern.compile("/v(\\d+).*");
    /** 当前的版本号 */
    private int currentVersion;

    public ApiVersionRequestCondition(int version) {
        this.currentVersion = version;
    }

    @Override
    public ApiVersionRequestCondition combine(ApiVersionRequestCondition apiVersionRequestCondition) {
        // 方法优先原则，方法上的定义覆盖类上面的定义
        return new ApiVersionRequestCondition(apiVersionRequestCondition.getCurrentVersion());
    }

    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        Matcher m = VERSION_PATTERN.matcher(httpServletRequest.getRequestURI());
        if(m.find()){
            Integer version = Integer.valueOf(m.group(1));
            // 直接返回对应version的接口，不对超出版本号的接口进行降级最近匹配
            if (version == this.currentVersion) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionRequestCondition apiVersionRequestCondition, HttpServletRequest httpServletRequest) {
        // 优先匹配最新的版本号
        return apiVersionRequestCondition.getCurrentVersion() - this.currentVersion;
    }

    public int getCurrentVersion(){
        return currentVersion;
    }

}
