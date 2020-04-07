package com.seven.gwc.modular.path.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.path.entity.FishForbiddenAreaEntity;
import com.seven.gwc.modular.path.dao.FishForbiddenAreaMapper;
import com.seven.gwc.modular.path.service.FishForbiddenAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * description : 禁渔区服务实现类
 *
 * @author : QQC
 * @date : 2020-03-09
 */
@Service
public class FishForbiddenAreaServiceImpl extends ServiceImpl<FishForbiddenAreaMapper, FishForbiddenAreaEntity> implements FishForbiddenAreaService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FishForbiddenAreaMapper fishForbiddenAreaMapper;

    /**
     * 禁渔区查询列表
     */
    @Override
    public List<FishForbiddenAreaEntity> selectFishForbiddenArea(String fishForbiddenAreaName){
        LambdaQueryWrapper<FishForbiddenAreaEntity> lambdaQuery = Wrappers.<FishForbiddenAreaEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(fishForbiddenAreaName),FishForbiddenAreaEntity::getId,fishForbiddenAreaName);
        return fishForbiddenAreaMapper.selectList(lambdaQuery);
    }
    /**
     * 禁渔区查询列表
     */
    @Override
    public List<FishForbiddenAreaEntity> fishForbiddenAreaPointlist(String AreaId){
        LambdaQueryWrapper<FishForbiddenAreaEntity> lambdaQuery = Wrappers.<FishForbiddenAreaEntity>lambdaQuery();
        //按照序号顺序排列
        lambdaQuery.eq(FishForbiddenAreaEntity::getAreaId,AreaId)
                .orderByAsc(FishForbiddenAreaEntity::getNum);
        return fishForbiddenAreaMapper.selectList(lambdaQuery);
    }

}
