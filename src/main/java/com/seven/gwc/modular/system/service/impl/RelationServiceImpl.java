package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.modular.system.dao.RelationMapper;
import com.seven.gwc.modular.system.entity.RelationEntity;
import com.seven.gwc.modular.system.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description : 角色和菜单关联服务实现类
 *
 * @author : GD
 * @date : 2019-08-09
 */
@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, RelationEntity> implements RelationService {
    @Autowired
    private RelationMapper relationMapper;

}
