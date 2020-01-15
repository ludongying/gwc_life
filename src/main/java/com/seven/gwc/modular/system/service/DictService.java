package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.DictEntity;

import java.util.List;
import java.util.Map;

/**
 * description : 字典服务类
 *
 * @author LM
 * @date 2019-10-10
 */

public interface DictService extends IService<DictEntity> {

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
    List<Map<String, Object>> selectDictTree(String menuName, Long typeId);

}
