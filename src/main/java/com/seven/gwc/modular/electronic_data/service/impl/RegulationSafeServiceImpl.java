package com.seven.gwc.modular.electronic_data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.electronic_data.entity.RegulationSafeEntity;
import com.seven.gwc.modular.electronic_data.dao.RegulationSafeMapper;
import com.seven.gwc.modular.electronic_data.service.RegulationSafeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * description : 法律法规/航线安全服务实现类
 *
 * @author : SHQ
 * @date : 2020-02-25
 */
@Service
public class RegulationSafeServiceImpl extends ServiceImpl<RegulationSafeMapper, RegulationSafeEntity> implements RegulationSafeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegulationSafeMapper regulationSafeMapper;

    @Override
    public List<RegulationSafeEntity> selectRegulationSafe(String regulationSafeName,String lawRegularId,String type) {
        return regulationSafeMapper.selectRegulationSafeList(regulationSafeName,lawRegularId,type);
    }

    @Override
    public void addRegulationSafe(RegulationSafeEntity regulationSafe, ShiroUser user) {
        regulationSafe.setSynFlag(false);
        regulationSafe.setDeleteFlag(false);
        regulationSafe.setCreateDate(new Date());
        regulationSafe.setCreatePerson(user.getId());
        regulationSafeMapper.insert(regulationSafe);
    }

    @Override
    public void deleteRegulationSafe(String regulationSafeId, ShiroUser user) {
        regulationSafeMapper.deleteById(regulationSafeId);
    }

    @Override
    public void editRegulationSafe(RegulationSafeEntity regulationSafe, ShiroUser user) {
        regulationSafeMapper.updateById(regulationSafe);
    }

    @Override
    public boolean selectOnlyByName(String name, String type) {

        LambdaQueryWrapper<RegulationSafeEntity> query = Wrappers.lambdaQuery();
        query.eq(RegulationSafeEntity::getName, name)
                .eq(RegulationSafeEntity::getType, type);
        return regulationSafeMapper.selectList(query).size() == 0;
    }

}
