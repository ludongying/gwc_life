package com.seven.gwc.modular.ship.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.ship.entity.ShipWorkPlanEntity;
import com.seven.gwc.modular.ship.service.ShipWorkPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description : 工作计划控制器
 *
 * @author : 李晓晖
 * @date : 2020-02-27
 */
@Controller
@RequestMapping("shipWorkPlan")
public class ShipWorkPlanController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/ship/shipWorkPlan/";

    @Autowired
    private ShipWorkPlanService shipWorkPlanService;

    /**
     * 跳转到工作计划首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shipWorkPlan";
    }

    /**
     * 跳转到添加工作计划
     */
    @RequestMapping("/shipWorkPlan_add")
    public String shipWorkPlanAdd() {
        return PREFIX + "shipWorkPlan_add";
    }

    /**
     * 跳转到修改工作计划
     */
    @RequestMapping("/shipWorkPlan_edit")
    public String shipWorkPlanUpdate(String shipWorkPlanId) {
        return PREFIX + "shipWorkPlan_edit";
    }

    /**
     * 跳转到查看工作计划
     */
    @RequestMapping("/shipWorkPlan_detail")
    public String shipWorkPlanDetail(String shipWorkPlanId) {
        return PREFIX + "shipWorkPlan_detail";
    }

    /**
     * 获取工作计划列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage list(String shipWorkPlanName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<ShipWorkPlanEntity> shipWorkPlans = shipWorkPlanService.selectShipWorkPlan(shipWorkPlanName);
        PageInfo pageInfo = new PageInfo<>(shipWorkPlans);
        return new BaseResultPage().createPage(pageInfo);

    }

    /**
     * 增加工作计划
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(ShipWorkPlanEntity shipWorkPlan) {
        ShiroUser user = ShiroKit.getUser();
        shipWorkPlanService.addShipWorkPlan(shipWorkPlan, user);
        return SUCCESS;
    }

    /**
     * 删除工作计划
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String shipWorkPlanId) {
        ShiroUser user = ShiroKit.getUser();
        shipWorkPlanService.deleteShipWorkPlan(shipWorkPlanId, user);
        return SUCCESS;
    }

    /**
     * 编辑工作计划
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(ShipWorkPlanEntity shipWorkPlan) {
        ShiroUser user = ShiroKit.getUser();
        shipWorkPlanService.editShipWorkPlan(shipWorkPlan, user);
        return SUCCESS;
    }

    /**
     * 工作计划详情
     */
    @RequestMapping("/detail/{shipWorkPlanId}")
    @ResponseBody
    public ShipWorkPlanEntity detail(@PathVariable String shipWorkPlanId) {
        return shipWorkPlanService.getById(shipWorkPlanId);
    }

    /**
     * 获取工作列表
     */
    @RequestMapping("/listPlans")
    @ResponseBody
    public Object listPlans(ShipWorkPlanEntity shipWorkPlan) {
        ShiroUser user = ShiroKit.getUser();
        JSONArray jsonArray = shipWorkPlanService.listPlans(shipWorkPlan);
        return new BaseResult().content(jsonArray);
    }
}

