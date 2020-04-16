package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import com.seven.gwc.modular.munition.dao.MunitionInMapper;
import com.seven.gwc.modular.munition.entity.MunitionInDetailEntity;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.munitionEnum.MunitionInStatesEnum;
import com.seven.gwc.modular.munition.service.MunitionInService;
import com.seven.gwc.modular.sailor.dao.PersonMapper;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.dto.UserDTO;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * description : 物资入库服务实现类
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Service
public class MunitionInServiceImpl extends ServiceImpl<MunitionInMapper, MunitionInEntity> implements MunitionInService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionInMapper munitionInMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<MunitionInEntity> selectMunitionIn(MunitionInEntity munitionIn, Integer total, Integer size){
        List<MunitionInEntity> lists = munitionInMapper.selectMunitionInList(munitionIn,total,size);
        for(MunitionInEntity munitionInEntity: lists){
            if(ToolUtil.isNotEmpty(munitionInEntity.getMunitionType())){
                DictEntity dictEntity = dictMapper.selectById(munitionInEntity.getMunitionType());
                if(dictEntity != null){
                    munitionInEntity.setMunitionTypeDesp(dictEntity.getName());
                }
            }
            if(ToolUtil.isNotEmpty(munitionInEntity.getApplyPerson())){
                UserEntity user = userMapper.selectById(munitionInEntity.getApplyPerson());
                if(user != null){
                    munitionInEntity.setApplyPersonDesp(user.getName());
                }
            }
            if(ToolUtil.isNotEmpty(munitionInEntity.getInOutPerson())){
                UserEntity user = userMapper.selectById(munitionInEntity.getInOutPerson());
                if(user != null){
                    munitionInEntity.setInOutPersonDesp(user.getName());
                }
            }
        }
        return lists;
    }

    @Override
    public Integer getListSize(MunitionInEntity munitionIn) {
        List<MunitionInEntity> list = munitionInMapper.getListSize(munitionIn);
        return list.size();
    }

    @Override
    public boolean addMunitionIn(MunitionInEntity munitionIn, ShiroUser user) {
        LambdaQueryWrapper<MunitionInEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInEntity::getCode, munitionIn.getCode());
        MunitionInEntity munitionInEntity = munitionInMapper.selectOne(lambdaQuery);
        if (munitionInEntity != null) {
            return false;
        }
        munitionIn.setActionType(0);//入库为0，出库为1
        //表单状态：0保存，1提交，2出入库通过，3出入库驳回，4审核通过，5审核未通过
        munitionIn.setStatus(MunitionInStatesEnum.SAVE.getCode());
        munitionIn.setCreateDate(new Date());
        munitionIn.setCreatePerson(user.getId());
        munitionIn.setSynFlag(false);
        munitionIn.setDeleteFlag(true);
        munitionInMapper.insert(munitionIn);
        return true;
    }

    @Override
    public void deleteMunitionIn(String munitionInId, ShiroUser user) {
        MunitionInEntity munitionInEntity = munitionInMapper.selectById(munitionInId);
        if (munitionInEntity != null) {
            munitionInEntity.setDeleteFlag(false);
            munitionInEntity.setSynFlag(false);
            munitionInEntity.setUpdateDate(new Date());
            munitionInEntity.setUpdatePerson(user.getId());
        }
        munitionInMapper.updateById(munitionInEntity);
    }

    @Override
    public boolean editMunitionIn(MunitionInEntity munitionIn, ShiroUser user) {
        LambdaQueryWrapper<MunitionInEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInEntity::getCode,munitionIn.getCode()).eq(MunitionInEntity::getDeleteFlag, 1).ne(MunitionInEntity::getId,munitionIn.getId());
        MunitionInEntity munitionInEntity = munitionInMapper.selectOne(lambdaQuery);
        if(munitionInEntity != null){
            return false;
        }
        munitionIn.setUpdateDate(new Date());
        munitionIn.setUpdatePerson(user.getId());
        munitionInMapper.updateById(munitionIn);
        return true;
    }

    @Override
    public String getAutoCode() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = format.format(new Date());
        Integer code = munitionInMapper.maxCode(uid_pfix);
        String codestr = uid_pfix;
        if(code==0){
            codestr += "1001"; //1001为当天初始表单
        }else{
            code = code + 1001;
            codestr += code.toString();
        }
        return codestr;
    }

    @Override
    public int setStatus(String munitionInId, String state , ShiroUser user) {
        LambdaUpdateWrapper<MunitionInEntity> lambdaUpdate = Wrappers.<MunitionInEntity>lambdaUpdate();
        if(state == MunitionInStatesEnum.SUBMIT.getCode()){
            lambdaUpdate.set(MunitionInEntity::getStatus, state).set(MunitionInEntity::getApplyPerson,user.getId()).set(MunitionInEntity::getApplyTime,new Date()).eq(MunitionInEntity::getId, munitionInId);
        }
        else if(state == MunitionInStatesEnum.MUNITION_IN_OK.getCode() || state == MunitionInStatesEnum.MUNITION_IN_REFUSED.getCode()){
            lambdaUpdate.set(MunitionInEntity::getStatus, state).set(MunitionInEntity::getInOutPerson,user.getId()).set(MunitionInEntity::getInOutTime,new Date()).eq(MunitionInEntity::getId, munitionInId);
        }
        else if(state == MunitionInStatesEnum.APPROVE.getCode() || state == MunitionInStatesEnum.REFUSED.getCode()){
            lambdaUpdate.set(MunitionInEntity::getStatus, state).set(MunitionInEntity::getApprovePerson,user.getId()).set(MunitionInEntity::getApproveTime,new Date()).eq(MunitionInEntity::getId, munitionInId);
        }
        else{
            lambdaUpdate.set(MunitionInEntity::getStatus, state).eq(MunitionInEntity::getId, munitionInId);
        }
        return this.munitionInMapper.update(null, lambdaUpdate);
    }

}
