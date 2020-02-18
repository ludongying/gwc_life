package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.OperationLogEntity;
import com.seven.gwc.modular.system.service.OperationLogService;
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
 * description : 操作日志控制器
 *
 * @author : GD
 * @date : 2019-12-18
 */
@Controller
@RequestMapping("operationLog")
public class OperationLogController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String PREFIX = "/modular/system/operationLog/";

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 跳转到操作日志首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "operationLog";
    }

    /**
     * 跳转到添加操作日志
     */
    @RequestMapping("/operationLog_add")
    public String operationLogAdd() {
        return PREFIX + "operationLog_add";
    }

    /**
     * 跳转到修改操作日志
     */
    @RequestMapping("/operationLog_edit")
    public String operationLogUpdate(Long operationLogId) {
        return PREFIX + "operationLog_edit";
    }

    /**
     * 跳转到查看操作日志
     */
    @RequestMapping("/operationLog_detail")
    public String operationLogDetail(Long operationLogId) {
        return PREFIX + "operationLog_detail";
    }

    /**
     * 获取操作日志列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<OperationLogEntity> list(String operationLogName, String message, String beginTime, String endTime) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<OperationLogEntity> operationLogs = operationLogService.selectOperationLog(operationLogName, message, beginTime, endTime);
        PageInfo pageInfo = new PageInfo<>(operationLogs);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 新增操作日志
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(OperationLogEntity operationLog) {
        ShiroUser user = ShiroKit.getUser();
        operationLogService.save(operationLog);
        return SUCCESS;
    }

    /**
     * 删除操作日志
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long operationLogId) {
        operationLogService.removeById(operationLogId);
        return SUCCESS;
    }

    /**
     * 修改操作日志
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(OperationLogEntity operationLog) {
        ShiroUser user = ShiroKit.getUser();
        operationLogService.updateById(operationLog);
        return SUCCESS;
    }

    /**
     * 操作日志详情
     */
    @RequestMapping("/detail/{operationLogId}")
    @ResponseBody
    public OperationLogEntity detail(@PathVariable Long operationLogId) {
        return operationLogService.getById(operationLogId);
    }

}

