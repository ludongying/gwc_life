package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.modular.system.dao.PositionDeptMapper;
import com.seven.gwc.modular.system.entity.PositionDeptEntity;
import com.seven.gwc.modular.system.service.PositionDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description : 岗位与部门关联服务实现类
 *
 * @author : GD
 * @date : 2019-10-21
 */
@Service
public class PositionDeptServiceImpl extends ServiceImpl<PositionDeptMapper, PositionDeptEntity> implements PositionDeptService {
    @Autowired
    private PositionDeptMapper positionDeptMapper;

}
