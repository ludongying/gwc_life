package com.seven.gwc.modular.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : dictionaryMapper 接口
 *
 * @author : LM
 * @date : 2019-10-10
 */
public interface DictMapper extends BaseMapper<DictEntity> {

    /**
     * 获取ztree的节点列表
     * @param  dictTypeId 部门类型ID
     * @return 树
     */
    List<ZTreeNode> dictTree(@Param("dictTypeId") Long dictTypeId);

    /**
     * 根据ID查询字典
     * @param dictId 字典ID
     * @return 树
     */
    List<DictEntity> likeParentIds(@Param("dictId") Long dictId);

}
