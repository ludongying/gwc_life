package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description : 案件来源
 */
@Getter
public enum LawCaseSourceEnum implements IEnum<Integer> {

    CHECK(1,"检查发现"),
    MASS_REPORT(2,"群众举报"),
    MEDIA_EXPOSURE(3,"媒体曝光"),
    ASSIGN(4,"上级交办"),
    TRANSFER(5,"其他机关移送"),
    COMMIT(6,"主动投案");


    Integer code;
    @JsonValue
    String message;

    LawCaseSourceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static LawCaseSourceEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (LawCaseSourceEnum ms : LawCaseSourceEnum.values()) {
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

