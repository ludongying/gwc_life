package com.seven.gwc.core.log.factory;


import com.seven.gwc.core.state.LogSucceedEnum;
import com.seven.gwc.core.state.LogTypeEnum;
import com.seven.gwc.modular.system.entity.OperationLogEntity;

import java.util.Date;

/**
 * 日志对象创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogFactory {

    /**
     * 创建操作日志
     */
    public static OperationLogEntity createOperationLog(LogTypeEnum logType, String userId, String bussinessName, String clazzName, String methodName, String msg, LogSucceedEnum succeed) {
        OperationLogEntity operationLog = new OperationLogEntity();
        operationLog.setLogType(logType.getMessage());
        operationLog.setLogName(bussinessName);
        operationLog.setUserId(userId);
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreateTime(new Date());
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMessage(msg);
        return operationLog;
    }

}
