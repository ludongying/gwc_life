package com.seven.gwc.modular.system.service;

import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.DeptEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description: 部门服务类
 *
 * @author : GD
 * @date : 2019-08-02
 */

public interface DeptService extends IService<DeptEntity> {

    /**
     * @param deptName 部门名称
     * @param deptId   部门Id
     * @return 部门查询列表
     */
    List<DeptEntity> selectDept(String deptName, Long deptId);

    /**
     * 获取部门Tree
     */
    List<ZTreeNode> tree();

    /**
     * 获取部门菜单列表（s树形）  新增、编辑时用
     */
    List<DeptEntity> listTree(String deptName);

    /**
     * 新增部门
     */
    void saveDept(DeptEntity deptEntity);

    /**
     * 修改部门
     */
    void updateDept(DeptEntity deptEntity);

    /**
     * 获取所有部门
     */
    List<DeptEntity> selectDeptList();

    /**
     * @param id  部门Id
     * @param pid 父级部门Id
     * @return 校验是否是自己或自己的下级
     */
    boolean jude(Long id, Long pid);

    /**
     * @param deptId 数据id
     * @return 根据id获取数据
     */
    DeptEntity getDeptById(Long deptId);
}
