package com.ajar.springbootshiro.controller;


import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.BrandFrom;
import com.ajar.springbootshiro.from.CirculateFrom;
import com.ajar.springbootshiro.service.BrandService;
import com.ajar.springbootshiro.service.CirculateService;
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
 * @time: 2019/12/11 10:22
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class CirculateController {

    @Autowired
    CirculateService circulateService;

    @Autowired
    BrandService brandService;

    /**
     * @description:出入库列表
     * @author: Ajar
     * @time: 2019/12/11 10:22
     */
    @RequiresPermissions("sys:circulate:list")
    @GetMapping("/selectCirculateList")
    public Result selectCirculateList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "name", defaultValue = "") String name) {

        Pageable pageable;
        pageable = PageRequest.of(page, size);
        return circulateService.selectCirculateList(name, pageable);
    }


    /**
     * @description:修改出入库信息
     * @author: Ajar
     * @time: 2019/12/11 10:22
     */
    @RequiresPermissions("sys:circulate:update")
    @PutMapping("/updateCirculate")
    public Result updateCirculate(@Validated @RequestBody CirculateFrom circulateFrom, BindingResult bindingResult) {
        Assert.isNull(circulateFrom.getId(), "id不能为空");
        if (bindingResult.hasErrors()) {
            log.info("[更新出入库信息]出入库信息参数不正确={}" + circulateFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return circulateService.updateCirculate(circulateFrom);
    }



    /**
     * @description:删除出入库
     * @author: Ajar
     * @time: 2019/12/11 10:22
     */
    @RequiresPermissions("sys:circulate:delete")
    @DeleteMapping("/deleteCirculate/{id}")
    public Result deleteCirculate(@PathVariable Integer id) {
        return circulateService.deleteCirculate(id);
    }

    /**
     * @description:入库
     * @return:
     * @author: Ajar
     * @time: 2019/12/11 10:22
     */
    @RequiresPermissions("sys:circulate:put")
    @PostMapping("/saveCirculatePut")
    public Result saveCirculateIn(@Validated @RequestBody CirculateFrom circulateFrom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[新增设备类型]参数不正确：roleFrom={}" + circulateFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return circulateService.PutInStorage(circulateFrom);
    }

    /**
     * @description:出库
     * @return:
     * @author: Ajar
     * @time: 2019/12/11 10:22
     */
    @RequiresPermissions("sys:circulate:out")
    @PostMapping("/saveCirculateOut")
    public Result saveCirculateOut(@Validated @RequestBody CirculateFrom circulateFrom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[新增设备类型]参数不正确：roleFrom={}" + circulateFrom);
            throw new SystemException(REnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return circulateService.OutInStorage(circulateFrom);
    }

}
