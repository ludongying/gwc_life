package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "电子档案列表")
public class ElectronicFileListVO {

    @ApiModelProperty(value = "案件编号")
    private String lawCaseFineCode;  //AgencyEntity

    @ApiModelProperty(value = "案件类型")
    private String lawTypeName;    //lawType

    @ApiModelProperty(value = "案件归档时间")
    private String finishDate;  //AgencyEntity

    @ApiModelProperty(value = "被执法船")
    private String shipName;  //InquireEntity

    @ApiModelProperty(value = "被执法责任人")
    private String shipOwner;  //InquireEntity

    @ApiModelProperty(value = "被执法人电话")
    private String investigateTel;  //InquireEntity

    @ApiModelProperty(value = "案件来源")
    private String lawCaseSource;  //AgencyEntity
}
