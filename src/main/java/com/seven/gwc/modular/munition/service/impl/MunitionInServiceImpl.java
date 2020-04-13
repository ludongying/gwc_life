package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.dao.MunitionInMapper;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.service.MunitionInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description : 物资入库服务实现类
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Service
public class MunitionInServiceImpl extends ServiceImpl<MunitionInMapper, MunitionInEntity> implements MunitionInService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionInMapper munitionInMapper;

    @Override
    public List<MunitionInEntity> selectMunitionIn(MunitionInEntity munitionIn, Integer total, Integer size) {
        return null;
    }

    @Override
    public Integer getListSize(MunitionInEntity munitionIn) {
        return null;
    }

    @Override
    public boolean addMunitionIn(MunitionInEntity munitionIn, ShiroUser user) {
        munitionInMapper.insert(munitionIn);
        return true;
    }

    @Override
    public void deleteMunitionIn(String munitionInId, ShiroUser user) {
        munitionInMapper.deleteById(munitionInId);
    }

    @Override
    public void editMunitionIn(MunitionInEntity munitionIn, ShiroUser user) {
        munitionInMapper.updateById(munitionIn);
    }

    @Override
    public String getAutoCode() {
        return null;
    }

}
