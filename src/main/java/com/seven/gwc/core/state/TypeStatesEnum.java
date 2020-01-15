package com.seven.gwc.core.state;

import lombok.Getter;

/**
 * @author : GD
 * @date : 2019/8/1 15:15
 */
@Getter
public enum TypeStatesEnum {

    /**
     * 公用状态
     */
    OK("ENABLE", "启用"),
    PROHIBIT("PROHIBIT", "禁用"),
    FREEZED("LOCKED", "冻结"),
    DELETED("DELETED", "被删除");

    String code;
    String message;

    TypeStatesEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
