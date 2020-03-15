package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "决定实体-生产")
public class AppDecisionVO {
    /** 当事人类型（枚举） */
    @ApiModelProperty(value = "当事人类型（枚举）")
    private Integer punishPersonType;

    /** 单位名称/船名号 */
    @ApiModelProperty(value = "单位名称/船名号")
    private String punishCompanyName;

    /** 单位法定代表人/处罚人名称*/
    @ApiModelProperty(value = "单位法定代表人/处罚人名称")
    private String punishPersonName;

    /** 年龄*/
    @ApiModelProperty(value = "年龄")
    private Integer punishAge;

    /** 性别*/
    @ApiModelProperty(value = "性别")
    private Integer punishSex;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String punishIdentityCard;

    /** 系电话 */
    @ApiModelProperty(value = "系电话")
    private String punishTel;

    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String punishAddr;

    /** 情节严重性 */
    @ApiModelProperty(value = "情节严重性")
    private Integer severity;

    /** 证据是否有物价局价格指导 */
    @ApiModelProperty(value = "证据是否有物价局价格指导")
    private Integer caseBureauPrice;

    /** 证据是否有禁渔期通告(农业部) */
    @ApiModelProperty(value = "证据是否有禁渔期通告(农业部)")
    private Integer caseNotification;

    /** 罚款 */
    @ApiModelProperty(value = "罚款")
    private String fine;

    /** 资源补偿费 */
    @ApiModelProperty(value = "资源补偿费")
    private String resourceCompensation;

    /** 处罚文表述 */
    @ApiModelProperty(value = "处罚文表述")
    private String description;

    /** 是否有暴力抗法 */
    @ApiModelProperty(value = "是否有暴力抗法")
    private Integer caseViolenceLaw;
}
