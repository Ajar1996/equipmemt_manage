package com.ajar.springbootshiro.from;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 产品
 *
 */
@Data
public class EquipmentFrom {

    /**
     * 产品唯一id
     */
    private Integer id;

    /**
     * 产品名称
     */
    @Length(max = 32,message = "长度不能超过32位")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 产品类型
     */
    @NotNull(message = "产品类型不能为空")
    private Integer brandId;

}
