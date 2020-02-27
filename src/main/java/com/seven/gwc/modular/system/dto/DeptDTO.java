package com.seven.gwc.modular.system.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="部门响应对象", description="部门")
public class DeptDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门主键id")
    private Long id;
    @ApiModelProperty(value = "部门全称")
    private String fullName;

}
