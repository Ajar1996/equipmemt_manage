package com.ajar.springbootshiro.model;

import lombok.Data;
import lombok.ToString;
import javax.persistence.*;

/**
 * @description:角色
 * @author: Ajar
 * @time: 2019/9/30 12:25
 */
@Data
@Entity
@Table(name = "role")
@ToString(exclude={"users"})
public class Role {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 角色名
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色等级
     */
    @Column(name = "grade")
    private Integer grade;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

}
