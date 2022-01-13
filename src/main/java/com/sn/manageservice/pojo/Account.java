package com.sn.manageservice.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @auther 向南
 * @date 2021/12/28 10:33
 * @description
 */
@Data
@Entity
@Table(name = "t_account")
public class Account {
    public Account(){
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String company;

    @Column(nullable = true)
    private String mobile;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private Integer isValid;

    @Column(nullable = true)
    private Date createDate;

    @Column(nullable = true)
    private Date updateDate;
}
