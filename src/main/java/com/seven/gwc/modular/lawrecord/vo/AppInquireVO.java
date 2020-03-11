package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "询问笔录-生产")
public class AppInquireVO {
    /** 记录id */
    @ApiModelProperty(value = "记录id")
    private String id;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String investigateName;

    /** 性别(枚举) */
    @ApiModelProperty(value = "性别")
    private Integer investigateSex;

    /** 年龄 */
    @ApiModelProperty(value = "年龄")
    private Integer investigateAge;

    /** 职务 */
    @ApiModelProperty(value = "职务")
    private Integer investigatePosition;

    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    private String investigateTel;

    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String investigateAddr;

    /** 是否携带身份证*/
    @ApiModelProperty(value = "是否携带身份证")
    private Integer identityCase;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String identityCard;

    /** 渔船名称 */
    @ApiModelProperty(value = "渔船名称")
    private String shipName;

    /** 船主姓名 */
    @ApiModelProperty(value = "船主姓名")
    private String shipOwner;

    /** 船上总人数 */
    @ApiModelProperty(value = "船上总人数")
    private Integer shipMember;

    /** 实际作业类型 */
    @ApiModelProperty(value = "实际作业类型")
    private Integer shipRealType;

    /** 核定作业类型 */
    @ApiModelProperty(value = "核定作业类型")
    private Integer shipRatedType;

    /** 实际主机功率 */
    @ApiModelProperty(value = "实际主机功率")
    private Double shipRealPower;

    /** 核定主机功率 */
    @ApiModelProperty(value = "核定主机功率")
    private Double shipRatedPower;

    /** 船上信息 */
    @ApiModelProperty(value = "船上信息")
    private String shipInfo;

    /** 渔船状态 */
    @ApiModelProperty(value = "渔船状态")
    private Integer shipStatus;

    /** 出海时间 */
    @ApiModelProperty(value = "出海时间")
    private Date shipOutDate;

    /** 出港时间 */
    @ApiModelProperty(value = "出港时间")
    private String shipOutPort;

    /** 船上鱼货物数量 */
    @ApiModelProperty(value = "船上鱼货物数量")
    private String shipGoodsCount;

    /** 市场价值 */
    @ApiModelProperty(value = "市场价值")
    private String shipGoodsValue;

    /** 本行次产生次数 */
    @ApiModelProperty(value = "本行次产生次数")
    private Integer shipGenerateCount;

    /** 到达作业鱼区时间 */
    @ApiModelProperty(value = "到达作业鱼区时间")
    private Date shipFishAreaDate;
}
