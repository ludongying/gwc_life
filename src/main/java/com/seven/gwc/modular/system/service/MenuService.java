package com.seven.gwc.modular.system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.node.FirstMenuNode;
import com.seven.gwc.core.node.MenuNode;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.dto.MenuEntityDTO;
import com.seven.gwc.modular.system.entity.MenuEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * description : 菜单服务类
 *
 * @author : GD
 * @date : 2019-08-02
 */

public interface MenuService extends IService<MenuEntity> {

    /**
     * @param menuName 菜单名称
     * @return 菜单查询列表
     */
    List<MenuEntity> selectMenu(String menuName);

    /**
     * @param menuName 菜单名称
     * @param level    层级
     * @return 菜单树形列表
     */
    List<Map<String, Object>> selectMenuTree(String menuName, String level);

    /**
     * @param roleIds 多角色Id
     * @return 角色获取菜单
     */
    List<MenuNode> getMenusByRoleIds(Collection<String> roleIds);


    /**
     * 获取一级菜单
     *
     * @param roleIds
     * @return
     */
    List<FirstMenuNode> getFirstMenus(Collection<String> roleIds);

    /**
     * @param roleIds 多角色Id
     * @return 角色获取菜单
     * @Param pcode  父级菜单的code
     */
    List<MenuNode> getMenusByRoleIds(Collection<String> roleIds, String pcode);

    /**
     * @param roleId 角色Id
     * @return 根据条件查询菜单
     */
    List<Object> getMenuIdsByRoleId(String roleId);

    /**
     * 获取菜单列表树
     */
    List<ZTreeNode> menuTreeList();

    /**
     * @param menuIds 菜单Id
     * @return 根据ID获取菜单列表
     */
    List<ZTreeNode> menuTreeListByMenuIds(Collection<Object> menuIds);

    /**
     * @param menuId 菜单ID
     * @param status 菜单状态
     * @return 修改菜单状态
     */
    int setStatus(String menuId, String status);

    /**
     * 添加菜单
     */
    void addMenu(MenuEntityDTO menuDto, ShiroUser user);

    /**
     * 更新菜单
     */
    void updateMenu(MenuEntityDTO menuDto, ShiroUser user);

    JSONObject getUserListById(String id);
}
