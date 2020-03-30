package com.seven.gwc.modular.ship_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.ship_info.dao.ShipMapper;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.seven.gwc.modular.ship_info.service.ShipService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private FileManager fileManager;
    @Autowired
    private DictMapper dictMapper;

    /**
     * 执法船信息管理查询列表
     */
    @Override
//    @DataScope(deptAlias = "d", userAlias = "u")
    public List<ShipEntity> selectShip(ShipEntity shipEntity) {
//        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.<ShipEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(shipName),ShipEntity::getName,shipName);
//        return shipMapper.selectList(lambdaQuery);
        List<ShipEntity> ships = shipMapper.ShipEntityList(shipEntity);
        for (ShipEntity ship : ships) {
            if (ToolUtil.isNotEmpty(ship.getNationality())) {
                DictEntity nationDict = dictMapper.selectById(ship.getNationality());
                if (nationDict != null) {
                   ship.setNationalityDesp(nationDict.getName());
                }
            }
            if (ToolUtil.isNotEmpty(ship.getType())) {
                DictEntity typeDict = dictMapper.selectById(ship.getType());
                if (typeDict != null) {
                    ship.setTypeDesp(typeDict.getName());
                }
            }
        }
        return ships;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(ShipEntity ship, ShiroUser user) {
        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(ShipEntity::getShipCode, ship.getShipCode()).eq(ShipEntity::getDeleteFlag, 1);
        ShipEntity shipEntity = shipMapper.selectOne(lambdaQuery);
        if (shipEntity != null) {
            return false;
        }
        ship.setSynFlag(false);
        ship.setDeleteFlag(true);
        ship.setCreateDate(new Date());
        ship.setCreatePerson(user.getId());
        shipMapper.insert(ship);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(ShipEntity ship, ShiroUser user) {
        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(ShipEntity::getShipCode, ship.getShipCode()).eq(ShipEntity::getDeleteFlag, 1).ne(ShipEntity::getId, ship.getId());
        ShipEntity shipEntity = shipMapper.selectOne(lambdaQuery);
        if (shipEntity != null) {
            return false;
        }
        ship.setUpdateDate(new Date());
        ship.setUpdatePerson(user.getId());
        shipMapper.updateById(ship);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String id, ShiroUser user) {
        ShipEntity shipEntity = shipMapper.selectById(id);
        if (shipEntity != null) {
            shipEntity.setDeleteFlag(false);
            shipEntity.setSynFlag(false);
            shipEntity.setUpdateDate(new Date());
            shipEntity.setUpdatePerson(user.getId());
        }
        shipMapper.updateById(shipEntity);
//        shipMapper.deleteById(id);
        return true;
    }

    @Override
    public List<ShipEntity> listShips(String id) {
        LambdaQueryWrapper<ShipEntity> lambdaQueryShip = Wrappers.<ShipEntity>lambdaQuery();
//        lambdaQueryShip.eq(ShipEntity::getId, id);
        List<ShipEntity> shipEntities = shipMapper.selectList(lambdaQueryShip);
        //List<ShipEntity> shipEntities = shipMapper.ShipEntityList(new ShipEntity());
        return shipEntities;
    }

}
