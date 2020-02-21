package com.ajar.springbootshiro.utils;

import com.ajar.springbootshiro.exception.SystemException;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:数据校验工具类
 * @author: Ajar
 * @time: 2019/9/30 18:16
 */
public class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new SystemException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new SystemException(message);
        }
    }
}
