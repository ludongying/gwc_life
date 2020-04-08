package com.seven.gwc.modular.munition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.service.MunitionInService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 物资入库控制器
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Controller
@RequestMapping("munitionIn")
public class MunitionInController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/munition/munitionIn/";

    @Autowired
    private MunitionInService munitionInandoutService;

    /**
     * 跳转到物资入库首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "munitionIn";
    }

    /**
     * 跳转到添加物资入库
     */
    @RequestMapping("/munitionIn_add")
    public String munitionInandoutAdd() {
        return PREFIX + "munitionIn_add";
    }

    /**
     * 跳转到修改物资入库
     */
    @RequestMapping("/munitionIn_edit")
    public String munitionInandoutUpdate(String munitionInandoutId) {
        return PREFIX + "munitionIn_edit";
    }

    /**
     * 跳转到查看物资入库
     */
    @RequestMapping("/munitionIn_detail")
    public String munitionInandoutDetail(String munitionInandoutId) {
        return PREFIX + "munitionIn_detail";
    }

    /**
     * 获取物资入库列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<MunitionInEntity> list(String munitionInandoutName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MunitionInEntity> munitionInandouts = munitionInandoutService.selectMunitionInandout(munitionInandoutName);
        PageInfo pageInfo = new PageInfo<>(munitionInandouts);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加物资入库
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(MunitionInEntity munitionInandout) {
        ShiroUser user = ShiroKit.getUser();
        munitionInandoutService.addMunitionInandout(munitionInandout, user);
        return SUCCESS;
    }

    /**
     * 删除物资入库
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String munitionInandoutId) {
        ShiroUser user = ShiroKit.getUser();
        munitionInandoutService.deleteMunitionInandout(munitionInandoutId, user);
        return SUCCESS;
    }

    /**
     * 编辑物资入库
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(MunitionInEntity munitionInandout) {
        ShiroUser user = ShiroKit.getUser();
        munitionInandoutService.editMunitionInandout(munitionInandout, user);
        return SUCCESS;
    }

    /**
     * 物资入库详情
     */
    @RequestMapping("/detail/{munitionInandoutId}")
    @ResponseBody
    public MunitionInEntity detail(@PathVariable String munitionInandoutId) {
        return munitionInandoutService.getById(munitionInandoutId);
    }

}

