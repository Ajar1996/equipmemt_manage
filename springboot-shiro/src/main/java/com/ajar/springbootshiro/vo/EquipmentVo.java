package com.ajar.springbootshiro.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 产品
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquipmentVo {
    /**
     * 产品唯一id
     */

    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品类型
     */
    private String brand_id;

}
