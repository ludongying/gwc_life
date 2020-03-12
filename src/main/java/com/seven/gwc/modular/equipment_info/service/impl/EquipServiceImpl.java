package com.seven.gwc.modular.equipment_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.modular.electronic_data.entity.RegulationSafeEntity;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<EquipEntity> selectEquip(String equipName, String equipType){
//        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.<EquipEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(equipName),EquipEntity::getName,equipName);
        List<EquipEntity> list = equipMapper.selectEquipList(equipName, equipType);
        for(EquipEntity equipEntity : list){
            if(ToolUtil.isNotEmpty(equipEntity.getState())){
                DictEntity certificateTypeDict = dictMapper.selectById(equipEntity.getState());
                if(certificateTypeDict!= null){
                    equipEntity.setStateDesp(certificateTypeDict.getName());
                }
            }
        }
        return list;
    }

    @Override
    public EquipEntity selectEquipById(String id) {
        EquipEntity equipEntity = equipMapper.selectById(id);
        DictEntity dictEntity = dictMapper.selectById(equipEntity.getType());
        equipEntity.setTypeDesp(dictEntity.getName());
        return equipEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addEquip(EquipEntity equip, ShiroUser user) {
        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(EquipEntity::getSerialNumber,equip.getSerialNumber());
        EquipEntity equipEntity = equipMapper.selectOne(lambdaQuery);
        if(equipEntity != null)
        {
            return false;
        }
        equip.setCreateDate(new Date());
        equip.setCreatePerson(user.getId());
        equip.setSynFlag(false);
        equip.setDeleteFlag(true);
        equipMapper.insert(equip);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquip(String equipId, ShiroUser user) {
        EquipEntity equip = equipMapper.selectById(equipId);
        if (equip!=null){
            equip.setDeleteFlag(false);
            equip.setSynFlag(false);
            equip.setUpdateDate(new Date());
            equip.setUpdatePerson(user.getId());
        }
        equipMapper.updateById(equip);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editEquip(EquipEntity equip, ShiroUser user) {
        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(EquipEntity::getSerialNumber,equip.getSerialNumber()).ne(EquipEntity::getId,equip.getId());
        EquipEntity equipEntity = equipMapper.selectOne(lambdaQuery);
        if(equipEntity != null){
            return false;
        }
        equip.setUpdateDate(new Date());
        equip.setUpdatePerson(user.getId());
        equipMapper.updateById(equip);
        return true;

    }

}
