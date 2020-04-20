package com.seven.gwc.modular.munition.munitionEnum;

import lombok.Getter;

/**
 * @author : GD
 * @date : 2019/8/1 15:15
 */
@Getter
public enum MunitionInOutStatesEnum {

    /**
     * 公用状态
     */
    SAVE("0", "保存"),
    SUBMIT("1", "提交"),
    MUNITION_IN_OUT_OK("2", "已成功"),
    MUNITION_IN_OUT_REFUSED("3", "已驳回"),
//    MUNITION_OUT__OK("2", "出库通过"),
//    MUNITION_OUT_REFUSED("3", "出库不通过"),
    APPROVE("4", "审批通过"),
    REFUSED("5", "不通过");

    String code;
    String message;

    MunitionInOutStatesEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
