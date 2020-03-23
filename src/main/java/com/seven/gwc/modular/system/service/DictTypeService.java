package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.DictTypeEntity;

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

    BaseResult add(DictTypeEntity dictTypeEntity, ShiroUser user);

    BaseResult update(DictTypeEntity dictTypeEntity, ShiroUser user);
}
