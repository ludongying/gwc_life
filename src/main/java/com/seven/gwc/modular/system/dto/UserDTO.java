package com.seven.gwc.modular.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="User响应对象", description="用户")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键id")
    private Long id;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "用户名字")
    private String name;
    @ApiModelProperty(value = "用户电话")
    private String phone;
    @ApiModelProperty(value = "用户部门信息")
    private DeptDTO dept;
}
