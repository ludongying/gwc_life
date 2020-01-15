package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.config.constant.ConfigConsts;
import com.seven.gwc.core.annotation.Permission;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * description : 用户控制器
 *
 * @author : GD
 * @date : 2019-08-01
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    private String PREFIX = "/modular/system/user/";

    @Autowired
    private UserService userService;

    /**
     * 跳转到用户首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user";
    }

    /**
     * 跳转到添加用户
     */
    @RequestMapping("/user_add")
    public String userAdd(String deptId, String deptName) {
        System.out.println(deptId);
        System.out.println(deptName);
        return PREFIX + "user_add";
    }

    /**
     * 跳转到修改用户
     */
    @RequestMapping("/user_edit")
    public String userEdit(Long id) {
        return PREFIX + "user_edit";
    }

    /**
     * 跳转到查看用户
     */
    @RequestMapping("/user_detail")
    public String userDetail(Long id) {
        return PREFIX + "user_detail";
    }

    /**
     * 获取用户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public BaseResultPage<UserEntity> list(UserEntity userEntity) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<UserEntity> userEntities = userService.selectUser(userEntity);
        PageInfo pageInfo = new PageInfo<>(userEntities);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 新增用户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public BaseResult add(@Valid UserEntity user) {
        user.setCreateTime(new Date());
        user.setStatus(TypeStatesEnum.FREEZED.getCode());
        userService.addUser(user);
        return SUCCESS;
    }

    /**
     * 删除用户（物理删除）
     */
    @Permission({ConfigConsts.ADMIN_NAME})
    @RequestMapping(value = "/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long id) {
        if (id.equals(ConfigConsts.ADMIN_ID)) {
            return new BaseResult().failure(ErrorEnum.CANT_OPERATION_ADMIN);
        }
        userService.removeById(id);
        return SUCCESS;
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(UserEntity user) {
        if (user.getId().equals(ConfigConsts.ADMIN_ID)) {
            return new BaseResult().failure(ErrorEnum.CANT_OPERATION_ADMIN);
        }
        userService.updateById(user);
        user.setUpdateTime(new Date());
        return SUCCESS;
    }

    /**
     * 用户详情
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public UserEntity detail(@PathVariable Long id) {
        UserEntity userEntity = userService.getById(id);
        userEntity.setDeptName(CacheFactory.me().getDeptName(userEntity.getDeptId()));
        return userEntity;
    }


    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public BaseResult freeze(@RequestParam Long id) {
        //不能冻结超级管理员
        if (id.equals(ConfigConsts.ADMIN_ID)) {
            return new BaseResult().failure(ErrorEnum.CANT_OPERATION_ADMIN);
        }
        this.userService.setStatus(id, TypeStatesEnum.FREEZED.getCode());
        return SUCCESS;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public BaseResult unfreeze(@RequestParam Long id) {
        this.userService.setStatus(id, TypeStatesEnum.OK.getCode());
        return SUCCESS;
    }

    /**
     * 删除用户（逻辑删除）
     */
    @Permission(ConfigConsts.ADMIN_NAME)
    @RequestMapping("/deleteLogic")
    @ResponseBody
    public BaseResult deleteLogic(@RequestParam Long id) {
        //不能冻结超级管理员
        if (id.equals(ConfigConsts.ADMIN_ID)) {
            throw new BusinessException(ErrorEnum.CANT_OPERATION_ADMIN);
        }
        this.userService.setStatus(id, TypeStatesEnum.DELETED.getCode());
        return SUCCESS;
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("/reset")
    @ResponseBody
    public BaseResult reset(@RequestParam Long id) {
        UserEntity user = this.userService.getById(id);
        user.setSalt(ToolUtil.getRandomString(5));
        user.setPassword(ToolUtil.md5("888888", user.getSalt()));
        this.userService.updateById(user);
        return SUCCESS;
    }

    /**
     * 跳转到角色分配页面
     */
    @RequestMapping("/user_role_assign/{id}")
    public String roleRoleAssign(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return PREFIX + "user_role_assign";
    }

    /**
     * 分配角色
     */
    @RequestMapping("/setRole")
    @ResponseBody
    public BaseResult setRole(@RequestParam("id") Long id, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(id)) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        if (id.equals(ConfigConsts.ADMIN_ROLE_ID)) {
            //不能修改超级管理员
            throw new BusinessException(ErrorEnum.CANT_OPERATION_ADMIN);
        }
        this.userService.setRoles(id, roleIds);
        return SUCCESS;
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        if (ToolUtil.isOneEmpty(oldPassword, newPassword)) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        this.userService.changePwd(oldPassword, newPassword);
        return SUCCESS;
    }

}

