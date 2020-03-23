package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.DictEntity;

import java.util.List;

/**
 * description : 字典服务类
 *
 * @author LM
 * @date 2019-10-10
 */

public interface DictService extends IService<DictEntity> {

    List<DictEntity> selectDict(String dictTypeId, String name);

    /**
     * 添加字典
     */
    BaseResult add(DictEntity dict, ShiroUser user);

    /**
     * 编辑字典
     */
    BaseResult update(DictEntity dict, ShiroUser user);

    /**
     * 根据ID获取字典详情
     */
    DictEntity dictDetail(String dictId);

    /**
     * 获取字典的树形列表  字典列表用
     */
    List<ZTreeNode> getDictTreeByDictTypeCode(String dictTypeCode);

    /**
     * 根据字典类型编码获取车场类型字典数据
     * @author : SHQ
     * @return
     */
    List<DictEntity> getDictListByDictTypeCode(String dictTypeCode);

    /**
     * 根据字典名称跟地点类型获取字典数据
     * @author : SHQ
     * @return
     */
    DictEntity findByNameAndTypeCode(String name, String typeCode);
}
