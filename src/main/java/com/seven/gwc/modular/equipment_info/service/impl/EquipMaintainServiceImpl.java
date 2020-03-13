package com.seven.gwc.modular.equipment_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.equipment_info.entity.EquipMaintainEntity;
import com.seven.gwc.modular.equipment_info.dao.EquipMaintainMapper;
import com.seven.gwc.modular.equipment_info.service.EquipMaintainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 设备维护服务实现类
 *
 * @author : LDY
 * @date : 2020-03-12
 */
@Service
public class EquipMaintainServiceImpl extends ServiceImpl<EquipMaintainMapper, EquipMaintainEntity> implements EquipMaintainService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EquipMaintainMapper equipMaintainMapper;

    @Override
    public List<EquipMaintainEntity> selectEquipMaintain(String equipMaintainName){
        LambdaQueryWrapper<EquipMaintainEntity> lambdaQuery = Wrappers.<EquipMaintainEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(equipMaintainName),EquipMaintainEntity::getId,equipMaintainName);
        return equipMaintainMapper.selectList(lambdaQuery);
    }

    @Override
    public void addEquipMaintain(EquipMaintainEntity equipMaintain, ShiroUser user) {
        equipMaintainMapper.insert(equipMaintain);
    }

    @Override
    public void deleteEquipMaintain(String equipMaintainId, ShiroUser user) {
        equipMaintainMapper.deleteById(equipMaintainId);
    }

    @Override
    public void editEquipMaintain(EquipMaintainEntity equipMaintain, ShiroUser user) {
        equipMaintainMapper.updateById(equipMaintain);
    }

}
