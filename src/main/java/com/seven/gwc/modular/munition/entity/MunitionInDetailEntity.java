package com.seven.gwc.modular.munition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description : 物资入库详情实体
 *
 * @author : LDY
 * @date : 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_munition_inandout_detail")
public class MunitionInDetailEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 编码 */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 物资编码 */
    private String munitionMainId;

    /** 物资编码 */
    private String munitionId;

//    /** 物资名称 */
//    private String munitionName;

    /** 仓库编码 */
    private String depotId;

//    /** 仓库名称 */
//    private String depotName;

//    /** 每件数量 */
//    private Integer specQuantity;

    /** 总数量 */
    private Integer totalNum;

    /** 单位 */
    private String unit;


}
