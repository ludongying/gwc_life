package com.seven.gwc.modular.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="修改密码对象")
public class UserUpdatePsdVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "旧密码",required = true)
    private String oldPassword;
    @ApiModelProperty(value = "新密码",required = true)
    private String newPassword;
}
