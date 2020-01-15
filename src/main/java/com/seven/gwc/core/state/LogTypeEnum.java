package com.seven.gwc.core.state;

import lombok.Getter;

/**
 * @author : SHQ
 * @date : 2019-10-11
 */
@Getter
public enum LogTypeEnum {
    /**
     * 日志类型
     */
    LOGIN("登录日志"),
    LOGIN_FAIL("登录失败日志"),
    EXIT("退出日志"),
    EXCEPTION("异常日志"),
    BUSSINESS("业务日志");

    String message;

    LogTypeEnum(String message) {
        this.message = message;
    }

}
