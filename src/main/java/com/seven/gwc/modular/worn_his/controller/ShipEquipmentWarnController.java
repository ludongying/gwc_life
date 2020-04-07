package com.seven.gwc.modular.worn_his.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.worn_his.entity.ShipEquipmentWarnEntity;
import com.seven.gwc.modular.worn_his.service.ShipEquipmentWarnService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 历史报警控制器
 *
 * @author : 李晓晖
 * @date : 2020-03-13
 */
@Controller
@RequestMapping("shipEquipmentWarn")
public class ShipEquipmentWarnController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/worn_his/shipEquipmentWarn/";

    @Autowired
    private ShipEquipmentWarnService shipEquipmentWarnService;

    /**
     * 跳转到历史报警首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shipEquipmentWarn";
    }

    /**
     * 跳转到添加历史报警
     */
    @RequestMapping("/shipEquipmentWarn_add")
    public String shipEquipmentWarnAdd() {
        return PREFIX + "shipEquipmentWarn_add";
    }

    /**
     * 跳转到修改历史报警
     */
    @RequestMapping("/shipEquipmentWarn_edit")
    public String shipEquipmentWarnUpdate(String shipEquipmentWarnId) {
        return PREFIX + "shipEquipmentWarn_edit";
    }

    /**
     * 跳转到查看历史报警
     */
    @RequestMapping("/shipEquipmentWarn_detail")
    public String shipEquipmentWarnDetail(String shipEquipmentWarnId) {
        return PREFIX + "shipEquipmentWarn_detail";
    }

    /**
     * 获取历史报警列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<ShipEquipmentWarnEntity> list(String shipEquipmentWarnName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<ShipEquipmentWarnEntity> shipEquipmentWarns = shipEquipmentWarnService.selectShipEquipmentWarn(shipEquipmentWarnName);
        PageInfo pageInfo = new PageInfo<>(shipEquipmentWarns);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加历史报警
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(ShipEquipmentWarnEntity shipEquipmentWarn) {
        ShiroUser user = ShiroKit.getUser();
        shipEquipmentWarnService.addShipEquipmentWarn(shipEquipmentWarn, user);
        return SUCCESS;
    }

    /**
     * 删除历史报警
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String shipEquipmentWarnId) {
        ShiroUser user = ShiroKit.getUser();
        shipEquipmentWarnService.deleteShipEquipmentWarn(shipEquipmentWarnId, user);
        return SUCCESS;
    }

    /**
     * 编辑历史报警
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(ShipEquipmentWarnEntity shipEquipmentWarn) {
        ShiroUser user = ShiroKit.getUser();
        shipEquipmentWarnService.editShipEquipmentWarn(shipEquipmentWarn, user);
        return SUCCESS;
    }

    /**
     * 历史报警详情
     */
    @RequestMapping("/detail/{shipEquipmentWarnId}")
    @ResponseBody
    public ShipEquipmentWarnEntity detail(@PathVariable String shipEquipmentWarnId) {
        return shipEquipmentWarnService.getById(shipEquipmentWarnId);
    }

}

