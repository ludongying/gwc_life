package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.OperationLogMapper;
import com.seven.gwc.modular.system.entity.OperationLogEntity;
import com.seven.gwc.modular.system.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * description : 操作日志服务实现类
 *
 * @author : GD
 * @date : 2019-12-18
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLogEntity> implements OperationLogService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OperationLogMapper operationLogMapper;

    /**
     * 操作日志查询列表
     */
    @Override
    public List<OperationLogEntity> selectOperationLog(String operationLogName, String message, String beginTime, String endTime) {
        LambdaQueryWrapper<OperationLogEntity> lambdaQuery = Wrappers.<OperationLogEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(operationLogName), OperationLogEntity::getLogName, operationLogName)
                .like(ToolUtil.isNotEmpty(message),OperationLogEntity::getMessage, message)
                .apply(ToolUtil.isNotEmpty(beginTime), "date_format(create_time,'%Y-%m-%d %H:i:s') >= {0}", beginTime)
                .apply(ToolUtil.isNotEmpty(endTime), "date_format(create_time,'%Y-%m-%d %H:i:s') <= {0}", endTime)
                .orderByDesc(OperationLogEntity::getCreateTime);
        List<OperationLogEntity> operationLogEntities = operationLogMapper.selectList(lambdaQuery);
        for (OperationLogEntity operationLogEntity : operationLogEntities){
            operationLogEntity.setUserName(CacheFactory.me().getUserNameById(operationLogEntity.getUserId()));
        }
        return operationLogEntities;
    }

    @Override
    public boolean saveBatch(Collection<OperationLogEntity> entityList) {
        return false;
    }
}
