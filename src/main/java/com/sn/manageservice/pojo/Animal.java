package com.sn.manageservice.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 向南
 * @date 2021/12/28 10:33
 * @description
 */
@Data
@Entity
@Table(name = "t_animal")
public class Animal {
    public Animal(){
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String animalName;

    @Column(nullable = true)
    private Integer isValid;

    @Column(nullable = true)
    private Date createDate;

    @Column(nullable = true)
    private Date updateDate;

}
