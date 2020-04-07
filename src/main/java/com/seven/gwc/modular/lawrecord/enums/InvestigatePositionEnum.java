package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :询问人职位
 */
@Getter
public enum InvestigatePositionEnum implements IEnum<Integer> {

    CAPTAIN(1,"船长"),
    CHIEF_OFFICER(2,"大副"),
    CHIEF_ENGINEER(3,"轮机长"),
    BIG_TUBE_WHEEL(4,"大管轮"),
    CREW(5,"船员"),
    SHIPOWNER(6,"船东");

    Integer code;
    @JsonValue
    String message;

    InvestigatePositionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static InvestigatePositionEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (InvestigatePositionEnum ms : InvestigatePositionEnum.values()) {
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

