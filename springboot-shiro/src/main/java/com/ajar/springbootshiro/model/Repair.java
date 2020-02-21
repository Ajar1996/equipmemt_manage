package com.ajar.springbootshiro.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:修理/报废
 * @author: Ajar
 * @time: 2019/12/8 18:22
 */
@Data
@Entity
@Table(name = "repair")
@DynamicInsert
@DynamicUpdate
public class Repair {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请人姓名
     */
    private String name;

    /**
     * 申请人手机
     */
    private String phone;

    /**
     * 申返修地址
     */
    private String address;

    /**
     * 原因
     */

    private String reason;

    /**
     * 设备
     */
    @Column(name = "equipment_id", length = 64)
    private Integer equipmentId;

    /**
     * 紧急程度
     */
    private String level;

    /**
     * 备注
     */
    private String remark;

    /**
     * 申请时间
     */
    @Column(name = "application_time")
    private Date applicationTime;

    /**
     * 修理时间
     */
    @Column(name = "repair_time")
    private Date repairTime;

    /**
     * 发出时间
     */
    @Column(name = "sent_time")
    private Date sentTime;

    /**
     * 状态
     * 0：申请维修
     * 1：申请报废
     * 2：修理状态
     * 3：寄回状态
     * 4：完结状态
     */
    private Integer status;

}
