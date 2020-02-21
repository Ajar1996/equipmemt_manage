package com.ajar.springbootshiro.vo;

import com.ajar.springbootshiro.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/9/30 16:21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo {
    /**
     * 主键id
     */
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
    private String password;

    /**
     * 是否禁用 0：否；1：是
     */
    private String forbidden;

    /**
     * 用户角色
     */
    private List<Role> roles;
}
