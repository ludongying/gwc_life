package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :执法记录状态
 */
@Getter
public enum RecordStatusEnum implements IEnum<Integer> {

    OPEN_CASE(50,"未结案"),
    INVALID (80,"作废"),
    FINISH(90,"结案");


    Integer code;
    @JsonValue
    String message;

    RecordStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RecordStatusEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (RecordStatusEnum ms : RecordStatusEnum.values()) {
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

