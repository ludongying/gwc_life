package com.seven.gwc.modular.munition.entity;

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
 * description : 物资入库实体
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_munition_inandout")
public class MunitionInEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 编码 */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 出入库表单编号 */
    private String code;

    /** 仓库操作类型：入库为0，出库为1（枚举） */
    private Integer actionType;

    /** 物资类型 */
    private String munitionType;

    /** 物资类型描述 */
    @TableField(exist = false)
    private String munitionTypeDesp;

    /** 出入库物资列表详情id */
    private String detailId;

    /** 标题或缘由 */
    private String content;

    /** 备注 */
    private String remark;

    /** 审核状态 */
    private Integer status;

    /** 申请人 */
    private String applyPerson;

    /** 申请人描述 */
    @TableField(exist = false)
    private String applyPersonDesp;

    /** 申请日期 */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;

    /** 出入库人 */
    private String inOutPerson;

    /** 出库人描述 */
    @TableField(exist = false)
    private String inOutPersonDesp;

    /** 出入库日期 */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inOutTime;

}
