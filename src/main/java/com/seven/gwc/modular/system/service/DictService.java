package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.DictEntity;

import java.util.List;

/**
 * description : 字典服务类
 *
 * @author LM
 * @date 2019-10-10
 */

public interface DictService extends IService<DictEntity> {

    List<DictEntity> selectDict(Long dictTypeId);

    /**
     * 添加字典
     */
    void add(DictEntity dict);

    /**
     * 修改字典
     */
    void update(DictEntity dict);

    /**
     * 根据ID获取字典详情
     */
    DictEntity dictDetail(Long dictId);

    /**
     * 获取字典的树形列表  字典列表用
     */
    List<ZTreeNode> dictTreeList(Long dictTypeId, Long dictId);

    /**
     * 获取某个类型下字典树的列表，选择上级字典时用
     */

    /**
     * 根据字典类型编码获取车场类型字典数据
     * @author: SHQ
     * @return
     */
    List<DictEntity> selectDictListByDictType(String dictTypeCode);

    /**
     * 根据字典名称跟地点类型获取字典数据
     * @author: SHQ
     * @return
     */
    DictEntity findByNameAndTypeId(String name, String typeCode);
}
