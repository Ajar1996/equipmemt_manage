package com.ajar.springbootshiro.controller;

import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.ResourceFrom;
import com.ajar.springbootshiro.service.ResourceService;
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
 * @description:
 * @author: Ajar
 * @time: 2019/10/19 19:41
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    /**
     * @description:查找资源列表
     * @author: Ajar
     * @time: 2019/10/19 16:23
     */
    @RequiresPermissions("sys:resource:list")
    @GetMapping("/selectResourceList")
    public Result selectResourceList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestParam(value = "name",defaultValue = "") String name){

        Pageable pageable= PageRequest.of(page,size);
        return resourceService.selectResourceList(name,pageable);
    }


    /**
     * @description:修改角色
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:resource:update")
    @PutMapping("/updateResource")
    public Result updateResource(@Validated @RequestBody ResourceFrom resourceFrom, BindingResult bindingResult){
        Assert.isNull(resourceFrom.getId(),"id不能为空");
        if (bindingResult.hasErrors()){
            log.info("[更新角色]角色参数不正确={}"+resourceFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return resourceService.updateResource(resourceFrom);
    }

    /**
     * @description:角色详情
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:resource:detail")
    @GetMapping("selectResourceDetail")
    public Result selectResourceDetail(@RequestParam(value = "id",required = false) Integer id){
        Assert.isNull(id,"ID不能为空");
        return  resourceService.selectResourceDetail(id);
    }


    /**
     * @description:删除角色
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:resource:delete")
    @DeleteMapping("/deleteResource/{id}")
    public Result deleteResource(@PathVariable Integer id){
        return resourceService.deleteResource(id);
    }

    /**
     * @description:保存角色
     * @return:
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:resource:insert")
    @PostMapping("/saveResource")
    public Result saveRole(@Validated @RequestBody ResourceFrom resourceFrom, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[新增角色]参数不正确：roleFrom={}"+resourceFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        return resourceService.saveResource(resourceFrom);
    }

}
