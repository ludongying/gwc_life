package com.seven.gwc.modular.equipment_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.equipment_info.dao.EquipMaintainMapper;
import com.seven.gwc.modular.equipment_info.dao.EquipMaintaindetailMapper;
import com.seven.gwc.modular.equipment_info.dao.EquipMapper;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import com.seven.gwc.modular.equipment_info.entity.EquipMaintainEntity;
import com.seven.gwc.modular.equipment_info.entity.EquipMaintaindetailEntity;
import com.seven.gwc.modular.equipment_info.service.EquipMaintainService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private EquipMaintaindetailMapper equipMaintaindetailMapper;
    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<EquipMaintainEntity> selectEquipMaintain(String equipMaintainName){
        LambdaQueryWrapper<EquipMaintainEntity> lambdaQuery = Wrappers.<EquipMaintainEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(equipMaintainName),EquipMaintainEntity::getId,equipMaintainName);
        List<EquipMaintainEntity> equipMaintainEntityList = equipMaintainMapper.selectList(lambdaQuery);
        for(EquipMaintainEntity equipMaintainEntity: equipMaintainEntityList){
            if (ToolUtil.isNotEmpty(equipMaintainEntity.getEquipId())) {
                EquipEntity equipEntity = equipMapper.selectById(equipMaintainEntity.getEquipId());
                if (equipEntity != null) {
                    equipMaintainEntity.setEquipName(equipEntity.getName());
                    equipMaintainEntity.setSpecification(equipEntity.getSpecification());
                }
            }
            if (ToolUtil.isNotEmpty(equipMaintainEntity.getProblemType())) {
                DictEntity dict = dictMapper.selectById(equipMaintainEntity.getProblemType());
                if (dict != null) {
                    equipMaintainEntity.setProblemType(dict.getName());
                }
            }
        }
        return equipMaintainEntityList;
    }

    @Override
    public void addEquipMaintain(EquipMaintainEntity equipMaintain, ShiroUser user) {
        LambdaQueryWrapper<EquipMaintainEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(EquipMaintainEntity::getId,equipMaintain.getId());
        EquipMaintainEntity equipMaintainEntity = equipMaintainMapper.selectOne(lambdaQuery);
        if(equipMaintainEntity != null)
        {
            throw new BusinessException(ErrorEnum.ERROR_ONLY_MAINTAIN_CODE);
        }
        equipMaintain.setCreateDate(new Date());
        equipMaintain.setCreatePerson(user.getId());
        equipMaintain.setSynFlag(false);
        equipMaintain.setDeleteFlag(true);
        equipMaintainMapper.insert(equipMaintain);
        //详情页面插入
        EquipMaintaindetailEntity equipMaintaindetail = new EquipMaintaindetailEntity();
        equipMaintaindetail.setMaintainId(equipMaintain.getId());
        equipMaintaindetail.setStartTime(equipMaintain.getStartTime());
        equipMaintaindetail.setEndTime(equipMaintain.getEndTime());
        equipMaintaindetail.setContent(equipMaintain.getContent());
        equipMaintaindetail.setMunition(equipMaintain.getMunition());
        equipMaintaindetail.setMaintainPerson(equipMaintain.getMaintainPerson());
        equipMaintaindetail.setCreateDate(new Date());
        equipMaintaindetail.setCreatePerson(user.getId());
        equipMaintaindetail.setSynFlag(false);
        equipMaintaindetail.setDeleteFlag(true);
        equipMaintaindetailMapper.insert(equipMaintaindetail);
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
