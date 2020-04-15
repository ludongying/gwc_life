package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * description : 询问笔录-生产实体
 *
 * @author : ZZL
 * @date : 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_inquire")
public class InquireEntity extends InquireBase {

    private static final long serialVersionUID = 1L;

    /** 记录id */
    private String recordId;
    /** 实际作业类型 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer shipRealType;
    /** 核定作业类型 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer shipRatedType;
    /** 实际主机功率 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Double shipRealPower;
    /** 实际主机功率单位 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer shipRealPowerUnit;
    /** 核定主机功率 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Double shipRatedPower;
    /** 核定主机功率单位 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer shipRatedPowerUnit;
    /** 船上信息 携带有哪些生产网具，各有多少*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String shipInfo;
    /** 出海时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date shipOutDate;
    /** 港口 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String shipOutPort;
    /** 船上鱼货物数量  渔获物*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String shipGoodsCount;
    /** 市场价值 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String shipGoodsValue;
    /** 本行次产生次数 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer shipGenerateCount;
    /** 到达作业鱼区时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date shipFishAreaDate;

    /** 询问开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date inquireStartDate;
    /** 询问结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date inquireEndDate;

    @TableField(exist = false)
    private String shipRealTypeName;
    @TableField(exist = false)
    private String shipRatedTypeName;
    @TableField(exist = false)
    private String shipRealPowerUnitName;
    @TableField(exist = false)
    private String shipRatedPowerUnitName;

}
