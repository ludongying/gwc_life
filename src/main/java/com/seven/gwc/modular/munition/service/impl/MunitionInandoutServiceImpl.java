package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.dao.MunitionInMapper;
import com.seven.gwc.modular.munition.service.MunitionInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 物资入库服务实现类
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Service
public class MunitionInandoutServiceImpl extends ServiceImpl<MunitionInMapper, MunitionInEntity> implements MunitionInService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionInMapper munitionInandoutMapper;

    @Override
    public List<MunitionInEntity> selectMunitionInandout(String munitionInandoutName){
        LambdaQueryWrapper<MunitionInEntity> lambdaQuery = Wrappers.<MunitionInEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(munitionInandoutName), MunitionInEntity::getId,munitionInandoutName);
        return munitionInandoutMapper.selectList(lambdaQuery);
    }

    @Override
    public void addMunitionInandout(MunitionInEntity munitionInandout, ShiroUser user) {
        munitionInandoutMapper.insert(munitionInandout);
    }

    @Override
    public void deleteMunitionInandout(String munitionInandoutId, ShiroUser user) {
        munitionInandoutMapper.deleteById(munitionInandoutId);
    }

    @Override
    public void editMunitionInandout(MunitionInEntity munitionInandout, ShiroUser user) {
        munitionInandoutMapper.updateById(munitionInandout);
    }

}
