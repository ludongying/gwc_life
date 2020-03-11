package com.seven.gwc.config.constant;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * 系统常量
 *
 * @author : GD
 */
public interface ConfigConsts {

    /**
     * 默认管理系统的名称
     */
    String DEFAULT_SYSTEM_NAME = "GWC管理系统";

    /**
     * 默认欢迎界面的提示
     */
    String DEFAULT_WELCOME_TIP = "欢迎使用GWC管理系统!";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 管理员id
     */
    Long ADMIN_ID = 1L;

    /**
     * 超级管理员角色id
     */
    Long ADMIN_ROLE_ID = 1L;

    /**
     * 系统管理id
     */
    Long SYSTEM_ID = 1L;

    /**
     * 不需要权限验证的资源表达式
     */
    List<String> NONE_PERMISSION_RES = CollectionUtil.newLinkedList("/gwcApi/**", "/modular/**", "/common/**", "/login", "/error", "/sessionError", "/**.png", "/**.jpeg", "/**.jpg", "/**.pdf");

}
