package com.seven.gwc.modular.equipment_info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description : 设备维护实体
 *
 * @author : LDY
 * @date : 2020-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_equip_maintain")
public class EquipMaintainEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 设备编码 */
    private String equipId;

    /** 设备名称 */
    @TableField(exist = false)
    private String equipName;

    /** 设备型号 */
    @TableField(exist = false)
    private String specification;

    /** 问题类型 */
    private String problemType;

    /** 开始时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 备注 */
    private String remark;

    /** 维修人 */
    @TableField(exist = false)
    private String maintainPerson;

    /** 耗损备品备件 */
    @TableField(exist = false)
    private String munition;

    /** 维修内容 */
    @TableField(exist = false)
    private String content;

}
