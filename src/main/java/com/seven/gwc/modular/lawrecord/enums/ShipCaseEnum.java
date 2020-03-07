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
public enum ShipCaseEnum implements IEnum<Integer> {

    CORRECT(1,"正确"),
    ERROR(2,"错误"),
    NOT_SCRIBBLED(3,"未涂写");


    Integer code;
    @JsonValue
    String message;

    ShipCaseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ShipCaseEnum findByCode(Integer value) {
        if (value == null) {
            return null;
    } else {
        for (ShipCaseEnum ms : ShipCaseEnum.values()) {
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

