package com.ajar.springbootshiro.enums;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/9/30 17:23
 */
public enum RepairEnum {

    /**
     * 状态
     * 0：申请状态
     * 1：确认状态
     * 2：修理状态
     * 3：寄回状态
     */

    APPLY(0,"申请"),

    CONFIRM(1,"确认"),

    REPAIREING(2,"修理"),

    SENT(3,"寄回");
    private Integer code;

    private String message;
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    RepairEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
