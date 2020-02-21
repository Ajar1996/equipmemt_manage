package com.ajar.springbootshiro.controller;

import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.RoleFrom;
import com.ajar.springbootshiro.service.RoleService;
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

/**
 * @description:角色
 * @author: Ajar
 * @time: 2019/10/13 17:10
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class RoleController {
    @Autowired
    RoleService roleService;


    /**
     * @description:查找角色列表
     * @author: Ajar
     * @time: 2019/10/19 16:23
     */
    @RequiresPermissions("sys:role:list")
    @GetMapping("/selectRoleList")
    public Result selectRoleList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestParam(value = "name",defaultValue = "") String name){
        Pageable pageable=PageRequest.of(page,size);
        return roleService.selectRoleList(name,pageable);
    }

    /**
     * @description:查看角色详情
     * @author: Ajar
     * @time: 2019/10/19 16:23
     */
    @RequiresPermissions("sys:role:detail")
    @GetMapping("selectRoleDetail")
    public Result selectRoleDetail(@RequestParam(value = "id",required = false) Integer id){
        Assert.isNull(id,"ID不能为空");
        return  roleService.selectRoleDetail(id);
    }

    /**
     * @description:修改角色
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:role:update")
    @PutMapping("updateRole")
    public Result updateRole(@Validated @RequestBody RoleFrom roleFrom, BindingResult bindingResult){
        Assert.isNull(roleFrom.getId(),"id不能为空");
        if (bindingResult.hasErrors()){
            log.info("[更新角色]角色参数不正确={}"+roleFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return roleService.updateRole(roleFrom);
    }


    /**
     * @description:删除角色
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:role:delete")
    @DeleteMapping("/deleteRole/{id}")
    public Result deleteRole(@PathVariable Integer id){
        return roleService.deleteRole(id);
    }

    /**
     * @description:保存角色
     * @return:
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:role:insert")
    @PostMapping("/saveRole")
    public Result saveRole(@Validated @RequestBody RoleFrom roleFrom, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[新增角色]参数不正确：roleFrom={}"+roleFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return roleService.saveRole(roleFrom);
    }







}
