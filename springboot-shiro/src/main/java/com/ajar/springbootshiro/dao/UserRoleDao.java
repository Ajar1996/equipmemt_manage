package com.ajar.springbootshiro.dao;

import com.ajar.springbootshiro.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRoleDao extends JpaRepository<UserRole,Integer> {
    List<UserRole> findByUserId(Integer id);

    void deleteByUserId(Integer id);
}
