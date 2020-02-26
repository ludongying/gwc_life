package com.seven.gwc.modular.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="修改用户信息")
public class UserUpdateVO implements Serializable {

    @ApiModelProperty(value = "用戶姓名",required = false)
    private String userName;
    @ApiModelProperty(value = "用户手机号",required = false)
    private String phone;
}
