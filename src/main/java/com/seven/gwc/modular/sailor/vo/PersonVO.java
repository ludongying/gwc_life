package com.seven.gwc.modular.sailor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "执法人员VO")
public class PersonVO implements Serializable {
    @ApiModelProperty(value = "执法人员ID")
    private String id;
    @ApiModelProperty(value = "执法人员")
    private String personName;
    @ApiModelProperty(value = "执法证号")
    private String lawCode;

}
