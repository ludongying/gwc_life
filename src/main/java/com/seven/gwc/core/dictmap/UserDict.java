package com.seven.gwc.core.dictmap;

import com.seven.gwc.core.dictmap.base.AbstractDictMap;

/**
 * 用户的字典
 *
 * @author GD
 */
public class UserDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id", "账号");
        put("avatar", "头像");
        put("account", "工号");
        put("name", "名字");
        put("birthday", "生日");
        put("sex", "性别");
        put("email", "电子邮件");
        put("phone", "电话");
        put("roleId", "角色名称");
        put("deptId", "部门名称");
        put("roleIds", "角色名称集合");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("deptId", "getDeptName");
        putFieldWrapperMethodName("roleId", "getSingleRoleName");
        putFieldWrapperMethodName("id", "getUserAccountById");
        putFieldWrapperMethodName("roleIds", "getRoleName");
    }
}
