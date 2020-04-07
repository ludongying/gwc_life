package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :处罚人类型
 */
@Getter
public enum PunishmentTypeEnum implements IEnum<Integer> {

    LEGAL_PERSON(1,"法人"),

    CITIZEN(2,"公民");


    Integer code;
    @JsonValue
    String message;

    PunishmentTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PunishmentTypeEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (PunishmentTypeEnum ms : PunishmentTypeEnum.values()) {
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

