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
public enum ProduceReasonEnum implements IEnum<Integer> {

    FRIED_FISH(1,"炸鱼"),
    POISON_FISH(2,"毒鱼"),
    ELECTRIC_FISH(3,"电鱼"),
    FISHING_AREA(4,"违反禁渔区的规定"),

    FISHING_SEASON(5,"违反禁渔期的规定"),
    FISHING_GEAR(6,"使用禁用的渔具"),
    CATCH(7,"使用禁用的捕捞方法"),
    NETTING(8,"使用最小网目尺寸的网具"),

    JOB_TYPE(21,"违反关于作业类型的规定"),
    JOB_YARD(22,"违反关于作业场的规定"),
    TIME_LIMIT(23,"违反关于作业时限的规定"),

    FINE(41,"罚款"),
    RESOURCE(42,"资源补偿费"),
    FINE_RESOURCE(43,"罚款+资源补偿费"),

    OTHER(99,"其他");


    Integer code;
    @JsonValue
    String message;

    ProduceReasonEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ProduceReasonEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (ProduceReasonEnum ms : ProduceReasonEnum.values()) {
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

