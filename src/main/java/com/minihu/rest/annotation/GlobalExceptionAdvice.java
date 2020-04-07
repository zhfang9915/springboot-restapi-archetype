package com.minihu.rest.annotation;

import com.minihu.rest.dto.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @Author: zhfang
 * @Date: 2020/4/7 10:07
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * hibernate-validation 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ApiResult<>().code(HttpStatus.BAD_REQUEST.value()).message(objectError.getDefaultMessage());
    }
}
