package com.seven.gwc.core.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.gwc.config.constant.CacheConsts;
import com.seven.gwc.config.constant.CacheKeyConsts;
import com.seven.gwc.core.util.SpringContextUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.*;
import com.seven.gwc.modular.system.entity.*;
import com.seven.gwc.modular.system.dao.*;
import com.seven.gwc.modular.system.entity.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量的生产工厂
 */
@Component
@DependsOn("springContextUtil")
public class CacheFactory implements ICacheFactory {

    private RoleMapper roleMapper = SpringContextUtil.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextUtil.getBean(DeptMapper.class);
    private UserMapper userMapper = SpringContextUtil.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextUtil.getBean(MenuMapper.class);
    private PositionMapper positionMapper = SpringContextUtil.getBean(PositionMapper.class);

    public static ICacheFactory me() {
        return SpringContextUtil.getBean("cacheFactory");
    }

    @Override
    @Cacheable(value = CacheConsts.USER_CONSTANT, key = "'" + CacheKeyConsts.SINGLE_USER_NAME + "'+#userId")
    public String getUserNameById(Long userId) {
        UserEntity user = userMapper.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }


    @Override
    public String getUserAccountById(Long userId) {
        UserEntity user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    @Override
    @Cacheable(value = CacheConsts.ROLE_CONSTANT, key = "'" + CacheKeyConsts.ROLES_NAME + "'+#roleIds")
    public String getRoleName(String roleIds) {
        if (ToolUtil.isEmpty(roleIds)) {
            return "";
        }
        Long[] roles = Convert.toLongArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (Long role : roles) {
            RoleEntity roleObj = roleMapper.selectById(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrUtil.removeSuffix(sb.toString(), ",");
    }

    @Override
    @Cacheable(value = CacheConsts.ROLE_CONSTANT, key = "'" + CacheKeyConsts.SINGLE_ROLE_NAME + "'+#roleId")
    public String getSingleRoleName(Long roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleEntity roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    @Override
    @Cacheable(value = CacheConsts.ROLE_CONSTANT, key = "'" + CacheKeyConsts.SINGLE_ROLE_TIP + "'+#roleId")
    public String getSingleRoleTip(Long roleId) {
        if (0 == roleId) {
            return "--";
        }
        RoleEntity roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getDescription();
        }
        return "";
    }


    @Override
    @Cacheable(value = CacheConsts.POSITION_CONSTANT, key = "'" + CacheKeyConsts.POSITIONS_NAME + "'+#positionIds")
    public String getPositionName(String positionIds) {
        if (ToolUtil.isEmpty(positionIds)) {
            return "";
        }
        Long[] positions = Convert.toLongArray(positionIds);
        StringBuilder sb = new StringBuilder();
        for (Long position : positions) {
            PositionEntity positionObj = positionMapper.selectById(position);
            if (ToolUtil.isNotEmpty(positionObj) && ToolUtil.isNotEmpty(positionObj.getName())) {
                sb.append(positionObj.getName()).append(",");
            }
        }
        return StrUtil.removeSuffix(sb.toString(), ",");
    }

    @Override
    @Cacheable(value = CacheConsts.POSITION_CONSTANT, key = "'" + CacheKeyConsts.SINGLE_POSITION_NAME + "'+#positionId")
    public String getSinglePositionName(Long positionId) {
        if (0 == positionId) {
            return "--";
        }
        PositionEntity positionObj = positionMapper.selectById(positionId);
        if (ToolUtil.isNotEmpty(positionObj) && ToolUtil.isNotEmpty(positionObj.getName())) {
            return positionObj.getName();
        }
        return "";
    }


    @Override
    @Cacheable(value = CacheConsts.DEPT_CONSTANT, key = "'" + CacheKeyConsts.SINGLE_DEPT_NAME + "'+#deptId")
    public String getDeptName(Long deptId) {
        if (deptId == null) {
            return "";
        } else if (deptId == 0L) {
            return "顶级";
        } else {
            DeptEntity dept = deptMapper.selectById(deptId);
            if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullName())) {
                return dept.getFullName();
            }
            return "";
        }
    }

    @Override
    public String getMenuNames(String menuIds) {
        Long[] menus = Convert.toLongArray(menuIds);
        StringBuilder sb = new StringBuilder();
        for (Long menu : menus) {
            MenuEntity menuObj = menuMapper.selectById(menu);
            if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrUtil.removeSuffix(sb.toString(), ",");
    }

    @Override
    @Cacheable(value = CacheConsts.MENT_CONSTANT, key = "'" + CacheKeyConsts.SINGLE_MENU_NAME + "'+#menuId")
    public String getMenuName(Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        } else {
            MenuEntity menu = menuMapper.selectById(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }


    @Override
    public List<Long> getParentDeptIds(Long deptId) {
        DeptEntity dept = deptMapper.selectById(deptId);
        String pids = dept.getPids();
        String[] split = pids.split(",");
        ArrayList<Long> parentDeptIds = new ArrayList<>();
        for (String s : split) {
            parentDeptIds.add(Long.valueOf(StrUtil.removeSuffix(StrUtil.removePrefix(s, "["), "]")));
        }
        return parentDeptIds;
    }

    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        } else if ("0".equals(code)) {
            return "顶级";
        } else {
            MenuEntity param = new MenuEntity();
            param.setCode(code);
            QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>(param);
            MenuEntity menu = menuMapper.selectOne(queryWrapper);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    @Override
    public Long getMenuIdByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return 0L;
        } else if ("0".equals(code)) {
            return 0L;
        } else {
            MenuEntity menu = new MenuEntity();
            menu.setCode(code);
            QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>(menu);
            MenuEntity tempMenu = this.menuMapper.selectOne(queryWrapper);
            return tempMenu.getId();
        }
    }

}
