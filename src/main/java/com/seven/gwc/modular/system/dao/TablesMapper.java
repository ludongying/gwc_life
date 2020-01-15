package com.seven.gwc.modular.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.system.entity.TablesEntity;

import java.util.List;

/**
 * description : 表结构Mapper 接口
 *
 * @author : GD
 * @date : 2019-09-18
 */
public interface TablesMapper extends BaseMapper<TablesEntity> {

    List<TablesEntity> getList(String tableName);

}
