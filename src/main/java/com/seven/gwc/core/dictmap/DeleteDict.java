package com.seven.gwc.core.dictmap;

import com.seven.gwc.core.dictmap.base.AbstractDictMap;

/**
 * 用于删除业务的字典
 *
 * @author GD
 */
public class DeleteDict extends AbstractDictMap {

    @Override
    public void init() {
        put("userId", "用户名称");
        put("roleId", "角色名称");
        put("deptId", "部门名称");
        put("menuId", "菜单名称");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("userId", "getCacheObject");
        putFieldWrapperMethodName("roleId", "getCacheObject");
        putFieldWrapperMethodName("deptId", "getCacheObject");
        putFieldWrapperMethodName("menuId", "getCacheObject");

    }
}
