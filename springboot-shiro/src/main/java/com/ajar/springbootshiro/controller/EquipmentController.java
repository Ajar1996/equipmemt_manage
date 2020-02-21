package com.ajar.springbootshiro.controller;

import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.EquipmentFrom;
import com.ajar.springbootshiro.service.EquipmentService;
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
 * @description:设备
 * @author: Ajar
 * @time: 2019/10/19 19:41
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    /**
     * @description:查找设备列表
     * @author: Ajar
     * @time: 2019/10/19 16:23
     */
    @RequiresPermissions("sys:equipment:list")
    @GetMapping("/selectEquipmentList")
    public Result selectEquipmentList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "name", defaultValue = "") String name) {

        Pageable pageable = PageRequest.of(page, size);
        return equipmentService.selectEquipmentList(name, pageable);
    }


    /**
     * @description:修改设备
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:equipment:update")
    @PutMapping("/updateEquipment")
    public Result updateEquipment(@Validated @RequestBody EquipmentFrom equipmentFrom, BindingResult bindingResult) {
        Assert.isNull(equipmentFrom.getId(), "id不能为空");
        if (bindingResult.hasErrors()) {
            log.info("[更新设备]设备参数不正确={}" + equipmentFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return equipmentService.updateEquipment(equipmentFrom);
    }



    /**
     * @description:删除设备
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:equipment:delete")
    @DeleteMapping("/deleteEquipment/{id}")
    public Result deleteEquipment(@PathVariable Integer id) {
        return equipmentService.deleteEquipment(id);
    }

    /**
     * @description:保存设备
     * @return:
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:equipment:insert")
    @PostMapping("/saveEquipment")
    public Result saveRole(@Validated @RequestBody EquipmentFrom equipmentFrom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[新增设备]参数不正确：roleFrom={}" + equipmentFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return equipmentService.saveEquipment(equipmentFrom);
    }

}
