package com.seven.gwc.modular.equipment_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.equipment_info.entity.EquipMaintaindetailEntity;
import com.seven.gwc.modular.equipment_info.dao.EquipMaintaindetailMapper;
import com.seven.gwc.modular.equipment_info.service.EquipMaintaindetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 设备维保详情服务实现类
 *
 * @author : LDY
 * @date : 2020-03-25
 */
@Service
public class EquipMaintaindetailServiceImpl extends ServiceImpl<EquipMaintaindetailMapper, EquipMaintaindetailEntity> implements EquipMaintaindetailService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EquipMaintaindetailMapper equipMaintaindetailMapper;

    @Override
    public List<EquipMaintaindetailEntity> selectEquipMaintaindetail(String equipMaintaindetailName){
        LambdaQueryWrapper<EquipMaintaindetailEntity> lambdaQuery = Wrappers.<EquipMaintaindetailEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(equipMaintaindetailName),EquipMaintaindetailEntity::getId,equipMaintaindetailName);
        return equipMaintaindetailMapper.selectList(lambdaQuery);
    }

    @Override
    public void addEquipMaintaindetail(EquipMaintaindetailEntity equipMaintaindetail, ShiroUser user) {
        equipMaintaindetailMapper.insert(equipMaintaindetail);
    }

    @Override
    public void deleteEquipMaintaindetail(String equipMaintaindetailId, ShiroUser user) {
        equipMaintaindetailMapper.deleteById(equipMaintaindetailId);
    }

    @Override
    public void editEquipMaintaindetail(EquipMaintaindetailEntity equipMaintaindetail, ShiroUser user) {
        equipMaintaindetailMapper.updateById(equipMaintaindetail);
    }

}
