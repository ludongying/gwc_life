package com.seven.gwc.core.shiro.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.UserEntity;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.List;

/**
 * 定义shirorealm所需数据的接口
 */
public interface UserAuthService {

    /**
     * 根据账号获取登录用户
     */
    UserEntity user(String account);

    /**
     * 根据系统用户获取Shiro的用户
     */
    ShiroUser shiroUser(UserEntity userEntity);

    /**
     * 获取权限列表通过角色id
     */
    List<String> findPermissionsByRoleId(Long roleId);

    /**
     * 根据角色id获取角色名称
     */
    String findRoleNameByRoleId(Long roleId);

    /**
     * 获取shiro的认证信息
     */
    SimpleAuthenticationInfo info(ShiroUser shiroUser, UserEntity userEntity, String realmName);

}
