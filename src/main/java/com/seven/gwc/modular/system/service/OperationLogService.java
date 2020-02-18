package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.system.entity.OperationLogEntity;

import java.util.List;

/**
 * description : 操作日志服务类
 *
 * @author : GD
 * @date : 2019-12-18
 */

public interface OperationLogService extends IService<OperationLogEntity> {

    List<OperationLogEntity> selectOperationLog(String operationLogName, String message, String beginTime, String endTime);
}
