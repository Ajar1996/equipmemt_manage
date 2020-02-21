package com.ajar.springbootshiro.enums;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/9/30 17:23
 */
public enum  ForbiddenEnum {
    ENABL(0,"启用"),
    DISABLE(1,"禁用");


    private Integer code;

    private String message;
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ForbiddenEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
