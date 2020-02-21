package com.ajar.springbootshiro.dao;

import com.ajar.springbootshiro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer>  {
    User findByAccount(String Account);
    Page<User> findAll(Specification<User> sysRoleSpecification, Pageable pageable);
}
