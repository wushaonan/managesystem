package com.sn.manageservice.controller.vo;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * @author 向南
 * @date 2021/12/29 20:27
 * @description
 */
@Data
public class RelationVo {

    private Integer id;

    private Integer accountId;

    private String account;

    private Integer animalId;

    private String animalName;

    private String startDateStr;

    private String endDateStr;

    private String createDateStr;

    private String updateDateStr;
}
