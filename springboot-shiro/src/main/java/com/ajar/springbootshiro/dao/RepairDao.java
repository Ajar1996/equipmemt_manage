package com.ajar.springbootshiro.dao;

import com.ajar.springbootshiro.model.Repair;
import com.ajar.springbootshiro.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RepairDao extends JpaRepository<Repair,Integer> {

    Page<Repair> findAll(Specification<Repair> sysRoleSpecification, Pageable pageable);

    @Transactional
    void deleteByEquipmentId(Integer Equipment_id);
}

