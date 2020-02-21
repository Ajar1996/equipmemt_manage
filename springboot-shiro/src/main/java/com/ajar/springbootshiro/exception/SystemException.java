package com.ajar.springbootshiro.exception;

import com.ajar.springbootshiro.enums.REnum;

/**
 * @description:自定义异常类
 * @author: Ajar
 * @time: 2019/9/30 18:30
 */
public class SystemException extends RuntimeException {

    private Integer code;

    public SystemException(REnum rEnum) {
        super(rEnum.getMessage());

        this.code = rEnum.getCode();
    }

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(String message) {
        super(message);
    }
}
