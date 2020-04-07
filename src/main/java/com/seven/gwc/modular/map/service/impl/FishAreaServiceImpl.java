package com.seven.gwc.modular.map.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.map.entity.FishAreaEntity;
import com.seven.gwc.modular.map.dao.FishAreaMapper;
import com.seven.gwc.modular.map.service.FishAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 海图渔区服务实现类
 *
 * @author : QQC
 * @date : 2020-03-23
 */
@Service
public class FishAreaServiceImpl extends ServiceImpl<FishAreaMapper, FishAreaEntity> implements FishAreaService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FishAreaMapper fishAreaMapper;

    /**
     * 海图渔区查询列表
     */
    @Override
    public List<FishAreaEntity> selectFishArea(String fishAreaName){
        LambdaQueryWrapper<FishAreaEntity> lambdaQuery = Wrappers.<FishAreaEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(fishAreaName),FishAreaEntity::getName,fishAreaName);
        return fishAreaMapper.selectList(lambdaQuery);
    }
    /**
     * 海图渔区查询列表
     */
    @Override
    public List<FishAreaEntity> fishAreaPointlist(){
        LambdaQueryWrapper<FishAreaEntity> lambdaQuery = Wrappers.<FishAreaEntity>lambdaQuery();

        return fishAreaMapper.selectList(lambdaQuery);
    }
}
