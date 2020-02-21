package com.ajar.springbootshiro.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description:角色资源关系
 * @author: Ajar
 * @time: 2019/9/30 16:17
 */
@Data
@Entity
public class RoleResource {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 资源id
     */
    private Integer resourceId;
}
