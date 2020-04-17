package com.seven.gwc.modular.munition.munitionEnum;

import lombok.Getter;

/**
 * @author : GD
 * @date : 2019/8/1 15:15
 */
@Getter
public enum MunitionInStatesEnum {

    /**
     * 公用状态
     */
    SAVE("0", "保存"),
    SUBMIT("1", "提交"),
    MUNITION_IN_OK("2", "入库通过"),
    MUNITION_IN_REFUSED("3", "入库不通过"),
    APPROVE("4", "审批通过"),
    REFUSED("5", "不通过");

    String code;
    String message;

    MunitionInStatesEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
