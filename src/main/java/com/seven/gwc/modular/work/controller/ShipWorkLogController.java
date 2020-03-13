package com.seven.gwc.modular.work.controller;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.work.entity.ShipWorkLogEntity;
import com.seven.gwc.modular.work.service.ShipWorkLogService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.Date;
import java.util.List;

/**
 * description : 工作日志记录控制器
 *
 * @author : 李晓晖
 * @date : 2020-03-06
 */
@Controller
@RequestMapping("shipWorkLog")
public class ShipWorkLogController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/work/shipWorkLog/";

    @Autowired
    private ShipWorkLogService shipWorkLogService;

    /**
     * 跳转到工作日志记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shipWorkLog";
    }

    /**
     * 跳转到添加工作日志记录
     */
    @RequestMapping("/shipWorkLog_add")
    public String shipWorkLogAdd() {
        return PREFIX + "shipWorkLog_add";
    }

    /**
     * 跳转到修改工作日志记录
     */
    @RequestMapping("/shipWorkLog_edit")
    public String shipWorkLogUpdate(String shipWorkLogId) {
        return PREFIX + "shipWorkLog_edit";
    }

    /**
     * 跳转到查看工作日志记录
     */
    @RequestMapping("/shipWorkLog_detail")
    public String shipWorkLogDetail(String shipWorkLogId) {
        return PREFIX + "shipWorkLog_detail";
    }

    /**
     * 获取工作日志记录列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<ShipWorkLogEntity> list(String shipWorkLogName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<ShipWorkLogEntity> shipWorkLogs = shipWorkLogService.selectShipWorkLog(shipWorkLogName);
        PageInfo pageInfo = new PageInfo<>(shipWorkLogs);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加工作日志记录
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(ShipWorkLogEntity shipWorkLog) {
        ShiroUser user = ShiroKit.getUser();
        shipWorkLog.setCreatePerson(user.getId());
        shipWorkLog.setCreateDate(new Date());
        shipWorkLogService.addShipWorkLog(shipWorkLog, user);
        return SUCCESS;
    }

    /**
     * 删除工作日志记录
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String shipWorkLogId) {
        ShiroUser user = ShiroKit.getUser();
        shipWorkLogService.deleteShipWorkLog(shipWorkLogId, user);
        return SUCCESS;
    }

    /**
     * 编辑工作日志记录
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(ShipWorkLogEntity shipWorkLog) {
        ShiroUser user = ShiroKit.getUser();
        shipWorkLogService.editShipWorkLog(shipWorkLog, user);
        return SUCCESS;
    }

    /**
     * 工作日志记录详情
     */
    @RequestMapping("/detail/{shipWorkLogId}")
    @ResponseBody
    public ShipWorkLogEntity detail(@PathVariable String shipWorkLogId) {
        return shipWorkLogService.getById(shipWorkLogId);
    }
    /**
     * 获取日志列表
     */
    @RequestMapping("/listLogs")
    @ResponseBody
    public Object listLogs(ShipWorkLogEntity shipWorkLog) {
        ShiroUser user = ShiroKit.getUser();
        JSONArray jsonArray = shipWorkLogService.listLogs(shipWorkLog,user);
        return new BaseResult().content(jsonArray);
    }
}

