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
    APPROVE("2", "审批通过"),
    REFUSED("3", "不通过");

    String code;
    String message;

    MunitionInStatesEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
