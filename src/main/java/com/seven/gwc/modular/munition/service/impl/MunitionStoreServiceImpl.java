package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.munition.entity.MunitionStoreEntity;
import com.seven.gwc.modular.munition.dao.MunitionStoreMapper;
import com.seven.gwc.modular.munition.service.MunitionStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * description : 库存管理服务实现类
 *
 * @author : LDY
 * @date : 2020-04-20
 */
@Service
public class MunitionStoreServiceImpl extends ServiceImpl<MunitionStoreMapper, MunitionStoreEntity> implements MunitionStoreService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionStoreMapper munitionStoreMapper;

    @Override
    public MunitionStoreEntity getMunitionStore(String munitionId){
        LambdaQueryWrapper<MunitionStoreEntity> lambdaQuery = Wrappers.<MunitionStoreEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(munitionId),MunitionStoreEntity::getMunitionId,munitionId);
        return munitionStoreMapper.selectOne(lambdaQuery);
    }

    @Override
    public boolean addMunitionStore(MunitionStoreEntity munitionStore, ShiroUser user) {
        LambdaQueryWrapper<MunitionStoreEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionStoreEntity::getMunitionId,munitionStore.getMunitionId()).eq(MunitionStoreEntity::getDeleteFlag,1);
        MunitionStoreEntity munitionStoreEntity = munitionStoreMapper.selectOne(lambdaQuery);
        if(munitionStoreEntity != null){//物资存在，不做任何操作
            return false;
        }
        munitionStore.setCreateDate(new Date());
        munitionStore.setCreatePerson(user.getId());
        munitionStore.setSynFlag(false);
        munitionStore.setDeleteFlag(true);
        munitionStoreMapper.insert(munitionStore);
        return true;
    }

    @Override
    public void deleteMunitionStore(String munitionStoreId, ShiroUser user) {
        MunitionStoreEntity store = munitionStoreMapper.selectById(munitionStoreId);
        if(store!= null){
            store.setDeleteFlag(false);
            store.setSynFlag(false);
            store.setUpdateDate(new Date());
            store.setUpdatePerson(user.getId());
        }
        munitionStoreMapper.updateById(store);
    }

    @Override
    public boolean editMunitionStore(MunitionStoreEntity munitionStore, ShiroUser user) {
        LambdaQueryWrapper<MunitionStoreEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionStoreEntity::getMunitionId,munitionStore.getMunitionId()).eq(MunitionStoreEntity::getDeleteFlag,1).ne(MunitionStoreEntity::getId,munitionStore.getId());
        MunitionStoreEntity store = munitionStoreMapper.selectOne(lambdaQuery);
        if( store!=null){
            return false;
        }
        munitionStore.setUpdateDate(new Date());
        munitionStore.setUpdatePerson(user.getId());
        munitionStoreMapper.updateById(munitionStore);
        return false;
    }

}
