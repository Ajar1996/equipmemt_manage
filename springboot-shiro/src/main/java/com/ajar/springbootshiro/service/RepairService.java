package com.ajar.springbootshiro.service;

import com.ajar.springbootshiro.from.RepairFrom;
import com.ajar.springbootshiro.from.RepairFrom;
import com.ajar.springbootshiro.model.Repair;
import com.ajar.springbootshiro.model.Resource;
import com.ajar.springbootshiro.vo.Result;
import org.springframework.data.domain.Pageable;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/12/11 11:01
 */
public interface RepairService {

    Result selectRepairList(String name, Pageable pageable);

    
    Result updateRepair(RepairFrom repairFrom);

    Result deleteRepair(Integer id);
}
