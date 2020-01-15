package com.seven.gwc.modular.system.service;

import com.seven.gwc.modular.system.entity.TablesEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 表结构服务类
 * @author : GD
 * @date : 2019-09-18
 */

public interface TablesService extends IService<TablesEntity> {

    /**
     * 表结构查询列表
     */
    List<TablesEntity> getList(String tableName);
}
