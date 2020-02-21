package com.ajar.springbootshiro.service.impl;

import com.ajar.springbootshiro.dao.*;
import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.from.EquipmentFrom;
import com.ajar.springbootshiro.from.UserFrom;
import com.ajar.springbootshiro.model.Equipment;
import com.ajar.springbootshiro.model.Role;
import com.ajar.springbootshiro.model.User;
import com.ajar.springbootshiro.model.UserRole;
import com.ajar.springbootshiro.service.EquipmentService;
import com.ajar.springbootshiro.service.UserService;
import com.ajar.springbootshiro.utils.JPAUtil;
import com.ajar.springbootshiro.utils.RUtil;
import com.ajar.springbootshiro.utils.ShiroUtil;
import com.ajar.springbootshiro.vo.EquipmentVo;
import com.ajar.springbootshiro.vo.Result;
import com.ajar.springbootshiro.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentDao equipmentDao;

    @Autowired
    CirculateDao circulateDao;

    @Autowired
    RepairDao repairDao;

    @Override
    public Result saveEquipment(EquipmentFrom equipmentFrom) {

        Equipment equipment=new Equipment();
        
        /*保存设备信息*/
        BeanUtils.copyProperties(equipmentFrom,equipment);
        equipmentDao.save(equipment);
        log.info("设备信息保存={}"+equipment);
        
        return RUtil.success();
    }

    @Override
    public Result selectEquipmentList(String name, Pageable pageable) {
        Specification<Equipment> specificationcation=new Specification<Equipment>(){
            @Override
            public Predicate toPredicate(Root<Equipment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if (StringUtils.isNoneBlank(name)){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), JPAUtil.like(name)));
                }
                Predicate[] pre=new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(equipmentDao.findAll(specificationcation,pageable));

    }


    @Override
    public Result updateEquipment(EquipmentFrom equipmentFrom) {
        Equipment equipment=new Equipment();
        BeanUtils.copyProperties(equipmentFrom,equipment);

        Equipment equipmentSave=equipmentDao.save(equipment);

        log.info("设备信息更新={}"+equipmentSave);
        return RUtil.success();
    }

    @Override
    public Result deleteEquipment(Integer id) {
        equipmentDao.deleteById(id);
        circulateDao.deleteByEquipmentId(id);
        repairDao.deleteByEquipmentId(id);
        return RUtil.success();
    }


}
