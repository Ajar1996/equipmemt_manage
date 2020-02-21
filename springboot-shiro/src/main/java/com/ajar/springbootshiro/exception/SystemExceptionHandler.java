package com.ajar.springbootshiro.exception;

import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.utils.RUtil;
import com.ajar.springbootshiro.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * @description:异常处理器
 * @author: Ajar
 * @time: 2019/10/11 15:37
 */
@Slf4j
@RestControllerAdvice
public class SystemExceptionHandler {

    /**
     * @description:缺少权限异常处理
     * @author: Ajar
     * @time: 2019/10/11 15:52
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException e){
        log.error(REnum.NOT_PERMSSION.getMessage());
        return RUtil.error(REnum.NOT_PERMSSION.getCode(),REnum.NOT_PERMSSION.getMessage());
    }
}
