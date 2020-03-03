package com.seven.gwc.modular.address_book.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "好友列表对象")
public class FriendListVO {
    @ApiModelProperty(value = "用户id")
    private String personalId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "姓名首字母")
    private String initials;
}
