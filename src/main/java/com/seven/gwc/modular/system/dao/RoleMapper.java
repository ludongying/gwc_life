package com.seven.gwc.modular.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 角色Mapper 接口
 *
 * @author : GD
 * @date : 2019-08-02
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    List<RoleEntity> getRoleList(@Param("role") RoleEntity roleEntity, @Param("total") Integer total, @Param("size") Integer size);

    Integer getListSize(@Param("role") RoleEntity roleEntity);

    /**
     * 获取角色列表树
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
     * 根据角色名称获取角色列表
     *
     * @param roleName 角色名称
     * @return
     */
    List<RoleEntity> roleEntityList(@Param("roleName") String roleName);

}