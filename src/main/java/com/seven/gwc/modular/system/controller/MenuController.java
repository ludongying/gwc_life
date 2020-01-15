package com.seven.gwc.modular.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.config.constant.ConfigConsts;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dto.MenuEntityDTO;
import com.seven.gwc.modular.system.entity.MenuEntity;
import com.seven.gwc.modular.system.service.MenuService;
import com.seven.gwc.modular.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * description : 菜单控制器
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/modular/system/menu/";

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    /**
     * 跳转到菜单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu";
    }

    /**
     * 跳转到添加菜单
     */
    @RequestMapping("/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add";
    }

    /**
     * 跳转到修改菜单
     */
    @RequestMapping("/menu_edit")
    public String menuEdit(Long id) {
        return PREFIX + "menu_edit";
    }

    @RequestMapping("/menu_look")
    public String menuLook(Long id) {
        return PREFIX + "menu_look";
    }

    /**
     * 跳转到查看菜单
     */
    @RequestMapping("/menu_detail")
    public String menuDetail(Long id) {
        return PREFIX + "menu_detail";
    }

    /**
     * 获取菜单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public BaseResultPage<MenuEntity> list(String menuName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MenuEntity> menus = menuService.selectMenu(menuName);
        PageInfo pageInfo = new PageInfo<>(menus);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 获取菜单列表（s树形）
     */
    @RequestMapping(value = "/listTree")
    @ResponseBody
    public Object listTree(String menuName, String level) {
        List<Map<String, Object>> menus = this.menuService.selectMenuTree(menuName, level);
        return new BaseResultPage().treeData(menus);
    }


    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public BaseResult add(MenuEntityDTO menu) {
        ShiroUser user = ShiroKit.getUser();

        if (menu.getSort() == null) {
            menu.setSort(0);
        }
        menuService.addMenu(menu, user);
        return SUCCESS;
    }

    /**
     * 删除菜单
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long id) {
        menuService.removeById(id);
        return SUCCESS;
    }

    /**
     * 修改菜单
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(MenuEntityDTO menu) {
        ShiroUser user = ShiroKit.getUser();
        if (menu.getSort() == null) {
            menu.setSort(0);
        }
        //如果修改了编号，则该菜单的子菜单也要修改对应编号
        this.menuService.updateMenu(menu, user);
        //刷新当前用户菜单
        this.userService.refreshCurrentUser();
        return SUCCESS;
    }

    /**
     * 菜单详情
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public MenuEntity detail(@PathVariable Long id) {
        MenuEntity menuEntity = menuService.getById(id);
        //设置pid和父级名称
        menuEntity.setPId(CacheFactory.me().getMenuIdByCode(menuEntity.getPcode()));
        menuEntity.setPcodeName(CacheFactory.me().getMenuNameByCode(menuEntity.getPcode()));
        return menuEntity;
    }

    /**
     * 获取角色的菜单列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{id}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Long id) {
        List<Object> menuIds = this.menuService.getMenuIdsByRoleId(id);
        if (ToolUtil.isEmpty(menuIds)) {
            return this.menuService.menuTreeList();
        } else {
            return this.menuService.menuTreeListByMenuIds(menuIds);
        }
    }

    @RequestMapping(value = "/getUserListById")
    @ResponseBody
    public JSONObject getUserListById(Long id) {
        return menuService.getUserListById(id);
    }

    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public BaseResult freeze(@RequestParam Long id) {
        //不能冻结系统管理
        if (id.equals(ConfigConsts.SYSTEM_ID)) {
            return new BaseResult().failure(ErrorEnum.CANT_OPERATION_ADMIN);
        }
        this.menuService.setStatus(id, TypeStatesEnum.FREEZED.getCode());
        return SUCCESS;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public BaseResult unfreeze(@RequestParam Long id) {
        this.menuService.setStatus(id, TypeStatesEnum.OK.getCode());
        return SUCCESS;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

}

