package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.modular.lawrecord.enums.PlotSeverityEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-03-27
 * @description :情节严重性
 */
@Data
public class PlotSeverityDTO implements Serializable {

    private Integer code;
    private String message;
    private String content;

    public PlotSeverityDTO (PlotSeverityEnum plotSeverityEnum){
         this.code=plotSeverityEnum.getCode();
         this.message=plotSeverityEnum.getMessage();
    }

}
