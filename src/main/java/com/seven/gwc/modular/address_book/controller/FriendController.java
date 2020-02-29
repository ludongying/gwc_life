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

import com.seven.gwc.modular.address_book.entity.FriendEntity;
import com.seven.gwc.modular.address_book.service.FriendService;

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
@RequestMapping("friend")
public class FriendController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/address_book/friend/";

    @Autowired
    private FriendService friendService;

    /**
     * 跳转到group首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "friend";
    }

    /**
     * 跳转到添加group
     */
    @RequestMapping("/friend_add")
    public String friendAdd() {
        return PREFIX + "friend_add";
    }

    /**
     * 跳转到修改group
     */
    @RequestMapping("/friend_edit")
    public String friendUpdate(String friendId) {
        return PREFIX + "friend_edit";
    }

    /**
     * 跳转到查看group
     */
    @RequestMapping("/friend_detail")
    public String friendDetail(String friendId) {
        return PREFIX + "friend_detail";
    }

    /**
     * group详情
     */
    @RequestMapping("/detail/{friendId}")
    @ResponseBody
    public FriendEntity detail(@PathVariable String friendId) {
        return friendService.getById(friendId);
    }

}

