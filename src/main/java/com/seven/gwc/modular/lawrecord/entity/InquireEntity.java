package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 办案机关实体
 *
 * @author : ZZL
 * @date : 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_inquire")
public class InquireEntity extends GwcBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 记录id */
    private String id;

    /** 姓名 */
    private String investigateName;

    /** 性别(枚举) */
    private Integer investigateSex;

    /** 年龄 */
    private Integer investigateAge;

    /** 职务 */
    private Integer investigatePosition;

    /** 手机号码 */
    private String investigateTel;

    /** 地址 */
    private String investigateAddr;

    /** 是否携带身份证*/
    private Integer identityCase;

    /** 身份证号 */
    private String identityCard;

    /** 渔船名称 */
    private String shipName;

    /** 船主姓名 */
    private String shipOwner;

    /** 船上总人数 */
    private Integer shipMember;

    /** 实际作业类型 */
    private Integer shipRealType;

    /** 核定作业类型 */
    private Integer shipRatedType;

    /** 实际主机功率 */
    private Double shipRealPower;

    /** 核定主机功率 */
    private Double shipRatedPower;

    /** 船上信息 */
    private String shipInfo;
    /** 渔船状态 */
    private Integer shipStatus;

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
