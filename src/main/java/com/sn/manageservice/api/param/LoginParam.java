package com.sn.manageservice.api.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 向南
 * @date 2023/6/1 11:51
 * @description
 */
@Data
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String passWord;

    private boolean remember;
}
