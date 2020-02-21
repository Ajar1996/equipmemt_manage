package com.ajar.springbootshiro.service;

import com.ajar.springbootshiro.from.ResourceFrom;
import com.ajar.springbootshiro.vo.Result;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/9/30 16:45
 */
public interface ResourceService {
    Set<String> selectUserPerms(Integer userId);

    Result saveResource(ResourceFrom resourceFrom);

    Result selectResourceList(String name, Pageable pageable);

    Result updateResource(ResourceFrom resourceFrom);

    Result selectResourceDetail(Integer id);

    Result deleteResource(Integer id);

}
