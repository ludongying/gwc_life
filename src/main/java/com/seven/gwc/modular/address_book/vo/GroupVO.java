package com.seven.gwc.modular.address_book.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "群组对象")
public class GroupVO {
    @ApiModelProperty(value = "群组ID")
    private String groupId;
    @ApiModelProperty(value = "群组名")
    private String groupName;
}
