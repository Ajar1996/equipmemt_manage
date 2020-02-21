package com.ajar.springbootshiro.service;

import com.ajar.springbootshiro.from.EquipmentFrom;
import com.ajar.springbootshiro.from.EquipmentFrom;
import com.ajar.springbootshiro.vo.Result;
import org.springframework.data.domain.Pageable;

public interface EquipmentService {
    Result saveEquipment(EquipmentFrom equipmentFrom);

    Result selectEquipmentList(String name, Pageable pageable);


    Result updateEquipment(EquipmentFrom equipmentFrom);

    Result deleteEquipment(Integer id);

}
