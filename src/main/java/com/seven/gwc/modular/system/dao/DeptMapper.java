package com.seven.gwc.modular.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.DeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 部门Mapper 接口
 *
 * @author : GD
 * @since : 2019-08-02
 */
public interface DeptMapper extends BaseMapper<DeptEntity> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取部门菜单列表（s树形）
     */
    List<DeptEntity> liseTree(@Param("deptName") String deptName);


}
