package com.seven.gwc.modular.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.core.node.FirstMenuNode;
import com.seven.gwc.core.node.MenuNode;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.MenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * description : 菜单Mapper 接口
 *
 * @author : GD
 * @date : 2019-08-02
 */
public interface MenuMapper extends BaseMapper<MenuEntity> {

    List<Map<String, Object>> selectMenuTree(@Param("menuName") String menuName, @Param("level") String level);

    /**
     * 根据角色获取菜单
     */
    List<FirstMenuNode> getFirstMenusByRoleIds(Collection<Long> roleIds);

    /**
     * 根据角色获取菜单
     */
    List<MenuNode> getMenusByRoleIds(Collection<Long> roleIds);

    /**
     * 根据角色获取菜单
     */
    List<MenuNode> getMenusByRoleIdsAndPcode(Collection<Long> roleIds, String pcode);

    /**
     * 获取菜单列表树
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     */
    List<ZTreeNode> menuTreeListByMenuIds(Collection<Object> menuIds);

    /**
     * 获取资源url通过角色id
     */
    List<String> getResUrlsByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取pcodes like某个code的菜单列表
     */
    List<MenuEntity> getMenusLikePcodes(@Param("code") String code);

}
