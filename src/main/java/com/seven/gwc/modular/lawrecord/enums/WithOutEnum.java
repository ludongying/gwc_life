package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :案件类型
 */
@Getter
public enum WithOutEnum implements IEnum<Integer> {

    WITH(1,"有"),

    WITHOUT(0,"无");


    Integer code;
    @JsonValue
    String message;

    WithOutEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static WithOutEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (WithOutEnum ms : WithOutEnum.values()) {
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

