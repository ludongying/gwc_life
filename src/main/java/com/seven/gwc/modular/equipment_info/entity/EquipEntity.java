package com.seven.gwc.modular.equipment_info.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
public class EquipEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    /** 设备名称 */
    private String name;

    /** 设备类型 */
    private String type;

    /** 设备型号 */
    private String specification;

    /** 生产厂商 */
    private String producer;

    /** 出厂编码 */
    private String serialNumber;

    /** 图纸编码 */
    private String drawingNumber;

    /** 出厂日期 */
    private Date produceDate;

    /** 保养周期（时间） */
    private Integer maintainCycle;

    /** 设备状态：使用/维修/保养 */
    private Integer state;

    /** 备注 */
    private String remark;

    /** 最近保养时间 */
    private String lastMaintenanceDate;

    /** 设备信息 */
    private String info;

}
