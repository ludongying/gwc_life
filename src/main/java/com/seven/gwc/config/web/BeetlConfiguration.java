package com.seven.gwc.config.web;

import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.config.constant.ConfigConsts;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * beetl拓展配置,绑定一些工具类,方便在模板中直接调用
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {
    @Override
    public void initOther() {

        //全局共享变量
        Map<String, Object> shared = new HashMap<>();
        shared.put("systemName", ConfigConsts.DEFAULT_SYSTEM_NAME);
        shared.put("welcomeTip", ConfigConsts.DEFAULT_WELCOME_TIP);
        groupTemplate.setSharedVars(shared);

        //全局共享方法
        groupTemplate.registerFunctionPackage("shiro", new ShiroKit());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
    }
}
