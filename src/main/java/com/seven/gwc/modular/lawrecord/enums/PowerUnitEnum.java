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
public enum PowerUnitEnum implements IEnum<Integer> {

    HORSEPOWER(2,"马力"),
    KILOWATT(1,"千瓦");


    Integer code;
    @JsonValue
    String message;

    PowerUnitEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PowerUnitEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (PowerUnitEnum ms : PowerUnitEnum.values()) {
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

