package com.ajar.springbootshiro.from;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

/**
 * @description:前端传来的资源数据
 * @author: Ajar
 * @time: 2019/10/20 13:14
 */
@Data
public class ResourceFrom {

    /**
     * 资源id
     */
    private Integer id;

    /**
     * 资源父id
     */
    @Digits(integer=2,fraction=2,message = "类型必须为整数")
    @Range(min = 0,max = 10,message = "父资源ID在0到10之间")
    private Integer parentId;

    /**
     * 资源名称
     */
    @Length(max = 32,message = "长度不能超过32位")
    @NotEmpty(message = "资源名称不能为空")
    private String name;

    /**
     * 权限标识符
     */
    @Length(max = 32,message = "长度不能超过32位")
    @NotEmpty(message = "资源标识符不能为空")
    private String perms;

    /**
     * 类型：0：目录，1：菜单，2：按钮
     */
    @Digits(integer=2,fraction=2,message = "类型必须为整数")
    @Range(min = 0,max = 2,message = "等级系数在0到2之间")
    private String type;

}
