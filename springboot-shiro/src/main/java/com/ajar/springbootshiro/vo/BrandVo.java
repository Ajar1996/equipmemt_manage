package com.ajar.springbootshiro.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 产品类型
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandVo {
    /**
     * 产主键id
     */
    private Integer id;

    /**
     * 产品类型
     */
    private String brand_name;

}
