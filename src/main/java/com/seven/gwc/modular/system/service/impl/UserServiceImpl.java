package com.seven.gwc.modular.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.node.FirstMenuNode;
import com.seven.gwc.core.node.MenuNode;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.shiro.service.UserAuthService;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.core.jwt.JwtTokenUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.dto.UserDTO;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.MenuService;
import com.seven.gwc.modular.system.service.UserService;
import com.seven.gwc.modular.system.vo.GetTokenVO;
import com.seven.gwc.modular.system.vo.UserUpdateVO;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * description : 用户服务实现类
 *
 * @author : GD
 * @date : 2019-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserAuthService userAuthService;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<UserEntity> selectUser(UserEntity userEntity) {
        return userMapper.userEntityList(userEntity);
    }

    @Override
    public int setStatus(String userId, String status, String currentUserId) {
        LambdaUpdateWrapper<UserEntity> lambdaUpdate = Wrappers.<UserEntity>lambdaUpdate();
        //update sys_user set status = #{status} where id = #{userId}
        lambdaUpdate.set(UserEntity::getStatus, status).
                set(UserEntity::getUpdateUser, currentUserId).
                set(UserEntity::getUpdateTime, new Date()).
                eq(UserEntity::getId, userId);
        return this.userMapper.update(null, lambdaUpdate);
    }

    @Override
    public List<MenuNode> getUserMenuNodes(Collection<String> roleList) {
        if (roleList == null || roleList.size() == 0) {
            return new ArrayList<>();
        } else {
            List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            return titles;
        }

    }

    @Override
    public List<FirstMenuNode> getFirstMenu(Collection<String> roleList) {
        return menuService.getFirstMenus(roleList);
    }

    @Override
    public List<MenuNode> getUserMenuNodes(Collection<String> roleList, String pcode) {
        if (roleList == null || roleList.size() == 0) {
            return new ArrayList<>();
        } else {
            List<MenuNode> menus = menuService.getMenusByRoleIds(roleList, pcode);
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            return titles;
        }
    }

    @Override
    public int setRoles(String userId, String roleIds, String currentUserId) {
        LambdaUpdateWrapper<UserEntity> lambdaUpdate1 = Wrappers.<UserEntity>lambdaUpdate();
        //update sys_user set role_id = #{roleIds} where user_id =#{userId}
        lambdaUpdate1.eq(UserEntity::getId, userId).
                set(UserEntity::getRoleId, roleIds).
                set(UserEntity::getUpdateUser, currentUserId).
                set(UserEntity::getUpdateTime, new Date());

        return userMapper.update(null, lambdaUpdate1);
    }

    @Override
    public UserEntity getByAccount(String account) {
        LambdaQueryWrapper<UserEntity> lambdaQuery = Wrappers.<UserEntity>lambdaQuery();
        //select * from sys_user where account = 账号 and status != 'DELETED'
        lambdaQuery.eq(UserEntity::getAccount, account).ne(UserEntity::getStatus, "DELETED");
        UserEntity userEntity = userMapper.selectOne(lambdaQuery);

        return userEntity;
    }

    @Override
    public boolean changePwd(String oldPassword, String newPassword, String userId) {

        UserEntity userEntity = this.getById(userId);
        String oldMd5 = ShiroKit.md5(oldPassword, userEntity.getSalt());

        if (userEntity.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPassword, userEntity.getSalt());
            userEntity.setPassword(newMd5);
            this.updateById(userEntity);
        } else {
            return false;
        }
        return true;
    }



    @Override
    public int changeAvatar(String portraitUrl) {
        ShiroUser currentUser = ShiroKit.getUser();
        LambdaUpdateWrapper<UserEntity> lambdaUpdate = Wrappers.<UserEntity>lambdaUpdate();
        lambdaUpdate.set(UserEntity::getAvatar, portraitUrl).eq(UserEntity::getId, currentUser.getId());
        return this.userMapper.update(null, lambdaUpdate);
    }

    @Override
    public void addUser(UserEntity user) {
        ShiroUser user1 = ShiroKit.getUser();

        // 判断账号是否重复
        UserEntity theUser = this.getByAccount(user.getAccount());
        if (theUser != null){
            throw new BusinessException(ErrorEnum.ERROR_USER_EXIST);
        }
        // 完善账号信息
        String salt = ShiroKit.getRandomSalt(5);
        String password = ShiroKit.md5(user.getPassword(), salt);
        //默认头像
        user.setAvatar("/common/images/head.png");
        user.setCreateUser(user1.getId());
        user.setCreateTime(new Date());
        user.setStatus(TypeStatesEnum.FREEZED.getCode());
        user.setPassword(password);
        user.setSalt(salt);

        this.save(user);
    }

    @Override
    public void refreshCurrentUser() {
        ShiroUser user = ShiroKit.getUserNotNull();
        String id = user.getId();
        UserEntity currentUser = this.getById(id);
        ShiroUser shiroUser = userAuthService.shiroUser(currentUser);
        ShiroUser lastUser = ShiroKit.getUser();
        BeanUtil.copyProperties(shiroUser, lastUser);
    }

    /**
     * 查询所有用户姓名
     * @return
     */
    @Override
    public List<ZTreeNode> tree() {
        return this.userMapper.tree();
    }

    @Override
    public BaseResult login(GetTokenVO getTokenVO) {
        //封装请求账号密码为shiro可验证的token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(getTokenVO.getAccount(), getTokenVO.getPassword().toCharArray());

        //获取数据库中的账号密码，准备比对
        UserEntity user = userMapper.selectByAccount(getTokenVO.getAccount().replaceAll(" ", ""));
        if (user == null){
            return new BaseResult().failure(ErrorEnum.ERROR_USER_NOT_EXIST);
        }
        String credentials = user.getPassword();
        ByteSource credentialsSalt = new Md5Hash(user.getSalt());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(), credentials, credentialsSalt, "");

        //校验用户账号密码
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.HASH_ALGORITHM_NAME);
        md5CredentialsMatcher.setHashIterations(ShiroKit.HASH_ITERATIONS);
        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(usernamePasswordToken, simpleAuthenticationInfo);

        if (passwordTrueFlag) {
            String jwtToken = JwtTokenUtil.generateToken(user);
            return new BaseResult().successContent("bearer;" + jwtToken);
        } else{
            return new BaseResult().failure(ErrorEnum.ERROR_USER_FAILURE);
        }
    }

    @Override
    public UserDTO getUser(String id) {
        return userMapper.getUser(id);
    }

    @Override
    public boolean changeUser(UserUpdateVO userUpdateVO, String userId) {
        UserEntity user = userMapper.selectById(userId);
        if (ToolUtil.isNotEmpty(userUpdateVO.getUserName())) {
            user.setName(userUpdateVO.getUserName());
        }
        if (ToolUtil.isNotEmpty(userUpdateVO.getPhone())) {
            user.setPhone(userUpdateVO.getPhone());
        }
        return userMapper.updateById(user) > 0;
    }

    @Override
    public UserEntity getUserById(String userId) {
        LambdaQueryWrapper<UserEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(UserEntity::getId, userId);
        return userMapper.selectOne(lambdaQuery);
    }
}
