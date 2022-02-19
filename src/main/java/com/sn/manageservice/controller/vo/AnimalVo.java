package com.sn.manageservice.controller.vo;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author 向南
 * @date 2021/12/29 19:40
 * @description
 */
@Data
public class AnimalVo {

    private Integer id;

    private String animalName;

    private String createDate;

    private String updateDate;
}
