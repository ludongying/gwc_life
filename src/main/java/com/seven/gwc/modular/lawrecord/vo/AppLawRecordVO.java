package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "任务列表")
public class AppLawRecordVO {
    /** 记录id */
    @ApiModelProperty(value = "记录id")
    private String id;

    /** 案件编号 */
    @ApiModelProperty(value = "案件编号")
    private String lawCaseCode;

    /** 案件来源 */
    @ApiModelProperty(value = "案件来源")
    private String lawCaseSource;

    /** 案发经度 */
    @ApiModelProperty(value = "案发经度")
    private String lawCaseLon;

    /** 案发纬度 */
    @ApiModelProperty(value = "案发纬度")
    private String lawCaseLat;

    /** 案发时间 */
    @ApiModelProperty(value = "案发时间")
    private Date lawCaseDate;
}
