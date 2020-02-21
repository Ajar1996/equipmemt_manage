package com.ajar.springbootshiro.controller;

import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.RepairFrom;
import com.ajar.springbootshiro.service.RepairService;
import com.ajar.springbootshiro.service.RepairService;
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
 * @time: 2019/12/11 15:14
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class RepairController {
    @Autowired
    RepairService repairService;

    /**
     * @description:查找报废报修申请列表
     * @author: Ajar
     * @time: 2019/10/19 16:23
     */
    @RequiresPermissions("sys:repair:list")
    @GetMapping("/selectRepairList")
    public Result selectRepairList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "name", defaultValue = "") String name) {

        Pageable pageable = PageRequest.of(page, size);
        return repairService.selectRepairList(name, pageable);
    }


    /**
     * @description:修改报废报修申请
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:repair:update")
    @PutMapping("/updateRepair")
    public Result updateRepair(@Validated @RequestBody RepairFrom repairFrom, BindingResult bindingResult) {
        Assert.isNull(repairFrom.getStatus(),"状态不能为空");
        Assert.isNull(repairFrom.getId(), "id不能为空");
        if (bindingResult.hasErrors()) {
            log.info("[更新报废报修申请]报废报修申请参数不正确={}" + repairFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return repairService.updateRepair(repairFrom);
    }



    /**
     * @description:删除报废报修申请
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:repair:delete")
    @DeleteMapping("/deleteRepair/{id}")
    public Result deleteRepair(@PathVariable Integer id) {
        return repairService.deleteRepair(id);
    }




    /**
     * @description:报修申请
     * @return:
     * @author: Ajar
     * @time: 2019/10/19 16:31
     */
    @RequiresPermissions("sys:repair:apply")
    @PostMapping("/applyRepair")
    public Result applyRepair(@Validated @RequestBody RepairFrom repairFrom, BindingResult bindingResult) {
        Assert.isNull(repairFrom.getStatus(),"状态不能为空");
        if (bindingResult.hasErrors()) {
            log.error("[新增报废报修申请]参数不正确：roleFrom={}" + repairFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return repairService.updateRepair(repairFrom);
    }

}
