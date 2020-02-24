package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.node.FirstMenuNode;
import com.seven.gwc.core.node.MenuNode;
import com.seven.gwc.modular.system.entity.UserEntity;

import java.util.Collection;
import java.util.List;

/**
 * description : 用户服务类
 *
 * @author : GD
 * @date : 2019-08-01
 */

public interface UserService extends IService<UserEntity> {

    /**
     * 用户查询列表
     *
     * @param userEntity 用户实体
     * @return
     */
    List<UserEntity> selectUser(UserEntity userEntity);

    /**
     * 编辑用户状态
     *
     * @param userId 用户id
     * @param status 状态
     * @return
     */
    int setStatus(String userId, String status, String currentUserId);

    /**
     * 获取用户菜单列表
     *
     * @param roleList 角色id集合
     * @return
     */
    List<MenuNode> getUserMenuNodes(Collection<Long> roleList);


    /**
     * 获取一级菜单
     *
     * @param roleList 角色集合
     * @return
     */
    List<FirstMenuNode> getFirstMenu(Collection<Long> roleList);

    /**
     * 获取用户菜单列表
     *
     * @param roleList 角色id集合
     * @param pcode
     * @return
     */
    List<MenuNode> getUserMenuNodes(Collection<Long> roleList, String pcode);


    /**
     * 设置用户的角色
     *
     * @param userId  用户id
     * @param roleIds 角色id (例如 1,2)
     * @return
     */
    int setRoles(String userId, String roleIds, String currentUserId);

    /**
     * 通过账号获取用户
     *
     * @param account 账号
     * @return
     */
    UserEntity getByAccount(String account);

    /**
     * 编辑密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    void changePwd(String oldPassword, String newPassword);

    /**
     * 设置用户头像
     *
     * @param portraitUrl 头像地址
     * @return
     */
    int changeAvatar(String portraitUrl);

    /**
     * 添加用戶
     *
     * @param user 用户实体
     */
    void addUser(UserEntity user);

    /**
     * 刷新当前登录用户的信息
     */
    void refreshCurrentUser();


}
