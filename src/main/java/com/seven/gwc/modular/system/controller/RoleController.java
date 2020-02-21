package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.config.constant.ConfigConsts;
import com.seven.gwc.core.annotation.BussinessLog;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.dictmap.DeleteDict;
import com.seven.gwc.core.dictmap.RoleDict;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.log.LogObjectHolder;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.entity.RoleEntity;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.RoleService;
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

import java.util.Date;
import java.util.List;

/**
 * description : 角色控制器
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    private static String PREFIX = "/modular/system/role/";

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    /**
     * 跳转到角色首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "role";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping("/role_add")
    public String roleAdd() {
        return PREFIX + "role_add";
    }

    /**
     * 跳转到修改角色
     */
    @RequestMapping("/role_edit")
    public String roleEdit(Long id) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        RoleEntity role = this.roleService.getById(id);
        LogObjectHolder.me().set(role);
        return PREFIX + "role_edit";
    }

    /**
     * 跳转到查看角色
     */
    @RequestMapping("/role_detail")
    public String roleDetail(Long id) {
        return PREFIX + "role_detail";
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public BaseResultPage<RoleEntity> list(String roleName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<RoleEntity> roleEntitys = roleService.selectRole(roleName);
        PageInfo pageInfo = new PageInfo<>(roleEntitys);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加角色
     */
    @BussinessLog(value = "增加角色", key = "name", dict = RoleDict.class)
    @RequestMapping(value = "/add")
    @ResponseBody
    public BaseResult add(RoleEntity role) {
        ShiroUser user = ShiroKit.getUser();

        RoleEntity one = roleService.getOneByRoleName(role.getName());
        if (one != null) {
            return new BaseResult().failure(ErrorEnum.EXISTED_THE_ROLE_NAME);
        }
        if (role.getSort() == null) {
            role.setSort(0);
        }
        role.setCreateUser(user.getId());
        role.setCreateTime(new Date());
        roleService.save(role);
        return SUCCESS;
    }

    /**
     * 删除角色
     */
    @BussinessLog(value = "删除角色", key = "id", dict = DeleteDict.class)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long id) {
        if (ToolUtil.isEmpty(id)) {
            return new BaseResult().failure(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        RoleEntity one = roleService.getOneById(id);
        if (one != null) {
            return new BaseResult().failure(ErrorEnum.EXISTED_SUBORDINATE_DATA);
        } else if (id.equals(ConfigConsts.ADMIN_ROLE_ID)) {
            return new BaseResult().failure(ErrorEnum.CANT_OPERATION_ADMIN);
        }

        roleService.delRoleById(id);
        return SUCCESS;
    }

    /**
     * 编辑角色
     */
    @BussinessLog(value = "编辑角色", key = "name", dict = RoleDict.class)
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(RoleEntity role) {
        ShiroUser user = ShiroKit.getUser();

        if (ToolUtil.isOneEmpty(role, role.getName(), role.getPid())) {
            return new BaseResult().failure(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }

        RoleEntity one = roleService.getOneByIdAndRoleName(role.getId(), role.getName());

        if (one != null) {
            return new BaseResult().failure(ErrorEnum.EXISTED_THE_ROLE_NAME);
        } else if (role.getId().equals(ConfigConsts.ADMIN_ROLE_ID)) {
            return new BaseResult().failure(ErrorEnum.CANT_OPERATION_ADMIN);
        }

        if (role.getSort() == null) {
            role.setSort(0);
        }

        role.setUpdateUser(user.getId());
        role.setUpdateTime(new Date());
        roleService.editRole(role);
        return SUCCESS;
    }

    /**
     * 角色详情
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public RoleEntity detail(@PathVariable Long id) {
        RoleEntity roleEntity = roleService.getById(id);
        if (roleEntity.getPid().equals(0L)) {
            roleEntity.setPName("顶级");
        } else {
            roleEntity.setPName(CacheFactory.me().getRoleName(roleEntity.getPid().toString()));
        }
        return roleEntity;
    }

    /**
     * 跳转到权限分配
     */
    @RequestMapping(value = "/role_menu_assign/{id}")
    public String roleMenuAssign(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return PREFIX + "role_menu_assign";
    }

    /**
     * 配置权限
     */
    @BussinessLog(value = "配置权限", key = "id,ids", dict = RoleDict.class)
    @RequestMapping("/setAuthority")
    @ResponseBody
    public BaseResult setAuthority(@RequestParam("id") Long id, @RequestParam("menuIds") String menuIds) {
        if (ToolUtil.isOneEmpty(id)) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        this.roleService.setAuthority(id, menuIds);
        return SUCCESS;
    }

    /**
     * 获取角色列表，通过用户id
     */
    @RequestMapping(value = "/roleTreeListByUserId/{id}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Long id) {
        UserEntity theUser = this.userService.getById(id);
        String roleId = theUser.getRoleId();
        if (ToolUtil.isEmpty(roleId)) {
            return this.roleService.roleTreeList();
        } else {
            String[] strArray = roleId.split(",");
            //转化成Long[]
            Long[] longArray = new Long[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                longArray[i] = Long.valueOf(strArray[i]);
            }
            return this.roleService.roleTreeListByRoleId(longArray);
        }
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList = this.roleService.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

}

