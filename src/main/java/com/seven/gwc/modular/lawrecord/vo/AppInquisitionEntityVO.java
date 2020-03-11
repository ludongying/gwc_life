package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "勘验笔录")
public class AppInquisitionEntityVO {
    /** 主键 */
    @ApiModelProperty(value = "记录id")
    private String id;

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

    /** 师傅携带证件 */
    @ApiModelProperty(value = "师傅携带证件")
    private Integer shipCaseCredentials;

    /** 是否有残留渔获物 */
    @ApiModelProperty(value = "是否有残留渔获物")
    private Integer shipCaseFish;

    /** 捕捞许可证编号 */
    @ApiModelProperty(value = "捕捞许可证编号")
    private String shipCredentialsCode;

    /** 船号 */
    @ApiModelProperty(value = "船号")
    private String shipCode;

    /** 作业方式 */
    @ApiModelProperty(value = "作业方式")
    private Integer shipOperatePerson;

    /** 主机功率 */
    @ApiModelProperty(value = "主机功率")
    private String shipPower;

    /** 持证人姓名 */
    @ApiModelProperty(value = "持证人姓名")
    private String shipCredentialsOwner;

    /** 是否携带身份证*/
    @ApiModelProperty(value = "是否携带身份证")
    private Integer shipIdentityCase;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String shipCredentialsOwnerIdentity;

    /** 查获时渔船状态(枚举) */
    @ApiModelProperty(value = "查获时渔船状态")
    private Integer shipStatus;
}
