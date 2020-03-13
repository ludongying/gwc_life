package com.seven.gwc.modular.equipment_info.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
public class EquipMaintainEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    /** 设备编码 */
    private String equipId;

    /** 问题类型 */
    private Integer problemType;

    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 备注 */
    private String remark;

}
