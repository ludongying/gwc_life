package com.seven.gwc.modular.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageVO {

	@ApiModelProperty(value = "当前第几页")
    private Integer pageNum=0;

	@ApiModelProperty(value = "每页多少条")
    private Integer pageSize=10;

}
