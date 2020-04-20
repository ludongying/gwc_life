package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.munition.dao.MunitionOutMapper;
import com.seven.gwc.modular.munition.entity.MunitionOutEntity;
import com.seven.gwc.modular.munition.munitionEnum.MunitionInOutStatesEnum;
import com.seven.gwc.modular.munition.service.MunitionOutService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.dao.UserMapper;
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
 * description : 物资出库服务实现类
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Service
public class MunitionOutServiceImpl extends ServiceImpl<MunitionOutMapper, MunitionOutEntity> implements MunitionOutService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionOutMapper munitionOutMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<MunitionOutEntity> selectMunitionOut(MunitionOutEntity munitionOut, Integer total, Integer size){
        List<MunitionOutEntity> lists = munitionOutMapper.selectMunitionOutList(munitionOut,total,size);
        for(MunitionOutEntity munitionOutEntity: lists){
            if(ToolUtil.isNotEmpty(munitionOutEntity.getMunitionType())){
                DictEntity dictEntity = dictMapper.selectById(munitionOutEntity.getMunitionType());
                if(dictEntity != null){
                    munitionOutEntity.setMunitionTypeDesp(dictEntity.getName());
                }
            }
            if(ToolUtil.isNotEmpty(munitionOutEntity.getApplyPerson())){
                UserEntity userEntity = userMapper.selectById(munitionOutEntity.getApplyPerson());
                if(userEntity != null){
                    munitionOutEntity.setApplyPersonDesp(userEntity.getName());
                }
            }
            if(ToolUtil.isNotEmpty(munitionOutEntity.getInOutPerson())){
                UserEntity userEntity = userMapper.selectById(munitionOutEntity.getInOutPerson());
                if(userEntity != null){
                    munitionOutEntity.setInOutPersonDesp(userEntity.getName());
                }
            }
        }
        return lists;
    }

    @Override
    public Integer getListSize(MunitionOutEntity munitionOut) {
        List<MunitionOutEntity> list = munitionOutMapper.getListSize(munitionOut);
        return list.size();
    }

    @Override
    public boolean addMunitionOut(MunitionOutEntity munitionOut, ShiroUser user) {
        LambdaQueryWrapper<MunitionOutEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionOutEntity::getCode, munitionOut.getCode());
        MunitionOutEntity munitionOutEntity = munitionOutMapper.selectOne(lambdaQuery);
        if (munitionOutEntity != null) {
            return false;
        }
        munitionOut.setActionType(1);//入库为0，出库为1
        //表单状态：0保存，1提交，2出入库通过，3出入库驳回，4审核通过，5审核未通过
        munitionOut.setStatus(MunitionInOutStatesEnum.SAVE.getCode());
        munitionOut.setCreateDate(new Date());
        munitionOut.setCreatePerson(user.getId());
        munitionOut.setSynFlag(false);
        munitionOut.setDeleteFlag(true);
        munitionOutMapper.insert(munitionOut);
        return true;
    }

    @Override
    public void deleteMunitionOut(String munitionOutId, ShiroUser user) {
        MunitionOutEntity munitionOutEntity = munitionOutMapper.selectById(munitionOutId);
        if (munitionOutEntity != null) {
            munitionOutEntity.setDeleteFlag(false);
            munitionOutEntity.setSynFlag(false);
            munitionOutEntity.setUpdateDate(new Date());
            munitionOutEntity.setUpdatePerson(user.getId());
        }
        munitionOutMapper.updateById(munitionOutEntity);
    }

    @Override
    public boolean editMunitionOut(MunitionOutEntity munitionOut, ShiroUser user) {
        LambdaQueryWrapper<MunitionOutEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionOutEntity::getCode,munitionOut.getCode()).eq(MunitionOutEntity::getDeleteFlag, 1).ne(MunitionOutEntity::getId,munitionOut.getId());
        MunitionOutEntity munitionOutEntity = munitionOutMapper.selectOne(lambdaQuery);
        if(munitionOutEntity != null){
            return false;
        }
        munitionOut.setUpdateDate(new Date());
        munitionOut.setUpdatePerson(user.getId());
        munitionOutMapper.updateById(munitionOut);
        return true;
    }

    @Override
    public String getAutoCode() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = format.format(new Date());
        Integer code = munitionOutMapper.maxCode(uid_pfix);
        String codestr = uid_pfix;
        if(code==0){
            codestr += "2001"; //1001为当天初始表单
        }else{
            code = code + 2001;
            codestr += code.toString();
        }
        return codestr;
    }

    @Override
    public int setStatus(String munitionOutId, String state , ShiroUser user) {
        LambdaUpdateWrapper<MunitionOutEntity> lambdaUpdate = Wrappers.<MunitionOutEntity>lambdaUpdate();
        if(state == MunitionInOutStatesEnum.SUBMIT.getCode()){
            lambdaUpdate.set(MunitionOutEntity::getStatus, state).set(MunitionOutEntity::getApplyPerson,user.getId()).set(MunitionOutEntity::getApplyTime,new Date()).eq(MunitionOutEntity::getId, munitionOutId);
        }
        else if(state == MunitionInOutStatesEnum.MUNITION_IN_OUT_OK.getCode() || state == MunitionInOutStatesEnum.MUNITION_IN_OUT_REFUSED.getCode()){
            lambdaUpdate.set(MunitionOutEntity::getStatus, state).set(MunitionOutEntity::getInOutPerson,user.getId()).set(MunitionOutEntity::getInOutTime,new Date()).eq(MunitionOutEntity::getId, munitionOutId);
        }
        else if(state == MunitionInOutStatesEnum.APPROVE.getCode() || state == MunitionInOutStatesEnum.REFUSED.getCode()){
            lambdaUpdate.set(MunitionOutEntity::getStatus, state).set(MunitionOutEntity::getApprovePerson,user.getId()).set(MunitionOutEntity::getApproveTime,new Date()).eq(MunitionOutEntity::getId, munitionOutId);
        }
        else{
            lambdaUpdate.set(MunitionOutEntity::getStatus, state).eq(MunitionOutEntity::getId, munitionOutId);
        }
        return this.munitionOutMapper.update(null, lambdaUpdate);
    }

    @Override
    public MunitionOutEntity getMunitionOutDetail(String id) {
        MunitionOutEntity munitionOutEntity = munitionOutMapper.selectById(id);
        if(ToolUtil.isNotEmpty(munitionOutEntity.getInOutPerson())){
            UserEntity userEntity = userMapper.selectById(munitionOutEntity.getInOutPerson());
            if(userEntity != null){
                munitionOutEntity.setInOutPersonDesp(userEntity.getName());
            }
        }
        if(ToolUtil.isNotEmpty(munitionOutEntity.getApprovePerson())){
            UserEntity userEntity = userMapper.selectById(munitionOutEntity.getApprovePerson());
            if(userEntity != null){
                munitionOutEntity.setApprovePersonDesp(userEntity.getName());
            }
        }
        return munitionOutEntity;
    }

}
