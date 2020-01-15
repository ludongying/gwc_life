package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.DictTypeMapper;
import com.seven.gwc.modular.system.entity.DictTypeEntity;
import com.seven.gwc.modular.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : dictionary服务实现类
 *
 * @author : LM
 * @date : 2019-10-10
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictTypeEntity> implements DictTypeService {
    @Autowired
    private DictTypeMapper dictTypeMapper;

    /**
     * 字典类型查询列表
     */
    @Override
    public List<DictTypeEntity> selectSysDictType(String sysDictTypeName) {
        LambdaQueryWrapper<DictTypeEntity> lambdaQuery = Wrappers.<DictTypeEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(sysDictTypeName), DictTypeEntity::getName, sysDictTypeName)
                .orderByAsc(DictTypeEntity::getSort);
        return dictTypeMapper.selectList(lambdaQuery);
    }

}
