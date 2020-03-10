package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :船牌悬挂类型
 */
@Getter
public enum ShipCaseCardEnum implements IEnum<Integer> {

    CORRECT(1,"正确"),
    ERROR(2,"错误"),
    UNSUSPENDED(3,"未悬挂");


    Integer code;
    @JsonValue
    String message;

    ShipCaseCardEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ShipCaseCardEnum findByCode(Integer value) {
        if (value == null) {
            return null;
    } else {
        for (ShipCaseCardEnum ms : ShipCaseCardEnum.values()) {
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

