package com.seven.gwc.modular.lawrecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "枚举")
public class EnumVO {
    @ApiModelProperty(value = "枚举code")
    private Integer id;
    @ApiModelProperty(value = "枚举message")
    private String name;
}
