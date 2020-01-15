package com.seven.gwc.modular.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description : dictionaryMapper 接口
 *
 * @author : LM
 * @date : 2019-10-10
 */
public interface DictMapper extends BaseMapper<DictEntity> {


    List<Map<String, Object>> selectDictTree(@Param("menuName") String menuName, @Param("typeId") Long typeId);

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> dictTree(@Param("dictTypeId") Long dictTypeId);

    /**
     * where parentIds like ''
     */
    List<DictEntity> likeParentIds(@Param("dictId") Long dictId);

}
