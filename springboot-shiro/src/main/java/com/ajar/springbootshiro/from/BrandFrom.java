package com.ajar.springbootshiro.from;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/12/8 18:36
 */
@Data
public class BrandFrom {

    private Integer id;

    /**
     * 产品类型
     */
    @Length(max = 32,message = "长度不能超过32位")
    @NotEmpty(message = "产品类型名称不能为空")
    private String brandName;
}
