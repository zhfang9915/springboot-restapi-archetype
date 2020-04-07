package com.minihu.rest.controller;

import com.minihu.rest.annotation.ApiLink;
import com.minihu.rest.annotation.ApiVersion;
import com.minihu.rest.annotation.NotCastApiResult;
import com.minihu.rest.vo.Account;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: zhfang
 * @Date: 2020/4/7 9:49
 */
@RestController
public class TestController implements ITestController {
    @ApiVersion(1)
    @Override
    public String createAccount(@RequestBody @Valid Account account) {
        return "v1 create success";
    }
}
