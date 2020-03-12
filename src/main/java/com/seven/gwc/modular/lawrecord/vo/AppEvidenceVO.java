package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "证据")
public class AppEvidenceVO {
    /** 执法记录id */
    @ApiModelProperty(value = "记录id")
    private String recordId;

    /** 内容 */
    @ApiModelProperty(value = "内容")
    private String evidenceContent;

    /** 日期 */
    @ApiModelProperty(value = "日期")
    private Date evidenceDate;

    /** 路径 */
    @ApiModelProperty(value = "路径")
    private String evidenceFilePath;
}
