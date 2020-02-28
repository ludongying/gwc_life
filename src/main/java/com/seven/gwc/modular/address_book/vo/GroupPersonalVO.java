package com.seven.gwc.modular.address_book.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "群组成员对象")
public class GroupPersonalVO {
    @ApiModelProperty(value = "用户id")
    private String personalId;
    @ApiModelProperty(value = "群内名称")
    private String personNikeName;
}
