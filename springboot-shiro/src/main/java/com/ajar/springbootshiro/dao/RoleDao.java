package com.ajar.springbootshiro.dao;

import com.ajar.springbootshiro.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/9/30 12:43
 */
@Repository
public interface RoleDao extends JpaRepository<Role,Integer> {
    Page<Role> findAll(Specification<Role> sysRoleSpecification, Pageable pageable);
}
