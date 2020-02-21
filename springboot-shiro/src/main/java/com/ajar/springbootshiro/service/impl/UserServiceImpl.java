package com.ajar.springbootshiro.service.impl;

import com.ajar.springbootshiro.dao.RoleDao;
import com.ajar.springbootshiro.dao.UserDao;
import com.ajar.springbootshiro.dao.UserRoleDao;
import com.ajar.springbootshiro.enums.REnum;
import com.ajar.springbootshiro.from.UserFrom;
import com.ajar.springbootshiro.model.Role;
import com.ajar.springbootshiro.model.User;
import com.ajar.springbootshiro.model.UserRole;
import com.ajar.springbootshiro.service.UserService;
import com.ajar.springbootshiro.utils.JPAUtil;
import com.ajar.springbootshiro.utils.RUtil;
import com.ajar.springbootshiro.utils.ShiroUtil;
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
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    RoleDao roleDao;

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @Override
    public User findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    /**
     * 新增用户
     * @param userFrom
     * @return
     */
    @Override
    public Result saveUser(UserFrom userFrom) {
        /*判断用户是否存在*/
        if (userDao.findByAccount(userFrom.getAccount())!=null){
            log.error(userFrom.getAccount());
            return RUtil.error(REnum.ACCOUNT_EXIST.getCode(),REnum.ACCOUNT_EXIST.getMessage());
        }

        /*分离用户基本信息与其角色*/
        User user=new User();
        BeanUtils.copyProperties(userFrom,user);

        /*生成盐以及加密密码保存*/
        String salt=ShiroUtil.getSalt();
        String Md5Password=ShiroUtil.MD5(user.getPassword(),salt);
        user.setPassword(Md5Password);
        user.setSalt(salt);
        User userSave=userDao.save(user);
        log.info("用户基本信息保存：userSave={}"+userSave);

        /*用户对应角色保存 */
        List<UserRole> userRoles=new ArrayList<>();
        userFrom.getRoles().forEach(o -> {
            UserRole userRole=new UserRole();
            userRole.setRoleId(o.getId());
            userRole.setUserId(userSave.getId());
            userRoles.add(userRole);
        });
        List<UserRole> userRolesSave=userRoleDao.saveAll(userRoles);

        log.info("用户角色保存：userRoleSave={}"+userRolesSave);
        return RUtil.success();
    }

    /**
     * 查询用户列表
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Result selectUserList(String name, Pageable pageable) {

        /*JPA复杂查询
        使用匿名内部类的方式，创建一个Specification的实现类，并实现toPredicate方法*/
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNoneBlank(name)){
                    predicate.add(criteriaBuilder.like(root.get("name").as(String.class), JPAUtil.like(name)));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(userDao.findAll(specification,pageable));
    }

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    @Override
    public Result selectUserDetail(Integer id) {

        /*查询用户基本信息*/
        UserVo userVo=new UserVo();
        Optional<User> user=userDao.findById(id);
        BeanUtils.copyProperties(user.get(),userVo);
        userVo.setPassword("*********");
        log.info("用户基本信息={}"+userVo);


        /*查询角色ID*/
        List<UserRole> userRoles=userRoleDao.findByUserId(id);
        List<Integer> roldIds=new ArrayList<>();
        userRoles.forEach(o->{
            roldIds.add(o.getRoleId());
        });

        /*查询对应角色*/
        List<Role> roles=roleDao.findAllById(roldIds);
        log.info("用户角色={}"+roles);
        userVo.setRoles(roles);
        return RUtil.success(userVo);
    }

    /**
     * 更新用户详情
     * @param userFrom
     * @return
     */
    @Override
    public Result updateUser(UserFrom userFrom) {
        /*分离用户与其拥有角色*/
        User user = new User();
        BeanUtils.copyProperties(userFrom,user);


        /*初始化密码与盐并保存*/
        String salt = ShiroUtil.getSalt();
        String md5Password = ShiroUtil.MD5(user.getPassword(),salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        User userSave = userDao.save(user);
        log.info("用户更新：sysUserSave = {}"+ userSave);

        /*初始化用户角色*/
        userRoleDao.deleteByUserId(userFrom.getId());


        System.out.println(userFrom);
        /*添加用户角色*/
        List<UserRole> userRoles = new ArrayList<>();
        userFrom.getRoles().forEach(o->{
            UserRole userRole = new UserRole();
            userRole.setRoleId(o.getId());
            userRole.setUserId(userFrom.getId());
            userRoles.add(userRole);
            System.out.println(o);
        });
        List<UserRole> userRolesSave = userRoleDao.saveAll(userRoles);
        log.info("用户角色：sysUserRolesSave = {}"+userRolesSave);
        return RUtil.success();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public Result delectUser(Integer id) {
        userRoleDao.deleteByUserId(id);
        userDao.deleteById(id);
        return RUtil.success();
    }
}
