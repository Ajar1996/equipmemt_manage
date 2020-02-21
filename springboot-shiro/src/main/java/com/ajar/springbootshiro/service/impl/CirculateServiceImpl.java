package com.ajar.springbootshiro.service.impl;

import com.ajar.springbootshiro.dao.CirculateDao;
import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.exception.SystemException;
import com.ajar.springbootshiro.from.CirculateFrom;
import com.ajar.springbootshiro.model.Circulate;
import com.ajar.springbootshiro.model.Circulate;
import com.ajar.springbootshiro.model.Circulate;
import com.ajar.springbootshiro.service.CirculateService;
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
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:出入库处理逻辑层
 * @author: Ajar
 * @time: 2019/12/11 9:19
 */
@Service
@Transactional
@Slf4j
public class CirculateServiceImpl implements CirculateService {

    @Autowired
    CirculateDao circulateDao;

    /**
     * @description:入库处理
     * @return:
     * @author: Ajar
     * @time: 2019/12/11 11:12
     */
    @Override
    public Result PutInStorage(CirculateFrom circulateFrom) {
        /*判断设备是否已经入库*/
        if (circulateDao.findByEquipmentIdAndType(circulateFrom.getEquipmentId(),1) != null) {
            return  RUtil.error(REnum.Equipmenti_EXIST.getCode(),REnum.Equipmenti_EXIST.getMessage());
        }

        Circulate circulate = new Circulate();
        /*保存入库信息*/
        BeanUtils.copyProperties(circulateFrom, circulate);

        circulate.setTime(new Date());
        circulate.setType(1);
        circulateDao.save(circulate);
        log.info("入库信息保存={}" + circulate);

        return RUtil.success();

    }

    /**
     * @description:出库处理
     * @return:
     * @author: Ajar
     * @time: 2019/12/11 11:12
     */
    @Override
    public Result OutInStorage(CirculateFrom circulateFrom) {
        /*判断设备是否已经出库*/
        if (circulateDao.findByEquipmentIdAndType(circulateFrom.getEquipmentId(),0) != null) {
            return  RUtil.error(REnum.Equipmenti_EXIST.getCode(),REnum.Equipmenti_EXIST.getMessage());
        }
        Circulate circulate = new Circulate();
        /*保存出库信息*/
        BeanUtils.copyProperties(circulateFrom, circulate);
        circulate.setTime(new Date());
        circulate.setType(0);
        circulateDao.save(circulate);
        log.info("出库信息保存={}" + circulate);

        return RUtil.success();
    }


    @Override
    public Result CountOfout(Data data1, Data data2, Pageable pageable) {
        return null;
    }

    @Override
    public Result CountOfin(Data data1, Data data2, Pageable pageable) {
        return null;
    }


    /**
     * @description:出入库列表
     * @author: Ajar
     * @time: 2019/12/11 11:12
     */
    @Override
    public Result selectCirculateList(String name, Pageable pageable) {
        Specification<Circulate> specificationcation = new Specification<Circulate>() {
            @Override
            public Predicate toPredicate(Root<Circulate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNoneBlank(name)) {
                    predicates.add(criteriaBuilder.like(root.get("equipment_id").as(String.class), JPAUtil.like(name)));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(circulateDao.findAll(specificationcation, pageable));
    }

    /**
     * @description:更新出入库
     * @author: Ajar
     * @time: 2019/12/11 11:12
     */
    @Override
    public Result updateCirculate(CirculateFrom circulateFrom) {
        Circulate equipment = new Circulate();
        BeanUtils.copyProperties(circulateFrom, equipment);

        Circulate equipmentSave = circulateDao.save(equipment);

        log.info("出入库信息更新={}" + equipmentSave);
        return RUtil.success();
    }

    /**
     * @description:删除出入库的产品
     * @author: Ajar
     * @time: 2019/12/11 11:12
     */
    @Override
    public Result deleteCirculate(Integer id) {
        circulateDao.deleteById(id);
        return RUtil.success();
    }
}
