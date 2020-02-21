package com.ajar.springbootshiro.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @description:资源
 * @author: Ajar
 * @time: 2019/9/30 12:17
 */
@Entity
@Data
@Table(name = "resource")
public class Resource {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 资源父id
     */
    private Integer parentId;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 权限标识符
     */
    private String perms;

    /**
     * 类型：0：目录，1：菜单，2：按钮
     */
    private String type;
}
