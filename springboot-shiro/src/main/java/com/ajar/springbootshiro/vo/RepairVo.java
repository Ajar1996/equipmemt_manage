package com.ajar.springbootshiro.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @description:修理/报废
 * @author: Ajar
 * @time: 2019/12/8 18:22
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepairVo {
    /**
     * 主键id
     */
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
    private String equipment_id;

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
    private String application_time;

    /**
     * 修理时间
     */
    private String repair_time;

    /**
     * 发出时间
     */
    private String sent_time;

    /**
     * 状态
     * 0：申请状态
     * 1：确认状态
     * 2：修理状态
     * 3：寄回状态
     */
    private String status;

}
