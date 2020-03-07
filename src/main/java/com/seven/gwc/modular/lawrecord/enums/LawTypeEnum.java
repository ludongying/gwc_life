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
public enum LawTypeEnum implements IEnum<Integer> {

    PRODUCE(1,"生产"),

    SAFE(2,"安全");


    Integer code;
    @JsonValue
    String message;

    LawTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static LawTypeEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (LawTypeEnum ms : LawTypeEnum.values()) {
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

