package com.ajar.springbootshiro.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @description:返回的数据模型
 * @author: Ajar
 * @time: 2019/9/30 16:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体数据
     */
    private T data;
}
