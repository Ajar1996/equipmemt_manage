package com.ajar.springbootshiro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
/**
 * 用户
 *
 */
@Data
@Entity
@Table(name = "user")
@ToString(exclude={"roles"})
@DynamicInsert
@DynamicUpdate
public class User {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 盐
     */
    @JsonIgnore
    private String salt;

    /**
     * 是否禁用 0：否；1：是
     */
    private String forbidden;
}
