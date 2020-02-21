package com.ajar.springbootshiro.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 产品类型
 *
 */
@Data
@Entity
@Table(name = "brand")
@DynamicInsert
@DynamicUpdate
public class Brand {
    /**
     * 产主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 产品类型
     */
    @Column(name = "brand_name", length = 64)
    private String brandName;

}
