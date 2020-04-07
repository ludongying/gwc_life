package com.seven.gwc.modular.equipment_info.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import com.seven.gwc.modular.equipment_info.service.EquipService;
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
 * description : 设备信息控制器
 *
 * @author : LDY
 * @date : 2020-03-09
 */
@Controller
@RequestMapping("equip")
public class EquipController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/equipment_info/equip/";

    @Autowired
    private EquipService equipService;

    /**
     * 跳转到设备信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "equip";
    }

    /**
     * 跳转到添加设备信息
     */
    @RequestMapping("/equip_add")
    public String equipAdd() {
        return PREFIX + "equip_add";
    }

    /**
     * 跳转到修改设备信息
     */
    @RequestMapping("/equip_edit")
    public String equipUpdate(String equipId) {
        return PREFIX + "equip_edit";
    }

    /**
     * 跳转到查看设备信息
     */
    @RequestMapping("/equip_detail")
    public String equipDetail(String equipId) {
        return PREFIX + "equip_detail";
    }

    /**
     * 获取设备信息列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<EquipEntity> list(EquipEntity equipEntity) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<EquipEntity> equips = equipService.selectEquip(equipEntity,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(equips);
        Integer size = equipService.getListSize(equipEntity);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);

    }

    /**
     * 按类别获取设备信息列表
     */
    @RequestMapping("/listByTypeAndName")
    @ResponseBody
    public List<EquipEntity> listByTypeAndName(String equipName,String equipType) {
        return equipService.getListByTypeName(equipName,equipType);
    }

    /**
     * 按类别获取设备信息列表
     */
    @RequestMapping("/listByType")
    @ResponseBody
    public List<EquipEntity> listByTypeAndName(String equipType) {
        return equipService.getListByType(equipType);
    }

    /**
     * 增加设备信息
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(EquipEntity equip) {
        ShiroUser user = ShiroKit.getUser();
        if(!equipService.addEquip(equip, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_EQUIP_CODE);
        }
        return SUCCESS;
    }

    /**
     * 删除设备信息
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String equipId) {
        ShiroUser user = ShiroKit.getUser();
        equipService.deleteEquip(equipId, user);
        return SUCCESS;
    }

    /**
     * 编辑设备信息
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(EquipEntity equip) {
        ShiroUser user = ShiroKit.getUser();
        if(!equipService.editEquip(equip, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_EQUIP_CODE);
        }
        return SUCCESS;
    }

    /**
     * 设备信息详情
     */
    @RequestMapping("/detail/{equipId}")
    @ResponseBody
    public EquipEntity detail(@PathVariable String equipId) {
        return equipService.selectEquipById(equipId);
    }

    /**
     * 启动
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public BaseResult unfreeze(@RequestParam String id) {
        this.equipService.setStatus(id, TypeStatesEnum.OK.getCode());
        return SUCCESS;
    }

    /**
     * 禁用
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public BaseResult freeze(@RequestParam String id) {
        this.equipService.setStatus(id, TypeStatesEnum.PROHIBIT.getCode());
        return SUCCESS;
    }

}

