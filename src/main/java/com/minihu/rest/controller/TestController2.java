package com.minihu.rest.controller;

import com.minihu.rest.annotation.ApiVersion;
import com.minihu.rest.vo.Account;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: zhfang
 * @Date: 2020/4/7 9:49
 */
@RestController
public class TestController2 implements ITestController {

    @ApiVersion(2)
    @Override
    public String createAccount(@RequestBody @Valid Account account) {
        return "v2 create success";
    }



}
