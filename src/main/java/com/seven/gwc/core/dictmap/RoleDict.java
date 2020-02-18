package com.seven.gwc.core.dictmap;


import com.seven.gwc.core.dictmap.base.AbstractDictMap;

/**
 * 角色的字典
 *
 * @author GD
 */
public class RoleDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id", "角色名称");
        put("sort", "角色排序");
        put("pid", "角色的父级");
        put("name", "角色名称");
        put("description", "备注");
        put("ids", "资源名称");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("pid", "getSingleRoleName");
        putFieldWrapperMethodName("deptId", "getDeptName");
        putFieldWrapperMethodName("id", "getSingleRoleName");
        putFieldWrapperMethodName("ids", "getMenuNames");
    }
}
