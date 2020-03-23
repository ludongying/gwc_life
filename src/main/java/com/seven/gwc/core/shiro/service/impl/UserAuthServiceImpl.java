package com.seven.gwc.core.shiro.service.impl;

import cn.hutool.core.convert.Convert;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.shiro.service.UserAuthService;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.core.util.SpringContextUtil;
import com.seven.gwc.modular.system.dao.MenuMapper;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.UserService;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class UserAuthServiceImpl extends BaseController implements UserAuthService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserService userService;

    public static UserAuthService me() {
        return SpringContextUtil.getBean(UserAuthService.class);
    }

    @Override
    public UserEntity user(String account) {

        UserEntity userEntity = userService.getByAccount(account);

        // 账号不存在
        if (null == userEntity) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (!userEntity.getStatus().equals(TypeStatesEnum.OK.getCode())) {
            throw new LockedAccountException();
        }
        return userEntity;
    }

    @Override
    public ShiroUser shiroUser(UserEntity userEntity) {

        ShiroUser shiroUser = ShiroKit.createShiroUser(userEntity);

        //用户角色数组
        String[] roleArray = Convert.toStrArray(userEntity.getRoleId());

        //获取用户角色列表
        List<String> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        if (roleArray != null) {
            for (String roleId : roleArray) {
                roleList.add(roleId);
                roleNameList.add(CacheFactory.me().getSingleRoleName(roleId.toString()));
            }
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);


        //用户岗位数组
        String[] positionArray = Convert.toStrArray(userEntity.getPositionId());

        //获取用户岗位列表
        List<String> positionList = new ArrayList<>();
        List<String> positionNameList = new ArrayList<>();
        if (positionArray != null) {
            for (String positionId : positionArray) {
                positionList.add(positionId);
                positionNameList.add(CacheFactory.me().getSinglePositionName(positionId.toString()));
            }
        }
        shiroUser.setPositionList(positionList);
        shiroUser.setPositionNames(positionNameList);


        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(String roleId) {
        return menuMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public String findRoleNameByRoleId(String roleId) {
        return CacheFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, UserEntity userEntity, String realmName) {
        String credentials = userEntity.getPassword();

        // 密码加盐处理
        String source = userEntity.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
