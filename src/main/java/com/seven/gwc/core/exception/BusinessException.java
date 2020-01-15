package com.seven.gwc.core.exception;

import com.seven.gwc.core.state.ErrorEnum;

/**
 * @Author: GD
 * @Description: 异常处理封装类
 * @Date: 2019/8/2 10:37
 */
public class BusinessException extends ServiceException {

    public BusinessException(ErrorEnum errorEnum) {
        super(errorEnum);
    }
}
