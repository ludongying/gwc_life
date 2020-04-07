package com.seven.gwc.modular.worn_his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description : 历史报警实体
 *
 * @author : 李晓晖
 * @date : 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_equipment_warn")
public class ShipEquipmentWarnEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 设备id */
    private String equipId;

    /** 告警类型 */
    private Integer warnType;

    /** 报警内容 */
    private String warnDescribe;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

}
