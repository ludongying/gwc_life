package com.seven.gwc.modular.address_book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.address_book.entity.GroupEntity;
import com.seven.gwc.modular.address_book.service.GroupService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : group控制器
 *
 * @author : SHQ
 * @date : 2020-02-27
 */
@Controller
@RequestMapping("group")
public class GroupController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/address_book/group/";

    @Autowired
    private GroupService groupService;

    /**
     * 跳转到group首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "group";
    }

    /**
     * 跳转到添加group
     */
    @RequestMapping("/group_add")
    public String groupAdd() {
        return PREFIX + "group_add";
    }

    /**
     * 跳转到修改group
     */
    @RequestMapping("/group_edit")
    public String groupUpdate(String groupId) {
        return PREFIX + "group_edit";
    }

    /**
     * 跳转到查看group
     */
    @RequestMapping("/group_detail")
    public String groupDetail(String groupId) {
        return PREFIX + "group_detail";
    }

    /**
     * group详情
     */
    @RequestMapping("/detail/{groupId}")
    @ResponseBody
    public GroupEntity detail(@PathVariable String groupId) {
        return groupService.getById(groupId);
    }

}

