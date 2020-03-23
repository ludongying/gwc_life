package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.DictTypeMapper;
import com.seven.gwc.modular.system.entity.DictTypeEntity;
import com.seven.gwc.modular.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public BaseResult add(DictTypeEntity dictTypeEntity, ShiroUser user) {
        LambdaQueryWrapper<DictTypeEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(DictTypeEntity::getCode, dictTypeEntity.getCode());
        DictTypeEntity dictType = dictTypeMapper.selectOne(lambdaQuery);
        if (ToolUtil.isNotEmpty(dictType)) {
            return new BaseResult(false, 500, ErrorEnum.ERROR_ONLY_CODE.getMessage());
        }
        dictTypeEntity.setCreateTime(new Date());
        dictTypeEntity.setCreateUser(user.getName());
        dictTypeMapper.insert(dictTypeEntity);
        return new BaseResult(true,200, "操作成功");
    }

    @Override
    public BaseResult update(DictTypeEntity dictTypeEntity, ShiroUser user) {
        LambdaQueryWrapper<DictTypeEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(DictTypeEntity::getCode, dictTypeEntity.getCode())
                .ne(DictTypeEntity::getId, dictTypeEntity.getId());
        DictTypeEntity dictType = dictTypeMapper.selectOne(lambdaQuery);
        if (ToolUtil.isNotEmpty(dictType)) {
            return new BaseResult(false, 500, ErrorEnum.ERROR_ONLY_CODE.getMessage());
        }
        dictTypeEntity.setUpdateTime(new Date());
        dictTypeEntity.setUpdateUser(user.getName());
        dictTypeMapper.updateById(dictTypeEntity);
        return new BaseResult(true,200, "操作成功");
    }

}
