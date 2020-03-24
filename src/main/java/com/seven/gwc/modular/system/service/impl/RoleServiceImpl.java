package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.config.constant.CacheConsts;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.util.CacheUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.RelationMapper;
import com.seven.gwc.modular.system.dao.RoleMapper;
import com.seven.gwc.modular.system.entity.RelationEntity;
import com.seven.gwc.modular.system.entity.RoleEntity;
import com.seven.gwc.modular.system.service.RoleService;
import com.seven.gwc.modular.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description : 角色服务实现类
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private UserService userService;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<RoleEntity> selectRole(RoleEntity roleEntity) {
        /*LambdaQueryWrapper<RoleEntity> lambdaQuery = Wrappers.<RoleEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(roleName), RoleEntity::getName, roleName)
                .orderByAsc(RoleEntity::getSort)
                .orderByDesc(RoleEntity::getCreateTime);*/
        List<RoleEntity> list = roleMapper.getRoleList(roleEntity);
        for (RoleEntity role : list) {
            if (role.getPid().equals("0")) {
                role.setPName("顶级");
            } else {
                RoleEntity entity = roleMapper.selectById(role.getPid());
                if (ToolUtil.isNotEmpty(entity)) {
                    role.setPName(entity.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<ZTreeNode> roleTreeList() {
        return this.roleMapper.roleTreeList();
    }

    @Override
    public List<ZTreeNode> roleTreeListByRoleId(String[] roleId) {
        return this.roleMapper.roleTreeListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAuthority(String roleId, String menuIds) {

        // 删除该角色所有的权限
        LambdaQueryWrapper<RelationEntity> lambdaQuery = Wrappers.<RelationEntity>lambdaQuery();
        lambdaQuery.eq(RelationEntity::getRoleId, roleId);
        relationMapper.delete(lambdaQuery);

        // 添加新的权限
        for (String menuId : menuIds.split(SysConsts.STR_COMMA)) {
            RelationEntity relation = new RelationEntity();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            this.relationMapper.insert(relation);
        }
        // 刷新当前用户的权限
        userService.refreshCurrentUser();
    }

    @Override
    public RoleEntity getOneById(String id) {
        LambdaQueryWrapper<RoleEntity> lambdaQuery = Wrappers.<RoleEntity>lambdaQuery();
        lambdaQuery.eq(RoleEntity::getPid, id);
        return roleMapper.selectOne(lambdaQuery);
    }

    @Override
    public RoleEntity getOneByRoleName(String roleName) {
        LambdaQueryWrapper<RoleEntity> lambdaQuery = Wrappers.<RoleEntity>lambdaQuery();
        lambdaQuery.eq(RoleEntity::getName, roleName);
        return roleMapper.selectOne(lambdaQuery);
    }

    @Override
    public RoleEntity getOneByIdAndRoleName(String id, String roleName) {
        LambdaQueryWrapper<RoleEntity> lambdaQuery = Wrappers.<RoleEntity>lambdaQuery();
        lambdaQuery.eq(RoleEntity::getName, roleName).ne(RoleEntity::getId, id);
        return roleMapper.selectOne(lambdaQuery);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRoleById(String roleId) {
        //缓存被删除的角色名称
        CacheFactory.me().getSingleRoleName(roleId);
        //删除角色
        this.roleMapper.deleteById(roleId);
        //删除该角色所有的权限
        LambdaUpdateWrapper<RelationEntity> lambdaUpdate = Wrappers.<RelationEntity>lambdaUpdate();
        lambdaUpdate.eq(RelationEntity::getRoleId, roleId);
        relationMapper.delete(lambdaUpdate);
        //删除缓存
        CacheUtil.removeAll(CacheConsts.ROLE_CONSTANT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRole(RoleEntity roleId) {
        //更改角色
        this.updateById(roleId);
        //删除缓存
        CacheUtil.removeAll(CacheConsts.ROLE_CONSTANT);
    }


}
