package com.ajar.springbootshiro.dao;

import com.ajar.springbootshiro.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceDao extends JpaRepository<Resource,Integer> {
    Page<Resource> findAll(Specification<Resource> sysRoleSpecification, Pageable pageable);
}
