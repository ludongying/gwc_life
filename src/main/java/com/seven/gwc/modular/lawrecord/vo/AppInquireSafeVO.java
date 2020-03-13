package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "笔录-安全")
public class AppInquireSafeVO {
    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String investigateName;

    /** 性别 */
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

    /** 船名号书写情况 */
    @ApiModelProperty(value = "船名号书写情况")
    private Integer shipCaseName;

    /** 船籍港涂写情况 */
    @ApiModelProperty(value = "船籍港涂写情况")
    private Integer shipCaseRegistry;

    /** 船籍 */
    @ApiModelProperty(value = "船籍")
    private String shipRegistry;

    /** 船名牌悬挂情况 */
    @ApiModelProperty(value = "船名牌悬挂情况")
    private Integer shipCaseCard;

    /** 救生圈数量 */
    @ApiModelProperty(value = "救生圈数量")
    private Integer lifebuoyCount;

    /** 灭火器数量 */
    @ApiModelProperty(value = "灭火器数量")
    private Integer extinguisherCount;

    /** 救生衣数量 */
    @ApiModelProperty(value = "救生衣数量")
    private Integer lifeVestCount;

    /** 救生筏数量 */
    @ApiModelProperty(value = "救生筏数量")
    private Integer lifeRaftCount;

    /** 职务船证数量 */
    @ApiModelProperty(value = "职务船证数量")
    private Integer shipCertificateCount;

    /** 普通船证数量 */
    @ApiModelProperty(value = "普通船证数量")
    private Integer shipCommonCertificateCount;

    /** 船的长度 */
    @ApiModelProperty(value = "船的长度")
    private Double shipLength;

    /** 查获时候捕鱼状态 */
    @ApiModelProperty(value = "查获时候捕鱼状态")
    private Integer shipStatus;
}
