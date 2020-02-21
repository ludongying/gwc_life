package com.seven.gwc.core.dictmap;

import com.seven.gwc.core.dictmap.base.AbstractDictMap;

/**
 * description : TODO
 * @date : 2019/12/19 14:09
 * @author : GD
 * @version : 1.0
 */
public class PositionDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id", "岗位名称");
        put("name", "岗位名称");
        put("sort", "岗位排序");
        put("dataScope", "岗位数据范围");
        put("description", "备注");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("id", "getSinglePositionName");
    }
}
