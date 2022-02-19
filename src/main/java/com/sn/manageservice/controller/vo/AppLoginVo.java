package com.sn.manageservice.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 向南
 * @date 2021/12/30 19:47
 * @description
 */
@Data
public class AppLoginVo {

    private String account;

    private String userName;

    private List<AppRelationVo> appRelationVos;
}
