package com.sn.manageservice.controller.param;

import lombok.Data;

/**
 * @author 向南
 * @date 2021/12/29 17:08
 * @description
 */
@Data
public class RelationParam {

    private Integer id;

    private Integer accountId;

    private String account;

    private Integer animalId;

    private String startDate;

    private String endDate;

    private Integer days;


}
