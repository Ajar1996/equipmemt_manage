package com.ajar.springbootshiro.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description:用户角色关系
 * @author: Ajar
 * @time: 2019/9/30 16:16
 */
@Data
@Entity
public class UserRole {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 用户Id
     */
    private Integer userId;


    /**
     * 角色Id
     */
    private Integer roleId;
}
