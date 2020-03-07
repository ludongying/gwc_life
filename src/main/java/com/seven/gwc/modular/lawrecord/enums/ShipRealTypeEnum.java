package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-03-2
 * @description :实际作业类型
 */
@Getter
public enum ShipRealTypeEnum implements IEnum<Integer> {

    GILL_NET(1,"刺网"),
    PURSE_SEINE(2,"围网"),
    TRAWL(3,"拖网"),
    OPEN_NET(4,"张网"),
    FISHING_TACKLE(5,"钓具"),
    RAKE(6,"耙刺"),
    TRAP(7,"陷阱"),
    CAGE_POT(8,"笼壶"),
    FISHING_GEAR(9,"杂渔具"),
    FISHING_AID(10,"捕捞辅助"),
    PRODUCTS_ACQUISITION(11,"水产品收购"),
    WATER_DIVIDER(12,"分水板"),
    OTHER(13,"其他");


    Integer code;
    @JsonValue
    String message;

    ShipRealTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ShipRealTypeEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (ShipRealTypeEnum ms : ShipRealTypeEnum.values()) {
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

