package com.seven.gwc.modular.path.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.path.entity.PointlistEntity;
import com.seven.gwc.modular.path.dao.PointlistMapper;
import com.seven.gwc.modular.path.service.PointlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : path服务实现类
 *
 * @author : QQC
 * @date : 2020-01-02
 */
@Service
public class PointlistServiceImpl extends ServiceImpl<PointlistMapper, PointlistEntity> implements PointlistService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PointlistMapper pointlistMapper;

    /**
     * path查询列表
     */
    @Override
    public List<PointlistEntity> selectPointlist(String pointlistName){
        LambdaQueryWrapper<PointlistEntity> lambdaQuery = Wrappers.<PointlistEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(pointlistName),PointlistEntity::getShipId,pointlistName);
        return pointlistMapper.selectList(lambdaQuery);
    }

}
