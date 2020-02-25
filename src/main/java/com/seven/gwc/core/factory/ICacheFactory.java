package com.seven.gwc.core.factory;



import java.util.List;

/**
 * 常量生产工厂的接口
 */
public interface ICacheFactory {

    /**
     * 根据用户id获取用户名称
     */
    String getUserNameById(String userId);

    /**
     * 根据用户id获取用户账号
     */
    String getUserAccountById(Long userId);

    /**
     * 通过角色ids获取角色名称
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(Long roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    String getSingleRoleTip(Long roleId);

    /**
     * 通过岗位ids获取岗位名称
     */
    String getPositionName(String positionIds);

    /**
     * 通过岗位id获取岗位名称
     */
    String getSinglePositionName(Long positionId);

    /**
     * 获取部门名称
     */
    String getDeptName(String deptId);

    /**
     * 获取菜单的名称们(多个)
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称
     */
    String getMenuName(Long menuId);

    /**
     * 获取所有父部门id
     */
    List<Long> getParentDeptIds(Long deptId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuIdByCode(String code);

}
