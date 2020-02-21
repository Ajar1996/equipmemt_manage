package com.ajar.springbootshiro.vo;

import com.ajar.springbootshiro.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.shiro.session.Session;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/10/21 16:42
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionVo {
    private Session session;
    private UserVo userVo;
}
