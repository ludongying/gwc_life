package com.seven.gwc.modular.system.service;

import com.seven.gwc.modular.system.entity.DictTypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 字典类型服务类
 *
 * @author : LM
 * @date : 2019-10-10
 */

public interface DictTypeService extends IService<DictTypeEntity> {

    /**
     * @param sysDictTypeName 字典类型名称，可为空
     * @return 字典类型列表
     */
    List<DictTypeEntity> selectSysDictType(String sysDictTypeName);
}
