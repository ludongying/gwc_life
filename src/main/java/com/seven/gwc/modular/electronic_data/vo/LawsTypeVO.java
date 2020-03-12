package com.seven.gwc.modular.electronic_data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "法律法规类型列表")
public class LawsTypeVO {
    @ApiModelProperty(value = "类型ID")
    private String id;
    @ApiModelProperty(value = "类型Name")
    private String name;
}
