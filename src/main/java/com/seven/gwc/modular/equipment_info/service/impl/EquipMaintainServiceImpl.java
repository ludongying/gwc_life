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
import com.seven.gwc.modular.equipment_info.entity.EquipMaintainEntity;
import com.seven.gwc.modular.equipment_info.entity.EquipMaintaindetailEntity;
import com.seven.gwc.modular.equipment_info.service.EquipMaintainService;
import com.seven.gwc.modular.sailor.dao.PersonMapper;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
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
    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<EquipMaintainEntity> selectEquipMaintain(EquipMaintainEntity equipMaintain){
        List<EquipMaintainEntity> equipMaintainEntityList = equipMaintainMapper.getMaintainList(equipMaintain);
        for(EquipMaintainEntity equipMaintainEntity: equipMaintainEntityList){
            if (ToolUtil.isNotEmpty(equipMaintainEntity.getType())) {//设备类型名称
                DictEntity dict = dictMapper.selectById(equipMaintainEntity.getType());
                if (dict != null) {
                    equipMaintainEntity.setTypeDesp(dict.getName());
                }
            }
            if (ToolUtil.isNotEmpty(equipMaintainEntity.getProblemType())) {//维保工作类型
                DictEntity dict = dictMapper.selectById(equipMaintainEntity.getProblemType());
                if (dict != null) {
                    equipMaintainEntity.setProblemTypeDesp(dict.getName());
                }
            }
            if (ToolUtil.isNotEmpty(equipMaintainEntity.getMaintainPerson())) {//维修人姓名
                PersonEntity person = personMapper.PersonNamesEntityList(equipMaintainEntity.getMaintainPerson()).get(0);
                if (person != null) {
                    equipMaintainEntity.setMaintainPersonName(person.getPersonName());
                }
            }
        }
        return equipMaintainEntityList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addEquipMaintain(EquipMaintainEntity equipMaintain, ShiroUser user) {
        //维保详情表插入
        EquipMaintaindetailEntity equipMaintaindetail = new EquipMaintaindetailEntity();
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
        //维保主表插入
        LambdaQueryWrapper<EquipMaintainEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(EquipMaintainEntity::getId,equipMaintain.getId());
        EquipMaintainEntity equipMaintainEntity = equipMaintainMapper.selectOne(lambdaQuery);
        if(equipMaintainEntity != null)
        {
            throw new BusinessException(ErrorEnum.ERROR_ONLY_MAINTAIN_CODE);
        }
        equipMaintain.setDetailIds(equipMaintaindetail.getId());//当前只有一个详情表，未来可以扩展为多个详情表,用，分隔
        equipMaintain.setCreateDate(new Date());
        equipMaintain.setCreatePerson(user.getId());
        equipMaintain.setSynFlag(false);
        equipMaintain.setDeleteFlag(true);
        equipMaintainMapper.insert(equipMaintain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquipMaintain(String equipMaintainId, ShiroUser user) {
        //先删除子表
        EquipMaintainEntity equipMaintainEntity = equipMaintainMapper.selectById(equipMaintainId);
        equipMaintaindetailMapper.deleteById(equipMaintainEntity.getDetailIds());
        //再删除主表
        equipMaintainMapper.deleteById(equipMaintainId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editEquipMaintain(EquipMaintainEntity equipMaintain, ShiroUser user) {
//        LambdaQueryWrapper<EquipMaintainEntity> lambdaQuery = Wrappers.lambdaQuery();
//        lambdaQuery.eq(EquipMaintainEntity::,equip.getSerialNumber()).ne(EquipEntity::getId,equip.getId());
//        EquipEntity equipEntity = equipMapper.selectOne(lambdaQuery);
//        if(equipEntity != null){
//            return false;
//        }
        //维保详情表更新
        EquipMaintaindetailEntity equipMaintaindetailEntity = new EquipMaintaindetailEntity();
        equipMaintaindetailEntity.setId(equipMaintain.getDetailIds());
        equipMaintaindetailEntity.setMaintainPerson(equipMaintain.getMaintainPerson());
        equipMaintaindetailEntity.setStartTime(equipMaintain.getStartTime());
        equipMaintaindetailEntity.setEndTime(equipMaintain.getEndTime());
        equipMaintaindetailEntity.setContent(equipMaintain.getContent());
        equipMaintaindetailEntity.setMunition(equipMaintain.getMunition());
        equipMaintaindetailEntity.setUpdateDate(new Date());
        equipMaintaindetailEntity.setUpdatePerson(user.getId());
        equipMaintaindetailMapper.updateById(equipMaintaindetailEntity);
        //维保主表更新
        equipMaintain.setUpdateDate(new Date());
        equipMaintain.setUpdatePerson(user.getId());
        equipMaintainMapper.updateById(equipMaintain);
    }

    @Override
    public EquipMaintainEntity getOneById(String equipMaintainId) {
        EquipMaintainEntity equipMaintainEntity = new EquipMaintainEntity();
        equipMaintainEntity.setId(equipMaintainId);
        return this.selectEquipMaintain(equipMaintainEntity).get(0);
    }

}
