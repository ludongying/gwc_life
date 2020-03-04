package com.seven.gwc.modular.address_book.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "通讯录首字母列表")
public class InitialsVO {
    @ApiModelProperty(value = "姓名首字母")
    private String initial;
    @ApiModelProperty(value = "好友列表对象")
    List<FriendListVO> friendListVOList = new ArrayList<>();
}
