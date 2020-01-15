package com.seven.gwc.modular.system.service;

import com.seven.gwc.modular.system.entity.ColumnsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 字段结构服务类
 *
 * @author : GD
 * @date : 2019-09-18
 */

public interface ColumnsService extends IService<ColumnsEntity> {

    /**
     * 字段结构查询列表
     */
    List<ColumnsEntity> selectColumns(String columnsName);
}
