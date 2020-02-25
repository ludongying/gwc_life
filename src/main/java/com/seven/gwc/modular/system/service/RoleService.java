package com.seven.gwc.modular.system.service;

import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.RoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 角色服务类
 *
 * @author : GD
 * @since : 2019-08-02
 */

public interface RoleService extends IService<RoleEntity> {

    /**
     * 角色查询列表
     *
     * @param name 角色名称
     * @return
     */
    List<RoleEntity> selectRole(String name);

    /**
     * 获取角色列表树
     * @return
     */
    List<ZTreeNode> roleTreeList();

    /**
     * 获取角色列表树
     *
     * @param roleId 角色id集合
     * @return
     */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);

    /**
     * 设置某个角色的权限
     *
     * @param roleId 角色id
     * @param ids    菜单id集合 （例如1,2）
     */
    void setAuthority(String roleId, String ids);

    /**
     * 根据角色父id 获取角色
     *
     * @param pid 父id
     * @return
     */
    RoleEntity getOneById(Long pid);

    /**
     * 根据角色名称获取角色
     *
     * @param roleName
     * @return
     */
    RoleEntity getOneByRoleName(String roleName);

    /**
     * 根据条件获取角色
     *
     * @param id       {@code ne id}
     * @param roleName {@code eq roleName}
     * @return
     */
    RoleEntity getOneByIdAndRoleName(String id, String roleName);

    /**
     * 根据角色id删除角色
     *
     * @param roleId 角色id
     */
    void delRoleById(Long roleId);

    /**
     * 编辑角色
     *
     * @param roleEntity 角色实体
     */
    void editRole(RoleEntity roleEntity);
}
