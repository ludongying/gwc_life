package com.seven.gwc.modular.equipment_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import com.seven.gwc.modular.equipment_info.dao.EquipMapper;
import com.seven.gwc.modular.equipment_info.service.EquipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 设备信息服务实现类
 *
 * @author : LDY
 * @date : 2020-03-09
 */
@Service
public class EquipServiceImpl extends ServiceImpl<EquipMapper, EquipEntity> implements EquipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EquipMapper equipMapper;

    @Override
    public List<EquipEntity> selectEquip(String equipName){
        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.<EquipEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(equipName),EquipEntity::getName,equipName);
        return equipMapper.selectList(lambdaQuery);
    }

    @Override
    public void addEquip(EquipEntity equip, ShiroUser user) {
        equipMapper.insert(equip);
    }

    @Override
    public void deleteEquip(String equipId, ShiroUser user) {
        equipMapper.deleteById(equipId);
    }

    @Override
    public void editEquip(EquipEntity equip, ShiroUser user) {
        equipMapper.updateById(equip);
    }

}
