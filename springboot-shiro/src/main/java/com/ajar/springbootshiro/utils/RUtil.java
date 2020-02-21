package com.ajar.springbootshiro.utils;

import com.ajar.springbootshiro.vo.Result;

/**
 * @description:对象模型工具类
 * @author: Ajar
 * @time: 2019/9/30 18:51
 */
public class RUtil {
    public static Result success(Object object){
        Result r = new Result();
        r.setCode(0);
        r.setMsg("ok");
        r.setData(object);
        return r;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code, String msg){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
