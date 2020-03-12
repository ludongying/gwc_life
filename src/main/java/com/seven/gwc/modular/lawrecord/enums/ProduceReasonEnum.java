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

    FRIED_FISH(1,"炸鱼","使用炸鱼的方法进行捕捞"),
    POISON_FISH(2,"毒鱼","使用毒鱼的方法进行捕捞"),
    ELECTRIC_FISH(3,"电鱼","使用电鱼的方法进行捕捞"),
    FISHING_AREA(4,"违反禁渔区的规定","违反禁渔区的规定进行捕捞"),

    FISHING_SEASON(5,"违反禁渔期的规定","违反禁渔期的规定进行捕捞"),
    FISHING_GEAR(6,"使用禁用的渔具","使用禁用的渔具进行捕捞"),
    CATCH(7,"使用禁用的捕捞方法","使用禁用的捕捞方法进行捕捞"),
    NETTING(8,"使用最小网目尺寸的网具","使用最小网目尺寸的网具进行捕捞"),

    JOB_TYPE(21,"违反关于作业类型的规定","违反捕捞许可证关于作业类型规定进行捕捞"),
    JOB_YARD(22,"违反关于作业场所的规定","违反捕捞许可证关于作业场所的规定进行捕捞"),
    TIME_LIMIT(23,"违反关于作业时限的规定","违反捕捞许可证关于作业时限规定进行捕捞"),

    FINE(41,"罚款","未依法取得捕捞许可证进行捕捞-罚款"),
    RESOURCE(42,"资源赔偿费","未依法取得捕捞许可证进行捕捞-资源赔偿费"),
    FINE_RESOURCE(43,"罚款+资源赔偿费","未依法取得捕捞许可证进行捕捞-罚款+资源赔偿费"),

    OTHER(99,"其他","其他");


    Integer code;
    @JsonValue
    String message;

    String content;

    ProduceReasonEnum(Integer code, String message,String content) {
        this.code = code;
        this.message = message;
        this.content=content;
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

