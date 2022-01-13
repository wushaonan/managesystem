package com.sn.manageservice.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author user
 * @auther 向南
 * @date 2021/12/28 11:10
 * @description
 */
@Data
@Entity
@Table(name = "t_relation")
public class Relation {
    public Relation(){
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer accountId;

    @Column(nullable = false)
    private Integer animalId;

    @Column(nullable = true)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = true)
    private Date createDate;

    @Column(nullable = true)
    private Date updateDate;
}
