package com.ajar.springbootshiro.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/10/19 19:45
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceVo {
    /**
     * 主键id
     */
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
