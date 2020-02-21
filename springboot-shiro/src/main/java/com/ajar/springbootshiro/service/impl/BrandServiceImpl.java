package com.ajar.springbootshiro.service.impl;

import com.ajar.springbootshiro.dao.CirculateDao;
import com.ajar.springbootshiro.dao.BrandDao;
import com.ajar.springbootshiro.dao.EquipmentDao;
import com.ajar.springbootshiro.dao.RepairDao;
import com.ajar.springbootshiro.from.BrandFrom;
import com.ajar.springbootshiro.model.Brand;
import com.ajar.springbootshiro.service.BrandService;
import com.ajar.springbootshiro.service.EquipmentService;
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
import java.util.List;

@Service
@Transactional
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandDao brandDao;
    
    @Autowired
    EquipmentService equipmentService;

    @Autowired
    EquipmentDao equipmentDao;

    @Autowired
    CirculateDao circulateDao;

    @Autowired
    RepairDao repairDao;

    @Override
    public Result saveBrand(BrandFrom brandFrom) {

        Brand brand=new Brand();
        
        /*保存设备类型信息*/
        BeanUtils.copyProperties(brandFrom,brand);
        brandDao.save(brand);
        log.info("设备类型信息保存={}"+brand);
        
        return RUtil.success();
    }

    @Override
    public Result selectBrandList(String name, Pageable pageable) {
        Specification<Brand> specificationcation=new Specification<Brand>(){
            @Override
            public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if (StringUtils.isNoneBlank(name)){
                    predicates.add(criteriaBuilder.like(root.get("brand_name").as(String.class), JPAUtil.like(name)));
                }
                Predicate[] pre=new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(brandDao.findAll(specificationcation,pageable));

    }


    @Override
    public Result updateBrand(BrandFrom brandFrom) {
        Brand brand=new Brand();
        BeanUtils.copyProperties(brandFrom,brand);

        Brand brandSave=brandDao.save(brand);

        log.info("设备类型信息更新={}"+brandSave);
        return RUtil.success();
    }

    @Override
    public Result deleteBrand(Integer id) {
        brandDao.deleteById(id);
        equipmentDao.deleteByBrandId(id);
        circulateDao.deleteByEquipmentId(id);
        repairDao.deleteByEquipmentId(id);
        return RUtil.success();
    }
}
