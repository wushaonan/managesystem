package com.sn.manageservice.controller.param;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author 向南
 * @date 2021/12/29 17:08
 * @description
 */
@Data
public class AccountParam {

    private Integer id;

    private String account;

    private String userName;

    private String password;

    private String company;

    private String mobile;

    private String email;
}
