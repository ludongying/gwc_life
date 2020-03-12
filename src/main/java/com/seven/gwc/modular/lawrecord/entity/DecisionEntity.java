package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * description : 决定实体
 *
 * @author : ZZL
 * @date : 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_decision")
public class DecisionEntity extends GwcBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 当事人类型（枚举） */
    private Integer punishPersonType;

    /** 单位名称/船名号 */
    private String punishCompanyName;

    /** 单位法定代表人/处罚人名称*/
    private String punishPersonName;
    /** 年龄*/
    private Integer punishAge;
    /** 性别*/
    private Integer punishSex;

    /** 身份证号 */
    private String punishIdentityCard;

    /** 系电话 */
    private String punishTel;

    /** 地址 */
    private String punishAddr;

    /** 情节严重性 */
    private Integer severity;

    /** 证据是否有物价局价格指导 */
    private Integer caseBureauPrice;

    /** 证据是否有禁渔期通告(农业部) */
    private Integer caseNotification;

    /** 罚款 */
    private String fine;

    /** 资源补偿费 */
    private String resourceCompensation;

    /** 处罚文表述 */
    private String description;

    /** 是否有暴力抗法 */
    private Integer caseViolenceLaw;

}
