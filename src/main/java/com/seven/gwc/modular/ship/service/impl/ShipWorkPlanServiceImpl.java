package com.seven.gwc.modular.ship.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.ship.entity.ShipWorkPlanEntity;
import com.seven.gwc.modular.ship.dao.ShipWorkPlanMapper;
import com.seven.gwc.modular.ship.service.ShipWorkPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 工作计划服务实现类
 *
 * @author : 李晓晖
 * @date : 2020-02-27
 */
@Service
public class ShipWorkPlanServiceImpl extends ServiceImpl<ShipWorkPlanMapper, ShipWorkPlanEntity> implements ShipWorkPlanService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShipWorkPlanMapper shipWorkPlanMapper;

    @Override
    public List<ShipWorkPlanEntity> selectShipWorkPlan(String shipWorkPlanName){
        LambdaQueryWrapper<ShipWorkPlanEntity> lambdaQuery = Wrappers.<ShipWorkPlanEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(shipWorkPlanName),ShipWorkPlanEntity::getId,shipWorkPlanName);
        return shipWorkPlanMapper.selectList(lambdaQuery);
    }

    @Override
    public void addShipWorkPlan(ShipWorkPlanEntity shipWorkPlan, ShiroUser user) {
        shipWorkPlanMapper.insert(shipWorkPlan);
    }

    @Override
    public void deleteShipWorkPlan(String shipWorkPlanId, ShiroUser user) {
        shipWorkPlanMapper.deleteById(shipWorkPlanId);
    }

    @Override
    public void editShipWorkPlan(ShipWorkPlanEntity shipWorkPlan, ShiroUser user) {
        shipWorkPlanMapper.updateById(shipWorkPlan);
    }

}
