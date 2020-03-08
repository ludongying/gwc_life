package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :生产案由
 */
@Getter
public enum SafeReasonEnum implements IEnum<Integer> {

    /**
     * 未按规定
     */
    DUTY_CREW(1,"配备职务船员"),
    PRODUCTION_EQUIPMENT(2,"配备渔业安全生产设备"),
    SHIP_NAME(3,"刷鞋船名号"),
    HOME_PORT(4,"刷鞋船籍港"),
    SHIP_NAMEPLATE(5,"悬挂船名牌"),
    OTHER(99,"其他");

    Integer code;
    @JsonValue
    String message;

    SafeReasonEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SafeReasonEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (SafeReasonEnum ms : SafeReasonEnum.values()) {
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

