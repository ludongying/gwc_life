package com.seven.gwc.modular.lawrecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value="办案机关")
public class AppAgencyVO {
    private static final long serialVersionUID = 1L;

    /** 执法船号 */
    @ApiModelProperty(value = "执法船号")
    private String lawShipCode;

    /** 单位简称 */
    @ApiModelProperty(value = "单位简称")
    private String shortName;

    /** 执法单位 */
    @ApiModelProperty(value = "执法单位")
    private String enforcementAgency;

    /** 案件编号 罚*/
    @ApiModelProperty(value = "案件编号 罚")
    private Integer lawCaseFineCode;

    /** 案件编号 */
    @ApiModelProperty(value = "案件编号")
    private Integer lawCaseCode;

    /** 案件来源 */
    @ApiModelProperty(value = "案件来源")
    private Integer lawCaseSource;

    /** 案发经度 */
    @ApiModelProperty(value = "案发经度")
    private String lawCaseLon;

    /** 案发纬度 */
    @ApiModelProperty(value = "案发纬度")
    private String lawCaseLat;

    /** 案发地点 */
    @ApiModelProperty(value = "案发地点")
    private String lawCaseAddr;

    /** 案发时间 */
    @ApiModelProperty(value = "案发时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date lawCaseDate;

    /** 联系人 */
    @ApiModelProperty(value = "联系人")
    private String agencyPerson;

    /** 电话 */
    @ApiModelProperty(value = "电话")
    private String agencyTel;

    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String agencyAddr;

    /** 指定银行 */
    @ApiModelProperty(value = "指定银行")
    private String bank;

    /** 复议机关 */
    @ApiModelProperty(value = "复议机关")
    private String reviewAgency;

    /** 诉讼机关 */
    @ApiModelProperty(value = "诉讼机关")
    private String lawsuitAgency;

    /** 勘探开始时间  */
    @ApiModelProperty(value = "勘探开始时间")
    private Date prospectStartDate;

    /** 勘探结束时间  */
    @ApiModelProperty(value = "勘探结束时间")
    private Date prospectEndDate;

    /** 询问开始时间  */
    @ApiModelProperty(value = "询问开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date inquireStartDate;

    /** 询问结束时间  */
    @ApiModelProperty(value = "询问结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date inquireEndDate;

    /** 电话请示时间  */
    @ApiModelProperty(value = "电话请示时间")
    private Date telApplyDate;

    /** 受案时间  */
    @ApiModelProperty(value = "受案时间")
    private Date acceptDate;

    /** 处理时间  */
    @ApiModelProperty(value = "处理时间")
    private Date dealDate;

    /** 处罚时间  */
    @ApiModelProperty(value = "处罚时间")
    private Date punishDate;

    /** 处决时间  */
    @ApiModelProperty(value = "处决时间")
    private Date decisionDate;

    /** 结案时间  */
    @ApiModelProperty(value = "结案时间")
    private Date finishDate;

    /** 保管年限  */
    @ApiModelProperty(value = "保管年限")
    private Date saveDate;

}
