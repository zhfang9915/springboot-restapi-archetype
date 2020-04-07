package com.minihu.rest.vo;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 用户账户信息
 * @Author: zhfang
 * @Date: 2020/4/7 9:50
 */
@Data
public class Account {

    private Long id;

    @NotNull(message = "用户名不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @NotNull(message = "年龄不能为空")
    @Max(value = 150, message = "年龄不能超过150岁")
    @Min(value = 16, message = "年龄不能小于16岁")
    private Integer age;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
}
