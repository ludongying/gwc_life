package com.seven.gwc.modular.ship.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.ship.entity.ShipWorkPlanEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 工作计划服务类
 *
 * @author : 李晓晖
 * @date : 2020-02-27
 */

public interface ShipWorkPlanService extends IService<ShipWorkPlanEntity> {

    /**
     * 工作计划查询列表
     *
     * @param shipWorkPlanName 名称
     * @return List<工作计划服务对象>
     */
    List<ShipWorkPlanEntity> selectShipWorkPlan(String shipWorkPlanName);

    /**
     * 工作计划新建
     *
     * @param shipWorkPlan 实体对象
     * @param user 当前用户
     */
    void addShipWorkPlan(ShipWorkPlanEntity shipWorkPlan, ShiroUser user);

    /**
     * 工作计划删除
     *
     * @param shipWorkPlanId 唯一标识
     * @param user 当前用户
     */
    void deleteShipWorkPlan(String shipWorkPlanId, ShiroUser user);

    /**
     * 工作计划编辑
     *
     * @param shipWorkPlan 实体对象
     * @param user 当前用户
     */
    void editShipWorkPlan(ShipWorkPlanEntity shipWorkPlan, ShiroUser user);

}
