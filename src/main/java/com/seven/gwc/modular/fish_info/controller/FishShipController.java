package com.seven.gwc.modular.fish_info.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.seven.gwc.modular.fish_info.service.FishShipService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 渔船信息控制器
 *
 * @author : SHQ
 * @date : 2020-03-02
 */
@Controller
@RequestMapping("fishShip")
public class FishShipController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/fish_info/fishShip/";

    @Autowired
    private FishShipService fishShipService;

    /**
     * 跳转到渔船信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "fishShip";
    }

    /**
     * 跳转到添加渔船信息
     */
    @RequestMapping("/fishShip_add")
    public String fishShipAdd() {
        return PREFIX + "fishShip_add";
    }

    /**
     * 跳转到修改渔船信息
     */
    @RequestMapping("/fishShip_edit")
    public String fishShipUpdate(String fishShipId) {
        return PREFIX + "fishShip_edit";
    }

    /**
     * 跳转到查看渔船信息
     */
    @RequestMapping("/fishShip_detail")
    public String fishShipDetail(String fishShipId) {
        return PREFIX + "fishShip_detail";
    }

    /**
     * 获取渔船信息列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<FishShipEntity> list(String code, String phone, String shipType) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<FishShipEntity> fishShips = fishShipService.selectFishShip(code, phone, shipType);
        PageInfo pageInfo = new PageInfo<>(fishShips);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加渔船信息
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(FishShipEntity fishShip) {
        ShiroUser user = ShiroKit.getUser();
        fishShipService.addFishShip(fishShip, user);
        return SUCCESS;
    }

    /**
     * 删除渔船信息
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String fishShipId) {
        ShiroUser user = ShiroKit.getUser();
        fishShipService.deleteFishShip(fishShipId, user);
        return SUCCESS;
    }

    /**
     * 编辑渔船信息
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(FishShipEntity fishShip) {
        ShiroUser user = ShiroKit.getUser();
        fishShipService.editFishShip(fishShip, user);
        return SUCCESS;
    }

    /**
     * 渔船信息详情
     */
    @RequestMapping("/detail/{fishShipId}")
    @ResponseBody
    public FishShipEntity detail(@PathVariable String fishShipId) {
        return fishShipService.detailFishShip(fishShipId);
    }

}

