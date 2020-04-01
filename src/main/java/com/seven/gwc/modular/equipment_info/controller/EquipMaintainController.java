package com.seven.gwc.modular.equipment_info.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.equipment_info.entity.EquipMaintainEntity;
import com.seven.gwc.modular.equipment_info.service.EquipMaintainService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 设备维护控制器
 *
 * @author : LDY
 * @date : 2020-03-12
 */
@Controller
@RequestMapping("equipMaintain")
public class EquipMaintainController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/equipment_info/equipMaintain/";

    @Autowired
    private EquipMaintainService equipMaintainService;

    /**
     * 跳转到设备维护首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "equipMaintain";
    }

    /**
     * 跳转到添加设备维护
     */
    @RequestMapping("/equipMaintain_add")
    public String equipMaintainAdd() {
        return PREFIX + "equipMaintain_add";
    }

    /**
     * 跳转到修改设备维护
     */
    @RequestMapping("/equipMaintain_edit")
    public String equipMaintainUpdate(String equipMaintainId) {
        return PREFIX + "equipMaintain_edit";
    }

    /**
     * 跳转到查看设备维护
     */
    @RequestMapping("/equipMaintain_detail")
    public String equipMaintainDetail(String equipMaintainId) {
        return PREFIX + "equipMaintain_detail";
    }

    /**
     * 获取设备维护列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<EquipMaintainEntity> list(EquipMaintainEntity equipMaintain) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<EquipMaintainEntity> equipMaintains = equipMaintainService.selectEquipMaintain(equipMaintain,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(equipMaintains);
        Integer size = equipMaintainService.getListSize(equipMaintain);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加设备维护
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(EquipMaintainEntity equipMaintain) {
        ShiroUser user = ShiroKit.getUser();
        equipMaintainService.addEquipMaintain(equipMaintain, user);
        return SUCCESS;
    }

    /**
     * 删除设备维护
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String equipMaintainId) {
        ShiroUser user = ShiroKit.getUser();
        equipMaintainService.deleteEquipMaintain(equipMaintainId, user);
        return SUCCESS;
    }

    /**
     * 编辑设备维护
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(EquipMaintainEntity equipMaintain) {
        ShiroUser user = ShiroKit.getUser();
        equipMaintainService.editEquipMaintain(equipMaintain, user);
        return SUCCESS;
    }

    /**
     * 设备维护详情
     */
    @RequestMapping("/detail/{equipMaintainId}")
    @ResponseBody
    public EquipMaintainEntity detail(@PathVariable String equipMaintainId) {
//        EquipMaintainEntity equipMaintainEntity = equipMaintainService.getOneById(equipMaintainId);
        return equipMaintainService.getOneById(equipMaintainId);
    }

}

