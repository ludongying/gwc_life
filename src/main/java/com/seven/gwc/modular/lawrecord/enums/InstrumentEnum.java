package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :文书模板
 */
@Getter
public enum InstrumentEnum implements IEnum<Integer> {

    INSTRUMENT_01(1,"封面_法人.docx", 1,Arrays.asList(2),Arrays.asList(1)),
    INSTRUMENT_02(1,"封面_目录.docx", 1,Arrays.asList(2),Arrays.asList(1,2));

    Integer code;
    /**
     * 模板名称
     */
    @JsonValue
    String message;
    /**
     *  文书生成方式 1：系统根据录入生成 2需要根据录入生成并且需要手工填写 3 需要手工填写
     */
    Integer generate;

    /**
     * 案件类型 1 生产   2安全
     */
    List<Integer> law;

    /**
     * 决定类型 1 法人  2 公民
     */
    List<Integer> decision;



    InstrumentEnum(Integer code, String message,Integer generate,List<Integer> law,List<Integer> decision) {
        this.code = code;
        this.message = message;
        this.generate=generate;
        this.law=law;
        this.decision=decision;
    }

    public static InstrumentEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (InstrumentEnum ms : InstrumentEnum.values()) {
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

