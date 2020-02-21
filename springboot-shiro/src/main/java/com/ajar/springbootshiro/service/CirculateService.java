package com.ajar.springbootshiro.service;


import com.ajar.springbootshiro.from.CirculateFrom;
import com.ajar.springbootshiro.vo.Result;
import org.springframework.data.domain.Pageable;

import javax.xml.crypto.Data;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/12/11 9:00
 */
public interface CirculateService {
    Result PutInStorage(CirculateFrom circulateFrom);

    Result OutInStorage(CirculateFrom circulateFrom);

    Result CountOfout(Data data1,Data data2,Pageable pageable);

    Result CountOfin(Data data1,Data data2,Pageable pageable);

    Result selectCirculateList(String name, Pageable pageable);

    Result updateCirculate(CirculateFrom circulateFrom);

    Result deleteCirculate(Integer id);

}
