package com.seven.gwc.modular.basic_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.PositionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.basic_info.entity.MunitonTypeEntity;
import com.seven.gwc.modular.basic_info.dao.MunitonTypeMapper;
import com.seven.gwc.modular.basic_info.service.MunitonTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * description : 物料类型服务实现类
 *
 * @author : LDY
 * @date : 2020-01-02
 */
@Service
public class MunitonTypeServiceImpl extends ServiceImpl<MunitonTypeMapper, MunitonTypeEntity> implements MunitonTypeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitonTypeMapper munitonTypeMapper;

    /**
     * 物料类型查询列表
     */
    @Override
    public List<MunitonTypeEntity> selectMunitonType(String munitonTypeName){
        LambdaQueryWrapper<MunitonTypeEntity> lambdaQuery = Wrappers.<MunitonTypeEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(munitonTypeName),MunitonTypeEntity::getName,munitonTypeName);
        return munitonTypeMapper.selectList(lambdaQuery);
    }

    /**
     * 物料类型显示状态
     */
    @Override
    public int setStatus(Long id, String state) {
        LambdaUpdateWrapper<MunitonTypeEntity> lambdaUpdate = Wrappers.<MunitonTypeEntity>lambdaUpdate();
        lambdaUpdate.set(MunitonTypeEntity::getShowFlag,state).eq(MunitonTypeEntity::getId,id);
        return this.munitonTypeMapper.update(null,lambdaUpdate);
    }

    /**
     * 新增物料类型
     */
    @Override
    public boolean add(MunitonTypeEntity munitionType) {
        LambdaQueryWrapper<MunitonTypeEntity> lambdaQueryWrapper = Wrappers.<MunitonTypeEntity>lambdaQuery();
        lambdaQueryWrapper.eq(MunitonTypeEntity::getTypeId,munitionType.getTypeId());
        MunitonTypeEntity munitonTypeEntity = munitonTypeMapper.selectOne(lambdaQueryWrapper);
        if(munitonTypeEntity != null){
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        munitionType.setCreateUser(user.getId());
        munitionType.setCreateTime(new Date());
        munitonTypeMapper.insert(munitionType);
        return true;
    }

    /**
     * 修改物料类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(MunitonTypeEntity munitonType) {
        LambdaQueryWrapper<MunitonTypeEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(MunitonTypeEntity::getTypeId,munitonType.getTypeId());
        MunitonTypeEntity munitonTypeEntity = munitonTypeMapper.selectOne(lambdaQueryWrapper);
        if(munitonTypeEntity == null){
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        munitonType.setUpdateUser(user.getId());
        munitonType.setUpdateTime(new Date());
        munitonTypeMapper.updateById(munitonType);
        return true;
    }
}
