package com.ajar.springbootshiro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 产品
 *
 */
@Data
@Entity
@Table(name = "equipment")
@DynamicInsert
@DynamicUpdate
public class Equipment {
    /**
     * 产品唯一id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品类型
     */
    @Column(name = "brand_id", length = 64)
    private Integer brandId;

}
