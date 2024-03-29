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
    String getUserAccountById(String userId);

    /**
     * 通过角色ids获取角色名称
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(String roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    String getSingleRoleTip(String roleId);

    /**
     * 通过岗位ids获取岗位名称
     */
    String getPositionName(String positionIds);

    /**
     * 通过岗位id获取岗位名称
     */
    String getSinglePositionName(String positionId);

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
    String getMenuName(String menuId);

    /**
     * 获取所有父部门id
     */
    List<Long> getParentDeptIds(String deptId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuIdByCode(String code);

}
