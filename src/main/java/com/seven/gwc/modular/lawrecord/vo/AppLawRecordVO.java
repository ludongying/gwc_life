package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "笔录-安全")
public class AppLawRecordVO {

    /** 文书地址 *//*
    @ApiModelProperty(value = "文书地址")
    private String writFilePath;

    *//** 文书名称 *//*
    @ApiModelProperty(value = "文书名称")
    private String writFileName;*/

    /** 主案由 */
    @ApiModelProperty(value = "主案由")
    private Integer mainReason;

    /** 副案由 */
    @ApiModelProperty(value = "副案由")
    private String secondReason;
}
