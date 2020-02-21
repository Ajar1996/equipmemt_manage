package com.ajar.springbootshiro.dao;

import com.ajar.springbootshiro.model.Equipment;
import com.ajar.springbootshiro.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EquipmentDao  extends JpaRepository<Equipment,Integer> {
    Page<Equipment> findAll(Specification<Equipment> sysRoleSpecification, Pageable pageable);

    @Transactional
    void deleteByBrandId(Integer id);
}

