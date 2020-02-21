package com.ajar.springbootshiro.enums;

import com.ajar.springbootshiro.model.Equipment;

/**
 * @description:返回信息枚举
 * @author: Ajar
 * @time: 2019/9/30 18:31
 */
public enum REnum {

    UNkNOWN_ACCOUNT(1,"用户不存在"),

    PARAM_ERROR(2, "参数不正确"),

    ACCOUNT_EXIST(3,"该账号已存在"),

    USERNAME_OR_PASSWORD_ERROR(4,"用户名或密码错误"),

    ACCOUNT_DISABLE(5,"账号已被禁用"),

    AUTH_ERROR(6,"账户验证失败"),

    NOT_LOGIN(7,"未登录"),

    NOT_PERMSSION(8,"您没有访问该功能的权限"),

    Equipmenti_EXIST(9,"设备已借出");



    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    REnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
