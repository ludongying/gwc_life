package com.seven.gwc.modular.munition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description : 物资信息实体
 *
 * @author : LDY
 * @date : 2020-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_munition_info")
public class MunitionInfoEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 编码 */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 物资类型编码 */
    private String typeId;

    /** 物资类型描述 */
    @TableField(exist = false)
    private String typeDesp;

    /** 物资编号 */
    private String code;

    /** 物资名称 */
    private String name;

    /** 物资规格 */
    private String description;

    /** 单位 */
    private String unit;

    /** 备注 */
    private String remark;

    /** 下限预警 默认0 */
    private Integer warnMinCount;

    /** 库存数量 */
    @TableField(exist = false)
    private Integer inventory;

}
