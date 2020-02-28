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

import com.seven.gwc.modular.address_book.entity.GroupPersonEntity;
import com.seven.gwc.modular.address_book.service.GroupPersonService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : groupPerson控制器
 *
 * @author : SHQ
 * @date : 2020-02-27
 */
@Controller
@RequestMapping("groupPerson")
public class GroupPersonController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/address_book/groupPerson/";

    @Autowired
    private GroupPersonService groupPersonService;

    /**
     * 跳转到groupPerson首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "groupPerson";
    }

    /**
     * 跳转到添加groupPerson
     */
    @RequestMapping("/groupPerson_add")
    public String groupPersonAdd() {
        return PREFIX + "groupPerson_add";
    }

    /**
     * 跳转到修改groupPerson
     */
    @RequestMapping("/groupPerson_edit")
    public String groupPersonUpdate(String groupPersonId) {
        return PREFIX + "groupPerson_edit";
    }

    /**
     * 跳转到查看groupPerson
     */
    @RequestMapping("/groupPerson_detail")
    public String groupPersonDetail(String groupPersonId) {
        return PREFIX + "groupPerson_detail";
    }

    /**
     * groupPerson详情
     */
    @RequestMapping("/detail/{groupPersonId}")
    @ResponseBody
    public GroupPersonEntity detail(@PathVariable String groupPersonId) {
        return groupPersonService.getById(groupPersonId);
    }

}

