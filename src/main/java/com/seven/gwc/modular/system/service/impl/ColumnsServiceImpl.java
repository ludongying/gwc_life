package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.ColumnsMapper;
import com.seven.gwc.modular.system.entity.ColumnsEntity;
import com.seven.gwc.modular.system.service.ColumnsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 字段结构服务实现类
 *
 * @author : GD
 * @date : 2019-09-18
 */
@Service
public class ColumnsServiceImpl extends ServiceImpl<ColumnsMapper, ColumnsEntity> implements ColumnsService {
    @Autowired
    private ColumnsMapper columnsMapper;

    @Override
    public List<ColumnsEntity> selectColumns(String columnsName) {
        LambdaQueryWrapper<ColumnsEntity> lambdaQuery = Wrappers.<ColumnsEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(columnsName), ColumnsEntity::getColumnName, columnsName);
        return columnsMapper.selectList(lambdaQuery);
    }

}
