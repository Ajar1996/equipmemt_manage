package com.ajar.springbootshiro.service.impl;

import com.ajar.springbootshiro.dao.RepairDao;
import com.ajar.springbootshiro.enums.RepairEnum;
import com.ajar.springbootshiro.from.RepairFrom;
import com.ajar.springbootshiro.model.Repair;
import com.ajar.springbootshiro.model.Repair;
import com.ajar.springbootshiro.model.Repair;
import com.ajar.springbootshiro.service.RepairService;
import com.ajar.springbootshiro.utils.JPAUtil;
import com.ajar.springbootshiro.utils.RUtil;
import com.ajar.springbootshiro.vo.Result;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/12/11 11:15
 */
@Service
@Transactional
@Slf4j
public class RepairServiceImpl implements RepairService {

    @Autowired
    RepairDao repairDao;

    /*查询列表*/
    @Override
    public Result selectRepairList(String status, Pageable pageable) {
        
        Specification<Repair> specificationcation = new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNoneBlank(status)) {
                    predicates.add(criteriaBuilder.like(root.get("status").as(String.class), JPAUtil.like(status))); //状态条件查询
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(repairDao.findAll(specificationcation, pageable));
    }



    @Override
    public Result updateRepair(RepairFrom repairFrom) {
        Repair repair=new Repair();
        BeanUtils.copyProperties(repairFrom,repair);
        /**
         * 状态
         * 0：申请维修
         * 1：申请报废
         * 2：修理状态
         * 3：寄回状态
         * 4：完结状态
         */
        if(repair.getStatus()==0){
            repair.setRemark("[报修]"+repair.getRemark());
            repair.setApplicationTime(new Date());
        }else if(repair.getStatus()==1){
            repair.setRemark("[报废]"+repair.getRemark());
            repair.setApplicationTime(new Date());
        }else if(repair.getStatus()==2){
            Optional<Repair> repair1=repairDao.findById(repair.getId());
            repair.setApplicationTime(repair1.get().getApplicationTime());
            repair.setRepairTime(new Date());
        }else if(repair.getStatus()==3){
            Optional<Repair> repair1=repairDao.findById(repair.getId());
            repair.setApplicationTime(repair1.get().getApplicationTime());
            repair.setRepairTime(repair1.get().getRepairTime());
            repair.setSentTime(new Date());
        }else if(repair.getStatus()==4){
            Optional<Repair> repair1=repairDao.findById(repair.getId());
            repair.setApplicationTime(repair1.get().getApplicationTime());
            repair.setRepairTime(repair1.get().getRepairTime());
            repair.setSentTime(repair1.get().getSentTime());
        }

        Repair repairSave=repairDao.save(repair);

        log.info("设备报废报修信息更新={}"+repairSave);
        return RUtil.success();
    }

    @Override
    public Result deleteRepair(Integer id) {
        repairDao.deleteById(id);
        return RUtil.success();
    }
}
