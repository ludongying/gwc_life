package com.seven.gwc.core.dictmap;


import com.seven.gwc.core.dictmap.base.AbstractDictMap;

/**
 * 部门的映射
 *
 * @author GD
 */
public class DeptDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id", "部门名称");
        put("sort", "部门排序");
        put("pid", "上级名称");
        put("simpleName", "部门简称");
        put("fullName", "部门全称");
        put("description", "备注");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("id", "getDeptName");
        putFieldWrapperMethodName("pid", "getDeptName");
    }
}
