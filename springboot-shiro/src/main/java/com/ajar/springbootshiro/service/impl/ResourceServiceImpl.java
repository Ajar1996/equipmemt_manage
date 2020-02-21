package com.ajar.springbootshiro.service.impl;

import com.ajar.springbootshiro.dao.*;
import com.ajar.springbootshiro.from.ResourceFrom;
import com.ajar.springbootshiro.model.Resource;
import com.ajar.springbootshiro.model.Role;
import com.ajar.springbootshiro.model.RoleResource;
import com.ajar.springbootshiro.model.UserRole;
import com.ajar.springbootshiro.service.ResourceService;
import com.ajar.springbootshiro.utils.JPAUtil;
import com.ajar.springbootshiro.utils.RUtil;
import com.ajar.springbootshiro.vo.ResourceVo;
import com.ajar.springbootshiro.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.INACTIVE;
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
import javax.xml.transform.Source;
import java.util.*;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/9/30 16:49
 */
@Service
@Transactional
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    ResourceDao resourceDao;

    @Autowired
    RoleResourceDao roleResourceDao;

    /*查询用户的权限表示符*/
    @Override
    public Set<String> selectUserPerms(Integer userId) {
        /*获取角色Id*/
        List<Integer> roleIdS=new ArrayList<>();
        List<UserRole> userRoles=userRoleDao.findByUserId(userId);
        userRoles.forEach(o->{
            roleIdS.add(o.getRoleId());
        });


        /*获取资源Id*/
        List<Integer> resourceIds=new ArrayList<>();
        List<RoleResource> roleResources=roleResourceDao.findByRoleIdIn(roleIdS);
        roleResources.forEach(o->{
            resourceIds.add(o.getResourceId());
        });

        /*获取权限表示符*/
        Set<String> prems=new HashSet<>();
        List<Resource> resources=resourceDao.findAllById(resourceIds);
        resources.forEach(o->{
            prems.add(o.getPerms());
        });

        return  prems;

    }

    /**
     * @description:保存资源基本信息
     * @author: Ajar
     * @time: 2019/10/20 14:03
     */
    @Override
    public Result saveResource(ResourceFrom resourceFrom) {
        Resource resource=new Resource();
        BeanUtils.copyProperties(resourceFrom,resource);
        Resource resourceSave=resourceDao.save(resource);
        log.info("资源基本信息保存={}"+resourceSave);
        return RUtil.success();
    }

    /**
     * @description:查询资源列表
     * @author: Ajar
     * @time: 2019/10/20 14:03
     */
    @Override
    public Result selectResourceList(String name, Pageable pageable) {
        Specification<Resource> specificationcation=new Specification<Resource>(){
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if (StringUtils.isNoneBlank(name)){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), JPAUtil.like(name)));
                }
                Predicate[] pre=new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(resourceDao.findAll(specificationcation,pageable));
    }

    /**
     * @description:更新资源
     * @author: Ajar
     * @time: 2019/10/20 14:03
     */
    @Override
    public Result updateResource(ResourceFrom resourceFrom) {
        Resource resource=new Resource();
        BeanUtils.copyProperties(resourceFrom,resource);

        Resource resourceSave=resourceDao.save(resource);

        log.info("资源信息更新={}"+resourceSave);
        return RUtil.success();
    }

    /**
     * @description:资源详情
     * @author: Ajar
     * @time: 2019/10/20 14:03
     */
    @Override
    public Result selectResourceDetail(Integer id) {

        ResourceVo resourceVo=new ResourceVo();

        /*查询用户基本信息*/
        Optional<Resource> resource=resourceDao.findById(id);
        BeanUtils.copyProperties(resource.get(),resourceVo);
        log.info("资源基本信息={}"+resourceVo);

        return RUtil.success(resourceVo);
    }

    /**
     * @description:删除资源接口
     * @author: Ajar
     * @time: 2019/10/20 14:03
     */
    @Override
    public Result deleteResource(Integer id) {
        resourceDao.deleteById(id);
        return RUtil.success();
    }


}
