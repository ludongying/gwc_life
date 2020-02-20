package com.seven.gwc.modular.ship_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.seven.gwc.modular.ship_info.dao.ShipMapper;
import com.seven.gwc.modular.ship_info.service.ShipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description : 执法船信息管理服务实现类
 *
 * @author : LDY
 * @date : 2020-02-12
 */
@Service
public class ShipServiceImpl extends ServiceImpl<ShipMapper, ShipEntity> implements ShipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShipMapper shipMapper;

    /**
     * 执法船信息管理查询列表
     */
    @Override
    public List<ShipEntity> selectShip(ShipEntity shipEntity){
//        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.<ShipEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(shipName),ShipEntity::getName,shipName);
//        return shipMapper.selectList(lambdaQuery);
        return shipMapper.ShipEntityList(shipEntity);
    }

    @Override
    public boolean add(ShipEntity ship) {
        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(ShipEntity::getShipCode,ship.getShipCode());
        ShipEntity shipEntity = shipMapper.selectOne(lambdaQuery);
        if(shipEntity != null)
        {
            return false;
        }
        shipMapper.insert(ship);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(ShipEntity ship) {
        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(ShipEntity::getShipCode,ship.getShipCode()).ne(ShipEntity::getId,ship.getId());
        ShipEntity shipEntity = shipMapper.selectOne(lambdaQuery);
        if(shipEntity != null){
            return false;
        }
        shipMapper.updateById(ship);
        return true;
    }

    @Override
    public boolean delete(String id) {
        shipMapper.deleteById(id);
        return true;
    }
}
