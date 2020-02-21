package com.ajar.springbootshiro.service;

import com.ajar.springbootshiro.from.UserFrom;
import com.ajar.springbootshiro.model.User;
import com.ajar.springbootshiro.vo.Result;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

@Transactional
public interface UserService {
    User findByAccount(String account);

    Result saveUser(UserFrom userFrom);

    Result selectUserList(String name, Pageable pageable);

    Result selectUserDetail(Integer id);

    Result updateUser(UserFrom userFrom);

    Result delectUser(Integer id);

}
