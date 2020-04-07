package com.minihu.rest.controller;

import com.minihu.rest.annotation.ApiLink;
import com.minihu.rest.annotation.ApiVersion;
import com.minihu.rest.vo.Account;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: zhfang
 * @Date: 2020/4/7 9:49
 */
@RequestMapping("/api/{version}")
public interface ITestController {

    @PostMapping("/user")
    @ApiLink(title = "查询用户信息", method = RequestMethod.GET, url = "/api/v1/user")
    String createAccount(@RequestBody @Valid Account account);



}
