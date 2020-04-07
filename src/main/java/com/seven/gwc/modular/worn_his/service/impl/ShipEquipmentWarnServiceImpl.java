package com.seven.gwc.modular.worn_his.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.worn_his.entity.ShipEquipmentWarnEntity;
import com.seven.gwc.modular.worn_his.dao.ShipEquipmentWarnMapper;
import com.seven.gwc.modular.worn_his.service.ShipEquipmentWarnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 历史报警服务实现类
 *
 * @author : 李晓晖
 * @date : 2020-03-13
 */
@Service
public class ShipEquipmentWarnServiceImpl extends ServiceImpl<ShipEquipmentWarnMapper, ShipEquipmentWarnEntity> implements ShipEquipmentWarnService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShipEquipmentWarnMapper shipEquipmentWarnMapper;

    @Override
    public List<ShipEquipmentWarnEntity> selectShipEquipmentWarn(String shipEquipmentWarnName){
        LambdaQueryWrapper<ShipEquipmentWarnEntity> lambdaQuery = Wrappers.<ShipEquipmentWarnEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(shipEquipmentWarnName),ShipEquipmentWarnEntity::getId,shipEquipmentWarnName);
        return shipEquipmentWarnMapper.selectList(lambdaQuery);
    }

    @Override
    public void addShipEquipmentWarn(ShipEquipmentWarnEntity shipEquipmentWarn, ShiroUser user) {
        shipEquipmentWarnMapper.insert(shipEquipmentWarn);
    }

    @Override
    public void deleteShipEquipmentWarn(String shipEquipmentWarnId, ShiroUser user) {
        shipEquipmentWarnMapper.deleteById(shipEquipmentWarnId);
    }

    @Override
    public void editShipEquipmentWarn(ShipEquipmentWarnEntity shipEquipmentWarn, ShiroUser user) {
        shipEquipmentWarnMapper.updateById(shipEquipmentWarn);
    }

}
