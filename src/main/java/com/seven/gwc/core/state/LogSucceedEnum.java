package com.seven.gwc.core.state;

import lombok.Getter;

/**
 * @author : SHQ
 * @date : 2019-10-11
 */
@Getter
public enum LogSucceedEnum {
    /**
     * 业务是否成功的日志记录
     */
    SUCCESS("成功"),
    FAIL("失败");

    String message;

    LogSucceedEnum(String message) {
        this.message = message;
    }

}
