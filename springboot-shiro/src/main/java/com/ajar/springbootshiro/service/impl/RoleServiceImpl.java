package com.ajar.springbootshiro.service.impl;

import com.ajar.springbootshiro.dao.ResourceDao;
import com.ajar.springbootshiro.dao.RoleDao;
import com.ajar.springbootshiro.dao.RoleResourceDao;
import com.ajar.springbootshiro.from.RoleFrom;
import com.ajar.springbootshiro.model.Resource;
import com.ajar.springbootshiro.model.Role;
import com.ajar.springbootshiro.model.RoleResource;
import com.ajar.springbootshiro.service.RoleService;
import com.ajar.springbootshiro.utils.JPAUtil;
import com.ajar.springbootshiro.utils.RUtil;
import com.ajar.springbootshiro.vo.Result;
import com.ajar.springbootshiro.vo.RoleVo;
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

/**
 * @description:
 * @author: Ajar
 * @time: 2019/9/30 16:44
 */
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    RoleResourceDao roleResourceDao;

    @Autowired
    ResourceDao resourceDao;

    @Override
    public Result saveRole(RoleFrom roleFrom) {
        /*分离角色保存*/
        Role role=new Role();
        BeanUtils.copyProperties(roleFrom,role);
        Role roleSave=roleDao.save(role);
        log.info("角色基本信息保存={}"+roleSave);

        /*构建该角色资源并保存*/
        List<RoleResource> roleResources=new ArrayList<>();
        roleFrom.getResources().forEach(o->{
            RoleResource roleResource=new RoleResource();
            roleResource.setRoleId(roleSave.getId());
            roleResource.setResourceId(o.getId());
            roleResources.add(roleResource);
        });
        System.out.println(roleResources);
        List<RoleResource> roleResourcesSave= roleResourceDao.saveAll(roleResources);
        log.info("角色资源保存={}"+roleResourcesSave);

        return RUtil.success();
    }

    /**
     * 查询角色列表
     * @param pageable
     * @return
     */
    @Override
    public Result selectRoleList(String name, Pageable pageable) {
        Specification<Role> specificationcation=new Specification<Role>(){
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if (StringUtils.isNoneBlank(name)){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), JPAUtil.like(name)));
                }
                Predicate[] pre=new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(roleDao.findAll(specificationcation,pageable));
    }

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    @Override
    public Result selectRoleDetail(Integer id) {
        /*查询用户基本信息*/
        RoleVo roleVo=new RoleVo();
        Optional<Role> role=roleDao.findById(id);
        BeanUtils.copyProperties(role.get(),roleVo);

        /*取出ResourceID*/
        List<Integer> resourceIDs=new ArrayList<>();
        List<RoleResource> roleResources=roleResourceDao.findByRoleId(id);
        roleResources.forEach(o->{
            resourceIDs.add(o.getResourceId());
        });

        /*根据资源ID查询资源*/
        List<Resource> resourceList=resourceDao.findAllById(resourceIDs);
        roleVo.setResources(resourceList);

        log.info("用户详情：{}"+roleVo);
        return RUtil.success(roleVo);
    }
    /**
     * 更新角色
     * @param roleFrom
     * @return
     */
    @Override
    public Result updateRole(RoleFrom roleFrom) {
        Role role=new Role();
        BeanUtils.copyProperties(roleFrom,role);

        /*初始化角色资源*/
        roleResourceDao.deleteByRoleId(roleFrom.getId());

        /*角色基本信息更新*/
        Role roleSave=roleDao.save(role);
        log.info("角色基本信息更新={}"+roleSave);

        /*更新角色资源*/
        List<RoleResource> roleResources=new ArrayList<>();
        roleFrom.getResources().forEach(o->{
            RoleResource roleResource=new RoleResource();
            roleResource.setResourceId(o.getId());
            roleResource.setRoleId(roleFrom.getId());
            roleResources.add(roleResource);
        });

        List<RoleResource> roleResourcesSave=roleResourceDao.saveAll(roleResources);
        log.info("角色资源更新={}"+roleResourcesSave);

        return RUtil.success();
    }

    /**
     * 删除角色接口
     * @param id
     * @return
     */
    @Override
    public Result deleteRole(Integer id) {
        roleDao.deleteById(id);
        roleResourceDao.deleteByRoleId(id);
        return RUtil.success();
    }
}
