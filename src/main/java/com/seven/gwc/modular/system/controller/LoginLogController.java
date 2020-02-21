package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.LoginLogEntity;
import com.seven.gwc.modular.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description : 登录历史控制器
 *
 * @author : SHQ
 * @date : 2019-10-11
 */
@Controller
@RequestMapping("loginLog")
public class LoginLogController extends BaseController {

    private static String PREFIX = "/modular/system/loginLog/";

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 跳转到登录历史首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "loginLog";
    }

    /**
     * 跳转到添加登录历史
     */
    @RequestMapping("/loginLog_add")
    public String loginLogAdd() {
        return PREFIX + "loginLog_add";
    }

    /**
     * 跳转到修改登录历史
     */
    @RequestMapping("/loginLog_edit")
    public String loginLogUpdate(Long loginLogId) {
        return PREFIX + "loginLog_edit";
    }

    /**
     * 跳转到查看登录历史
     */
    @RequestMapping("/loginLog_detail")
    public String loginLogDetail(Long loginLogId) {
        return PREFIX + "loginLog_detail";
    }

    /**
     * 获取登录历史列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<LoginLogEntity> list(String loginLogName, String message, String beginTime, String endTime) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LoginLogEntity> loginLogs = loginLogService.selectLoginLog(loginLogName, message, beginTime, endTime);
        PageInfo pageInfo = new PageInfo<>(loginLogs);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加登录历史
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(LoginLogEntity loginLog) {
        ShiroUser user = ShiroKit.getUser();
        loginLogService.save(loginLog);
        return SUCCESS;
    }

    /**
     * 删除登录历史
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long loginLogId) {
        loginLogService.removeById(loginLogId);
        return SUCCESS;
    }

    /**
     * 清空所有登录历史
     */
    @RequestMapping("/deleteAll")
    @ResponseBody
    public BaseResult deleteAll() {
        LambdaUpdateWrapper<LoginLogEntity> updateWrapper = Wrappers.<LoginLogEntity>lambdaUpdate();
        loginLogService.remove(updateWrapper);
        return SUCCESS;
    }

    /**
     * 编辑登录历史
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(LoginLogEntity loginLog) {
        ShiroUser user = ShiroKit.getUser();
        loginLogService.updateById(loginLog);
        return SUCCESS;
    }

    /**
     * 登录历史详情
     */
    @RequestMapping("/detail/{loginLogId}")
    @ResponseBody
    public LoginLogEntity detail(@PathVariable Long loginLogId) {
        return loginLogService.getById(loginLogId);
    }

}

