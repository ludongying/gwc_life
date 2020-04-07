package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.modular.lawrecord.enums.ProduceReasonEnum;
import com.seven.gwc.modular.lawrecord.enums.SafeReasonEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "枚举")
@NoArgsConstructor
public class EnumVO {
    @ApiModelProperty(value = "枚举code")
    private Integer id;
    @ApiModelProperty(value = "枚举message")
    private String name;

    public EnumVO(ProduceReasonEnum reason){
       this.id=reason.getCode();
       this.name=reason.getMessage();
    }
    public EnumVO(SafeReasonEnum reason){
        this.id=reason.getCode();
        this.name=reason.getMessage();
    }

}
