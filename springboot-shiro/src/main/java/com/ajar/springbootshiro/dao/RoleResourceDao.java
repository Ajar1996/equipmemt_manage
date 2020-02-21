package com.ajar.springbootshiro.dao;


import com.ajar.springbootshiro.model.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleResourceDao extends JpaRepository<RoleResource,Integer> {
    List<RoleResource> findByRoleId(Integer roleId);

    List<RoleResource> findByRoleIdIn(List<Integer> roleIds);

    void deleteByRoleId(Integer id);
}
