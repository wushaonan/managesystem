package com.sn.manageservice.controller.vo;

import lombok.Data;

/**
 * @author 向南
 * @date 2021/12/29 19:40
 * @description
 */
@Data
public class AccountVo {

    private Integer id;

    private String account;

    private String userName;

    private String password;

    private String company;

    private String mobile;

    private String email;

    private String createDateStr;

    private String updateDateStr;
}
