package com.seven.gwc.modular.ship_log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.ship_log.dao.SailLogMapper;
import com.seven.gwc.modular.ship_log.entity.SailLogEntity;
import com.seven.gwc.modular.ship_log.service.SailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SailLogServiceImpl extends ServiceImpl<SailLogMapper, SailLogEntity> implements SailLogService {


    @Autowired
    private SailLogMapper sailLogMapper;

    @Override
    public List<SailLogEntity> selectSailLog(SailLogEntity sailLog){
//        LambdaQueryWrapper<SailLogEntity> lambdaQuery = Wrappers.<SailLogEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(sailLogName), SailLogEntity::getId, sailLogName);
        List<SailLogEntity> SailLogs = sailLogMapper.ShiplogEntityList(sailLog);
        return SailLogs;
    }

    @Override
    public void addSailLog(SailLogEntity sailLog, ShiroUser user) {
        sailLogMapper.insert(sailLog);
    }

    @Override
    public void deleteSailLog(String sailLogId, ShiroUser user) {
        sailLogMapper.deleteById(sailLogId);
    }

    @Override
    public void editSailLog(SailLogEntity sailLog, ShiroUser user) {
        sailLogMapper.updateById(sailLog);
    }


}
