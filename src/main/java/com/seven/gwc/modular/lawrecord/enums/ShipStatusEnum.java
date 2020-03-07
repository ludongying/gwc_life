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
public enum ShipStatusEnum implements IEnum<Integer> {

    ANCHOR(1,"抛锚"),
    SAIL(2,"航行"),
    ESCAPE(3,"逃逸"),
    PRODUCE(4,"生产");


    Integer code;
    @JsonValue
    String message;

    ShipStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ShipStatusEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (ShipStatusEnum ms : ShipStatusEnum.values()) {
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

