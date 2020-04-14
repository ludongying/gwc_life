package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "执法人员")
public class AppOperatorVO {
    @ApiModelProperty(value = "执法人员1姓名")
    private String lawPersonId1;

    @ApiModelProperty(value = "执法人员1姓名")
    private String lawPersonName1;

    @ApiModelProperty(value = "执法人员1执法证号")
    private String lawCredentialCode1;

    @ApiModelProperty(value = "执法人员1姓名")
    private String lawPersonId2;

    @ApiModelProperty(value = "执法人员1姓名")
    private String lawPersonName2;

    @ApiModelProperty(value = "执法人员1执法证号")
    private String lawCredentialCode2;
}
