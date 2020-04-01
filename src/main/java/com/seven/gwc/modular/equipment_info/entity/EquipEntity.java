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
 * description : 设备信息实体
 *
 * @author : LDY
 * @date : 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_equip")
public class EquipEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 设备名称 */
    private String name;

    /** 设备类型 */
    private String type;

    /** 设备类型 描述*/
    @TableField(exist = false)
    private String typeDesp;


    /** 设备型号 */
    private String specification;

    /** 生产厂商 */
    private String producer;

    /** 出厂编码 */
    private String serialNumber;

    /** 图纸编码 */
    private String drawingNumber;

    /** 出厂日期 */
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date produceDate;

    /** 保养周期（时间） */
    private Integer maintainCycle;

    /** 设备状态*/
    private String state;

    /** 设备状态：描述*/
    @TableField(exist = false)
    private String stateDesp;

    /** 备注 */
    private String remark;

    /** 最近保养时间 */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastMaintenanceDate;

    /** 设备信息 */
    private String info;

    /** 维保提前提醒天数*/
    private Integer windowPhase;

    /** 维保提醒类型*/
    private Integer stateWarn;

}
