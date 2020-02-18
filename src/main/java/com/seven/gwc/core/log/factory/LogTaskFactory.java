package com.seven.gwc.core.log.factory;

import com.seven.gwc.core.log.LogManager;
import com.seven.gwc.core.state.LogSucceedEnum;
import com.seven.gwc.core.state.LogTypeEnum;
import com.seven.gwc.core.util.SpringContextUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.OperationLogMapper;
import com.seven.gwc.modular.system.entity.OperationLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    private static OperationLogMapper operationLogMapper = SpringContextUtil.getBean(OperationLogMapper.class);


    public static TimerTask bussinessLog(final Long userId, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLogEntity operationLog = LogFactory.createOperationLog(
                        LogTypeEnum.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogSucceedEnum.SUCCESS);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Long userId, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = ToolUtil.getExceptionMsg(exception);
                OperationLogEntity operationLog = LogFactory.createOperationLog(
                        LogTypeEnum.EXCEPTION, userId, "", null, null, msg, LogSucceedEnum.FAIL);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
