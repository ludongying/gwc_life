package com.seven.gwc.modular.path.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.path.entity.PointlistEntity;
import com.seven.gwc.modular.path.service.PointlistService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : path控制器
 *
 * @author : QQC
 * @date : 2020-01-02
 */
@Controller
@RequestMapping("pointlist")
public class PointlistController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String PREFIX = "/modular/path/pointlist/";

    @Autowired
    private PointlistService pointlistService;

    /**
     * 跳转到path首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "pointlist";
    }

    /**
     * 跳转到添加path
     */
    @RequestMapping("/pointlist_add")
    public String pointlistAdd() {
        return PREFIX + "pointlist_add";
    }

    /**
     * 跳转到修改path
     */
    @RequestMapping("/pointlist_edit")
    public String pointlistUpdate(Long pointlistId) {
        return PREFIX + "pointlist_edit";
    }

    /**
     * 跳转到查看path
     */
    @RequestMapping("/pointlist_detail")
    public String pointlistDetail(Long pointlistId) {
        return PREFIX + "pointlist_detail";
    }

    /**
     * 获取path列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<PointlistEntity> list(String pointlistName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<PointlistEntity> pointlists = pointlistService.selectPointlist(pointlistName);
        PageInfo pageInfo = new PageInfo<>(pointlists);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 新增path
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(PointlistEntity pointlist) {
        ShiroUser user = ShiroKit.getUser();
        pointlistService.save(pointlist);
        return SUCCESS;
    }

    /**
     * 删除path
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long pointlistId) {
        pointlistService.removeById(pointlistId);
        return SUCCESS;
    }

    /**
     * 修改path
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(PointlistEntity pointlist) {
        ShiroUser user = ShiroKit.getUser();
        pointlistService.updateById(pointlist);
        return SUCCESS;
    }

    /**
     * path详情
     */
    @RequestMapping("/detail/{pointlistId}")
    @ResponseBody
    public PointlistEntity detail(@PathVariable Long pointlistId) {
        return pointlistService.getById(pointlistId);
    }

}

