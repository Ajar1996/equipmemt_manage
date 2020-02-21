package com.ajar.springbootshiro.controller;

import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.BrandFrom;
import com.ajar.springbootshiro.service.BrandService;
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
 * @description:设备类型
 * @author: Ajar
 * @time: 2019/10/19 19:41
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    BrandService brandService;

    /**
     * @description:查找设备类型列表
     * @author: Ajar
     * @time: 2019/10/19 16:23
     */
    @RequiresPermissions("sys:brand:list")
    @GetMapping("/selectBrandList")
    public Result selectBrandList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "name", defaultValue = "") String name) {

        Pageable pageable = PageRequest.of(page, size);
        return brandService.selectBrandList(name, pageable);
    }


    /**
     * @description:修改设备类型
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:brand:update")
    @PutMapping("/updateBrand")
    public Result updateBrand(@Validated @RequestBody BrandFrom brandFrom, BindingResult bindingResult) {
        Assert.isNull(brandFrom.getId(), "id不能为空");
        if (bindingResult.hasErrors()) {
            log.info("[更新设备类型]设备类型参数不正确={}" + brandFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return brandService.updateBrand(brandFrom);
    }



    /**
     * @description:删除设备类型
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:brand:delete")
    @DeleteMapping("/deleteBrand/{id}")
    public Result deleteBrand(@PathVariable Integer id) {
        return brandService.deleteBrand(id);
    }

    /**
     * @description:保存设备类型
     * @return:
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:brand:insert")
    @PostMapping("/saveBrand")
    public Result saveRole(@Validated @RequestBody BrandFrom brandFrom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[新增设备类型]参数不正确：roleFrom={}" + brandFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return brandService.saveBrand(brandFrom);
    }

}
