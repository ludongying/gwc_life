package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.munition.dao.MunitionInfoMapper;
import com.seven.gwc.modular.munition.entity.MunitionInfoEntity;
import com.seven.gwc.modular.munition.service.MunitionInfoService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * description : 物资信息服务实现类
 *
 * @author : LDY
 * @date : 2020-04-03
 */
@Service
public class MunitionInfoServiceImpl extends ServiceImpl<MunitionInfoMapper, MunitionInfoEntity> implements MunitionInfoService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionInfoMapper munitionInfoMapper;
    @Autowired
    DictMapper dictMapper;

    @Override
    public List<MunitionInfoEntity> selectMunitionInfo(String munitionInfoName){
        LambdaQueryWrapper<MunitionInfoEntity> lambdaQuery = Wrappers.<MunitionInfoEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(munitionInfoName),MunitionInfoEntity::getName,munitionInfoName);
        List<MunitionInfoEntity> lists = munitionInfoMapper.selectList(lambdaQuery);
        for(MunitionInfoEntity munitionInfoEntity: lists){
            if(ToolUtil.isNotEmpty(munitionInfoEntity.getTypeId())){
                DictEntity dictEntity = dictMapper.selectById(munitionInfoEntity.getTypeId());
                if(dictEntity != null){
                    munitionInfoEntity.setTypeDesp(dictEntity.getName());
                }
            }
        }
        return lists;
    }

    @Override
    public boolean addMunitionInfo(MunitionInfoEntity munitionInfo, ShiroUser user) {
        LambdaQueryWrapper<MunitionInfoEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInfoEntity::getCode, munitionInfo.getCode());
        MunitionInfoEntity munition = munitionInfoMapper.selectOne(lambdaQuery);
        if (munition != null) {
            return false;
        }
        munitionInfo.setCreateDate(new Date());
        munitionInfo.setCreatePerson(user.getId());
        munitionInfo.setSynFlag(false);
        munitionInfo.setDeleteFlag(true);
        munitionInfoMapper.insert(munitionInfo);
        return true;
    }

    @Override
    public void deleteMunitionInfo(String munitionInfoId, ShiroUser user) {
        munitionInfoMapper.deleteById(munitionInfoId);
    }

    @Override
    public boolean editMunitionInfo(MunitionInfoEntity munitionInfo, ShiroUser user) {
        munitionInfoMapper.updateById(munitionInfo);
        return false;
    }

}
