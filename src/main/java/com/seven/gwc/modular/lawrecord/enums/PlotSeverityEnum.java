package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-03-06
 * @description :情节严重类型
 */
@Getter
public enum PlotSeverityEnum implements IEnum<Integer> {

    GENERAL(1,"一般"),
    SERIOUS(2,"严重/较重"),
    ESPECIALLY_SERIOUS(2,"特别严重");


    Integer code;
    @JsonValue
    String message;

    PlotSeverityEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PlotSeverityEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (PlotSeverityEnum ms : PlotSeverityEnum.values()) {
                if (ms.getCode().equals(value)) {
                    return ms;
                }
            }
            return null;
        }
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

}

