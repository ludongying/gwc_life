package com.seven.gwc.modular.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.node.FirstMenuNode;
import com.seven.gwc.core.node.MenuNode;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.MenuMapper;
import com.seven.gwc.modular.system.dao.RelationMapper;
import com.seven.gwc.modular.system.dao.RoleMapper;
import com.seven.gwc.modular.system.dto.MenuEntityDTO;
import com.seven.gwc.modular.system.entity.MenuEntity;
import com.seven.gwc.modular.system.entity.RelationEntity;
import com.seven.gwc.modular.system.entity.RoleEntity;
import com.seven.gwc.modular.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * description : 菜单服务实现类
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RelationMapper relationMapper;

    @Override
    public List<MenuEntity> selectMenu(String menuName) {
        LambdaQueryWrapper<MenuEntity> lambdaQuery = Wrappers.<MenuEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(menuName), MenuEntity::getName, menuName);
        return menuMapper.selectList(lambdaQuery);
    }

    @Override
    public List<Map<String, Object>> selectMenuTree(String menuName, String level) {
        List<Map<String, Object>> maps = this.baseMapper.selectMenuTree(menuName, level);

        if (maps == null) {
            maps = new ArrayList<>();
        }
        //创建根节点
        MenuEntity menu = new MenuEntity();
        menu.setId("-1");
        menu.setName("根节点");
        menu.setCode("0");
        menu.setPcode("-2");

        maps.add(BeanUtil.beanToMap(menu));

        return maps;
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(Collection<String> roleIds) {
        List<MenuNode> menus = this.menuMapper.getMenusByRoleIds(roleIds);
        return menus;
    }

    @Override
    public List<FirstMenuNode> getFirstMenus(Collection<String> roleIds) {
        if (Objects.nonNull(roleIds)) {
            return menuMapper.getFirstMenusByRoleIds(roleIds);
        }
        return null;
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(Collection<String> roleIds, String pcode) {
        List<MenuNode> menus = this.menuMapper.getMenusByRoleIdsAndPcode(roleIds, pcode);
        return menus;
    }

    @Override
    public List<Object> getMenuIdsByRoleId(String roleId) {
        LambdaQueryWrapper<RelationEntity> lambdaQuery = Wrappers.<RelationEntity>lambdaQuery();
        //select menu_id from sys_relation where role_id = 1
        lambdaQuery.select(RelationEntity::getMenuId).eq(RelationEntity::getRoleId, roleId);
        List<Object> objects = relationMapper.selectObjs(lambdaQuery);

        return objects;
    }

    @Override
    public List<ZTreeNode> menuTreeList() {
        return this.menuMapper.menuTreeList();
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(Collection<Object> menuIds) {
        return this.menuMapper.menuTreeListByMenuIds(menuIds);
    }

    @Override
    public int setStatus(String menuId, String status) {
        LambdaUpdateWrapper<MenuEntity> lambdaUpdate = Wrappers.<MenuEntity>lambdaUpdate();
        //update sys_menu set status = #{status} where id = #{userId}
        lambdaUpdate.set(MenuEntity::getStatus, status).eq(MenuEntity::getId, menuId);
        return this.menuMapper.update(null, lambdaUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(MenuEntityDTO menuDto, ShiroUser user) {
        if (ToolUtil.isOneEmpty(menuDto, menuDto.getCode(), menuDto.getName(), menuDto.getPid(), menuDto.getMenuFlag(), menuDto.getUrl())) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        //判断是否已经存在该编号
        String existedMenuName = CacheFactory.me().getMenuNameByCode(menuDto.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new BusinessException(ErrorEnum.EXISTED_THE_MENU);
        }

        //组装属性，设置父级菜单编号
        MenuEntity resultMenu = this.menuSetPcode(menuDto);
        resultMenu.setCreateUser(user.getId());
        resultMenu.setCreateTime(new Date());
        resultMenu.setStatus(TypeStatesEnum.OK.getCode());

        this.save(resultMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(MenuEntityDTO menuDto, ShiroUser user) {
        //如果菜单为空
        if (menuDto == null || ToolUtil.isOneEmpty(menuDto.getId(), menuDto.getCode())) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        //获取旧的菜单
        String id = menuDto.getId();
        MenuEntity menu = this.getById(id);

        if (menu == null) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        //设置父级菜单编号
        MenuEntity resultMenu = this.menuSetPcode(menuDto);
        resultMenu.setUpdateUser(user.getId());
        resultMenu.setUpdateTime(new Date());
        //查找该节点的子集合,并修改相应的pcodes和level(因为修改菜单后,层级可能变化了)
        updateSubMenuLevels(menu, resultMenu);

        this.updateById(resultMenu);
    }

    @Override
    public JSONObject getUserListById(String id) {
        JSONObject jsonObject = new JSONObject();
        LambdaQueryWrapper<RelationEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(RelationEntity::getMenuId, id);
        List<RelationEntity> relationEntityList = relationMapper.selectList(lambdaQuery);
        if (relationEntityList.size() > 0) {
            List<RoleEntity> roleEntityList = new ArrayList<>();
            for (RelationEntity relation : relationEntityList) {
                LambdaQueryWrapper<RoleEntity> roleLambdaQuery = Wrappers.lambdaQuery();
                roleLambdaQuery.eq(RoleEntity::getId, relation.getRoleId());
                RoleEntity roleEntity = roleMapper.selectOne(roleLambdaQuery);
                if (ToolUtil.isNotEmpty(roleEntity)) {
                    roleEntityList.add(roleEntity);
                }
            }
            jsonObject.put("data", roleEntityList);
        } else {
            jsonObject.put("data", null);
        }
        jsonObject.put("code", 0);
        return jsonObject;
    }


    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    public MenuEntity menuSetPcode(MenuEntityDTO menuParam) {

        MenuEntity resultMenu = new MenuEntity();
        BeanUtil.copyProperties(menuParam, resultMenu);

        if (ToolUtil.isEmpty(menuParam.getPid()) || menuParam.getPid().equals("0")) {
            resultMenu.setPcode("0");
            resultMenu.setPcodes("[0],");
            resultMenu.setLevels(1);
        } else {
            String pid = menuParam.getPid();
            MenuEntity pMenu = this.getById(pid);
            Integer pLevels = pMenu.getLevels();
            resultMenu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menuParam.getCode().equals(menuParam.getPcode())) {
                throw new BusinessException(ErrorEnum.MENU_PCODE_COINCIDENCE);
            }

            resultMenu.setLevels(pLevels + 1);
            resultMenu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }

        return resultMenu;
    }

    /**
     * 更新所有子菜单的结构
     *
     * @param oldMenu 原来的菜单
     * @param newMenu 新菜单
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSubMenuLevels(MenuEntity oldMenu, MenuEntity newMenu) {

        List<MenuEntity> menus = menuMapper.getMenusLikePcodes(oldMenu.getCode());

        for (MenuEntity menu : menus) {

            //更新pcode
            if (oldMenu.getCode().equals(menu.getPcode())) {
                menu.setPcode(newMenu.getCode());
            }

            //更新pcodes
            String oldPcodesPrefix = oldMenu.getPcodes() + "[" + oldMenu.getCode() + "],";
            String oldPcodesSuffix = menu.getPcodes().substring(oldPcodesPrefix.length());
            String menuPcodes = newMenu.getPcodes() + "[" + newMenu.getCode() + "]," + oldPcodesSuffix;
            menu.setPcodes(menuPcodes);

            //更新levels
            int level = StrUtil.count(menuPcodes, "[");
            menu.setLevels(level);

            this.updateById(menu);
        }

    }


}
