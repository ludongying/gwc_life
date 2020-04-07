package com.seven.gwc.modular.ship.dao;

import com.seven.gwc.modular.ship.entity.ShipWorkPlanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 工作计划Mapper 接口
 *
 * @author : 李晓晖
 * @date : 2020-02-27
 */
public interface ShipWorkPlanMapper extends BaseMapper<ShipWorkPlanEntity> {

    List<ShipWorkPlanEntity> WorkPlanList(@Param("WorkPlan") ShipWorkPlanEntity shipWorkPlanEntity);
}
