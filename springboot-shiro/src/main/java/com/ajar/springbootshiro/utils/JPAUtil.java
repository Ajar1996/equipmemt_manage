package com.ajar.springbootshiro.utils;

/**
 * @description:模糊查询工具类
 * @author: Ajar
 * @time: 2019/10/11 17:43
 */
public class JPAUtil {
    public static String like(String column){
        StringBuilder sb = new StringBuilder("%"+column+"%");
        return sb.toString();
    }
}
