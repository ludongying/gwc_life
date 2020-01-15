package com.seven.gwc.config.generator.action.model;

import lombok.Getter;

/**
 * 是否是菜单的枚举
 *
 * @author : GD
 */
@Getter
public enum IsMenu {
    /**
     * 是否为菜单
     */
    YES("Y", "是"),
    NO("N", "不是");

    String code;
    String message;

    IsMenu(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
