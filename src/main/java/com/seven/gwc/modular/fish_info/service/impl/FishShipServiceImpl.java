package com.seven.gwc.modular.fish_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.seven.gwc.modular.fish_info.dao.FishShipMapper;
import com.seven.gwc.modular.fish_info.service.FishShipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 渔船信息服务实现类
 *
 * @author : SHQ
 * @date : 2020-03-02
 */
@Service
public class FishShipServiceImpl extends ServiceImpl<FishShipMapper, FishShipEntity> implements FishShipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FishShipMapper fishShipMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<FishShipEntity> selectFishShip(String code, String phone, String shipType){
        LambdaQueryWrapper<FishShipEntity> lambdaQuery = Wrappers.<FishShipEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(code), FishShipEntity::getCode, code)
                .like(ToolUtil.isNotEmpty(phone), FishShipEntity::getPhone, phone)
                .eq(ToolUtil.isNotEmpty(shipType), FishShipEntity::getShipType, shipType);

        List<FishShipEntity> list = fishShipMapper.selectList(lambdaQuery);

        for (FishShipEntity fishShip : list) {
            if (ToolUtil.isNotEmpty(fishShip.getShipType())) {
                DictEntity shipTypeDict = dictMapper.selectById(fishShip.getShipType());
                fishShip.setShipTypeName(shipTypeDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShip.getAreaType())) {
                DictEntity areaTypeDict = dictMapper.selectById(fishShip.getAreaType());
                fishShip.setAreaTypeName(areaTypeDict.getName());
            }

            if (ToolUtil.isNotEmpty(fishShip.getSeaState())) {
                DictEntity seaStateDict = dictMapper.selectById(fishShip.getSeaState());
                fishShip.setSeaStateName(seaStateDict.getName());
            }

            DictEntity workTypeDict = dictMapper.selectById(fishShip.getWorkType());
            fishShip.setWorkTypeName(workTypeDict.getName());
        }
        return list;
    }

    @Override
    public void addFishShip(FishShipEntity fishShip, ShiroUser user) {
        fishShipMapper.insert(fishShip);
    }

    @Override
    public void deleteFishShip(String fishShipId, ShiroUser user) {
        fishShipMapper.deleteById(fishShipId);
    }

    @Override
    public void editFishShip(FishShipEntity fishShip, ShiroUser user) {
        fishShipMapper.updateById(fishShip);
    }

    @Override
    public FishShipEntity detailFishShip(String fishShipId) {
        FishShipEntity fishShipEntity = fishShipMapper.selectById(fishShipId);
        if (ToolUtil.isNotEmpty(fishShipEntity.getShipType())) {
            DictEntity shipTypeDict = dictMapper.selectById(fishShipEntity.getShipType());
            fishShipEntity.setShipTypeName(shipTypeDict.getName());
        }
        if (ToolUtil.isNotEmpty(fishShipEntity.getAreaType())) {
            DictEntity areaTypeDict = dictMapper.selectById(fishShipEntity.getAreaType());
            fishShipEntity.setAreaTypeName(areaTypeDict.getName());
        }

        if (ToolUtil.isNotEmpty(fishShipEntity.getSeaState())) {
            DictEntity seaStateDict = dictMapper.selectById(fishShipEntity.getSeaState());
            fishShipEntity.setSeaStateName(seaStateDict.getName());
        }

        DictEntity workTypeDict = dictMapper.selectById(fishShipEntity.getWorkType());
        fishShipEntity.setWorkTypeName(workTypeDict.getName());
        return fishShipEntity;
    }

}
