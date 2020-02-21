package com.ajar.springbootshiro.service;

import com.ajar.springbootshiro.from.RoleFrom;
import com.ajar.springbootshiro.vo.Result;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Result saveRole(RoleFrom roleFrom);

    Result selectRoleList(String name, Pageable pageable);

    Result selectRoleDetail(Integer id);

    Result updateRole(RoleFrom roleFrom);

    Result deleteRole(Integer id);
}
