package com.ajar.springbootshiro.controller;

import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.UserFrom;
import com.ajar.springbootshiro.model.User;
import com.ajar.springbootshiro.service.UserService;
import com.ajar.springbootshiro.utils.Assert;
import com.ajar.springbootshiro.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    /**
     * @description:保存用户
     * @return:
     * @author: Ajar
     * @time: 2019/10/11 15:55
     */
    @RequiresPermissions("sys:user:insert")
    @PostMapping("/saveUser")
    public Result saveUser(@Validated @RequestBody UserFrom userFrom, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[新增用户]参数不正确：userFrom={}"+userFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.saveUser(userFrom);
    }

    /**
     * @description:查询用户列表
     * @return:
     * @author: Ajar
     * @time: 2019/10/11 16:01
     */
    @RequiresPermissions("sys:user:list")
    @GetMapping("/selectUserList")
    public Result selectUserList(@RequestParam(value = "page",defaultValue = "0") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                                @RequestParam(value = "name",defaultValue = "") String name){
        Pageable pageable=PageRequest.of(page,size);
        return userService.selectUserList(name,pageable);
    }

    /**
     * @description:用户详情
     * @author: Ajar
     * @time: 2019/10/11 16:14
     */
    @RequiresPermissions("sys:user:detial")
    @GetMapping("/selectUserDetail")
    public Result selectUserDetail(@RequestParam(value = "id", required = false) Integer id){
        Assert.isNull(id,"id不能为空");
        return userService.selectUserDetail(id);
    }

    /**
     * @description:更新用户
     * @author: Ajar
     * @time: 2019/10/11 16:33
     */
    @RequiresPermissions("sys:user:update")
    @PutMapping("/updateUser")
    public Result updateUsear(@Validated @RequestBody UserFrom userFrom,BindingResult bindingResult){
        Assert.isNull(userFrom.getId(),"id不能为空");
        if (bindingResult.hasErrors()){
            log.error("[更新用户]参数不正确：userFrom={}"+userFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        return userService.updateUser(userFrom);
    }

    /**
     * @description:删除用户
     * @author: Ajar
     * @time: 2019/10/11 16:50
     */
    @RequiresPermissions("sys:user:delete")
    @DeleteMapping("/deleteUser/{id}")
    public Result deleteUser(@PathVariable Integer id){
        return userService.delectUser(id);
    }



}
