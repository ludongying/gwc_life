package com.seven.gwc.modular.lawrecord.entity;

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
    private Integer shipRealType;
    /** 核定作业类型 */
    private Integer shipRatedType;
    /** 实际主机功率 */
    private Double shipRealPower;
    /** 实际主机功率单位 */
    private Integer shipRealPowerUnit;
    /** 核定主机功率 */
    private Double shipRatedPower;
    /** 核定主机功率单位 */
    private Integer shipRatedPowerUnit;
    /** 船上信息 */
    private String shipInfo;
    /** 出海时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date shipOutDate;
    /** 出港时间 */
    private String shipOutPort;
    /** 船上鱼货物数量 */
    private String shipGoodsCount;
    /** 市场价值 */
    private String shipGoodsValue;
    /** 本行次产生次数 */
    private Integer shipGenerateCount;
    /** 到达作业鱼区时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date shipFishAreaDate;


}
