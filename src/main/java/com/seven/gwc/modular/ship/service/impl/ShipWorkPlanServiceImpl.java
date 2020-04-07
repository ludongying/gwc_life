package com.seven.gwc.modular.ship.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.ship.dao.ShipWorkPlanMapper;
import com.seven.gwc.modular.ship.entity.ShipWorkPlanEntity;
import com.seven.gwc.modular.ship.service.ShipWorkPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public JSONArray listPlans(ShipWorkPlanEntity shipWorkPlan) {
        List<ShipWorkPlanEntity> shipWorkPlanEntities = shipWorkPlanMapper.WorkPlanList(shipWorkPlan);
        JSONArray jsonArray = new JSONArray();
        for (ShipWorkPlanEntity shipWorkPlanEntity : shipWorkPlanEntities) {
            JSONObject jsonObject = new JSONObject();
            var ids = shipWorkPlanEntity.getId();
            if (!SysConsts.STR_NULL.equals(ids) && ids != null && ids.length() > 0) {
                for (String id : ids.split(SysConsts.STR_COMMA)) {
                    if (shipWorkPlanEntity.getId().equals(id)) {
                        jsonObject.put("selected", "selected");
                    }
                }
            }
            jsonObject.put("id", shipWorkPlanEntity.getId());
            jsonObject.put("start", DateTimeUtil.parse2String(shipWorkPlanEntity.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
            jsonObject.put("end", DateTimeUtil.parse2String(shipWorkPlanEntity.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
//            jsonObject.put("title", shipWorkPlanEntity.getWorkContent());
            var str = "工作内容："+shipWorkPlanEntity.getWorkContent()+"\r\n"+"参与人员："+shipWorkPlanEntity.getPersonIds();
            jsonObject.put("title", str);
            jsonObject.put("type", shipWorkPlanEntity.getPlanType());
            if (shipWorkPlanEntity.getPlanType() == 0) {
                jsonObject.put("color", "#3987AD");
                jsonObject.put("textColor", "white");
            }
            else if(shipWorkPlanEntity.getPlanType() == 1){
                jsonObject.put("color", "#82AE6F");
                jsonObject.put("textColor", "white");
            }
            else if(shipWorkPlanEntity.getPlanType() == 2){
                jsonObject.put("color", "#D25B47");
                jsonObject.put("textColor", "white");
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
