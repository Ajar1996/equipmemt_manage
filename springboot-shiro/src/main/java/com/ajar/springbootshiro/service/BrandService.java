package com.ajar.springbootshiro.service;

import com.ajar.springbootshiro.from.BrandFrom;
import com.ajar.springbootshiro.vo.Result;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    Result saveBrand(BrandFrom brandFrom);

    Result selectBrandList(String name, Pageable pageable);

    Result updateBrand(BrandFrom brandFrom);

    Result deleteBrand(Integer id);

}
