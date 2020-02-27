package com.seven.gwc.modular.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "登录请求对象")
public class GetTokenVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账号", required = true)
    private String Account;
    @ApiModelProperty(value = "用户密码", required = true)
    private String Password;
}
