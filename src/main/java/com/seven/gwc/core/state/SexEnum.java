package com.seven.gwc.core.state;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-03-02
 * @description :性别
 */
@Getter
public enum SexEnum implements IEnum<Integer> {

    MAN(1,"男"),

    FEMALE(2,"女");

    Integer code;
    @JsonValue
    String message;

    SexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SexEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (SexEnum ms : SexEnum.values()) {
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

