package com.seven.gwc.modular.system.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.system.entity.HolidayEntity;
import com.seven.gwc.modular.system.service.HolidayService;

import com.seven.gwc.core.base.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.util.Date;
import java.util.List;

/**
 * description : 假日控制器
 * @author : GD
 * @date  : 2019-10-17
 */
@Controller
@RequestMapping("holiday")
public class HolidayController extends BaseController {

private String PREFIX = "/modular/system/holiday/";

    @Autowired
    private HolidayService holidayService;

    /**
     * 跳转到假日首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "holiday";
    }

    /**
     * 跳转到添加假日
     */
    @RequestMapping("/holiday_add")
    public String holidayAdd() {
        return PREFIX + "holiday_add";
    }

    /**
     * 跳转到修改假日
     */
    @RequestMapping("/holiday_edit")
    public String holidayUpdate(Long holidayId) {
        return PREFIX + "holiday_edit";
    }

    /**
     * 跳转到查看假日
     */
    @RequestMapping("/holiday_detail")
    public String holidayDetail(Long holidayId) {
        return PREFIX + "holiday_detail";
    }

    /**
     * 获取假日列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<HolidayEntity> list(String holidayName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<HolidayEntity> holidays = holidayService.selectHoliday(holidayName);
        PageInfo pageInfo = new PageInfo<>(holidays);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 新增假日
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(HolidayEntity holiday) {
        ShiroUser user = ShiroKit.getUser();

        if (holiday.getSort() == null){
            holiday.setSort(0);
        }
        holiday.setCreateUser(user.getId());
        holiday.setCreateTime(new Date());
        holidayService.save(holiday);
        return SUCCESS;
    }

    /**
     * 删除假日
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long holidayId) {
        holidayService.removeById(holidayId);
        return SUCCESS;
    }

    /**
     * 修改假日
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(HolidayEntity holiday) {
        ShiroUser user = ShiroKit.getUser();

        if (holiday.getSort() == null){
            holiday.setSort(0);
        }
        holiday.setUpdateUser(user.getId());
        holiday.setUpdateTime(new Date());
        holidayService.updateById(holiday);
        return SUCCESS;
    }

    /**
     * 假日详情
     */
    @RequestMapping("/detail/{holidayId}")
    @ResponseBody
    public HolidayEntity detail(@PathVariable Long holidayId) {
        return holidayService.getById(holidayId);
    }

}

