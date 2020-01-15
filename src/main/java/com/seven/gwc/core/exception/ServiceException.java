package com.seven.gwc.core.exception;

import com.seven.gwc.core.state.ErrorEnum;
import lombok.Data;

/**
 * @Author: GD
 * @Description: 异常处理类
 * @Date: 2019/8/2 10:33
 */
@Data
public class ServiceException extends RuntimeException {
    private ErrorEnum errorEnum;

    public ServiceException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
    }

}
