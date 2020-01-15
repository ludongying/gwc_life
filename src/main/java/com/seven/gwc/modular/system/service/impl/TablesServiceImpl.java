package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.modular.system.dao.TablesMapper;
import com.seven.gwc.modular.system.entity.TablesEntity;
import com.seven.gwc.modular.system.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description : 表结构服务实现类
 *
 * @author : GD
 * @date : 2019-09-18
 */
@Service
public class TablesServiceImpl extends ServiceImpl<TablesMapper, TablesEntity> implements TablesService {
    @Autowired
    private TablesMapper tablesMapper;

    @Override
    public List<TablesEntity> getList(String tableName) {
        return tablesMapper.getList(tableName);
    }

}
