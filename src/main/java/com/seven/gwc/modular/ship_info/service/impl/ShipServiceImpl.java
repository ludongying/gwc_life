package com.seven.gwc.modular.ship_info.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.modular.lawrecord.data.file.FileData;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.ship_info.entity.CertificateShipEntity;
import com.seven.gwc.modular.system.entity.PositionEntity;
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

    /**
     * 执法船信息管理查询列表
     */
    @Override
    public List<ShipEntity> selectShip(ShipEntity shipEntity) {
//        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.<ShipEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(shipName),ShipEntity::getName,shipName);
//        return shipMapper.selectList(lambdaQuery);
        List<ShipEntity> ships = shipMapper.ShipEntityList(shipEntity);
        for (ShipEntity ship : ships) {
            if (ToolUtil.isNotEmpty(ship.getImageFilePath())) {
                 List<FileData> files = fileManager.listFile(ship.getImageFilePath());
                 String urls = "";
                 for(FileData fileData: files){
                     if(urls.isEmpty()){
                         urls = fileData.getUrl();
                     }else{
                         urls = urls + FileUtils.file_2_file_sep + fileData.getUrl();
                     }
                 }
                ship.setImageFilePath(urls);
            }
        }
        return ships;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(ShipEntity ship, ShiroUser user) {
        LambdaQueryWrapper<ShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(ShipEntity::getShipCode,ship.getShipCode()).eq(ShipEntity::getDeleteFlag,1);
        ShipEntity shipEntity = shipMapper.selectOne(lambdaQuery);
        if(shipEntity != null)
        {
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
        lambdaQuery.eq(ShipEntity::getShipCode,ship.getShipCode()).eq(ShipEntity::getDeleteFlag,1).ne(ShipEntity::getId,ship.getId());
        ShipEntity shipEntity = shipMapper.selectOne(lambdaQuery);
        if(shipEntity != null){
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

    @Override
    public ShipEntity getShipById(String id) {
        ShipEntity ship = shipMapper.selectById(id);
        if (ToolUtil.isNotEmpty(ship.getImageFilePath())) {
            List<FileData> files = fileManager.listFile(ship.getImageFilePath());
            String urls = "";
            for(FileData fileData: files){
                urls += fileData.getUrl() + FileUtils.file_2_file_sep;
            }
            ship.setImageFilePath(urls);
        }
        return ship;
    }
}
