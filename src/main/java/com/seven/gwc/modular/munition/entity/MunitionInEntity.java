package com.seven.gwc.modular.munition.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class MunitionInEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 出入库表单编码 */
    private String id;

    /** 仓库操作类型：入库为0，出库为1（枚举） */
    private Integer actionType;

    /** 物资类型 */
    private String munitionType;

    /** 出入库物资列表详情id */
    private String detailId;

    /** 标题或缘由 */
    private String content;

    /** 备注 */
    private String remark;

    /** 审核状态 */
    private String status;

    /** 申请人 */
    private String applyPerson;

    /** 出入库人 */
    private String inOutPerson;

    /** 出入库日期 */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inOutTime;

}
