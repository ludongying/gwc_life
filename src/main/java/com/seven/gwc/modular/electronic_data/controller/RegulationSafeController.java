package com.seven.gwc.modular.electronic_data.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.electronic_data.entity.RegulationSafeEntity;
import com.seven.gwc.modular.electronic_data.service.RegulationSafeService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 法律法规/航线安全控制器
 *
 * @author : SHQ
 * @date : 2020-02-25
 */
@Controller
@RequestMapping("regulationSafe")
public class RegulationSafeController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/electronic_data/regulationSafe/";

    @Autowired
    private RegulationSafeService regulationSafeService;

    /**
     * 跳转到法律法规/航线安全首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "regulationSafe";
    }

    /**
     * 跳转到添加法律法规/航线安全
     */
    @RequestMapping("/regulationSafe_add")
    public String regulationSafeAdd() {
        return PREFIX + "regulationSafe_add";
    }

    /**
     * 跳转到修改法律法规/航线安全
     */
    @RequestMapping("/regulationSafe_edit")
    public String regulationSafeUpdate(String regulationSafeId) {
        return PREFIX + "regulationSafe_edit";
    }

    /**
     * 跳转到查看法律法规/航线安全
     */
    @RequestMapping("/regulationSafe_detail")
    public String regulationSafeDetail(String regulationSafeId) {
        return PREFIX + "regulationSafe_detail";
    }

    /**
     * 获取法律法规/航线安全列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<RegulationSafeEntity> list(String regulationSafeName,String lawRegularId,String type) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<RegulationSafeEntity> regulationSafes = regulationSafeService.selectRegulationSafe(regulationSafeName,lawRegularId,type);
        PageInfo pageInfo = new PageInfo<>(regulationSafes);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加法律法规/航线安全
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(RegulationSafeEntity regulationSafe) {
        ShiroUser user = ShiroKit.getUser();
        regulationSafeService.addRegulationSafe(regulationSafe, user);
        return SUCCESS;
    }

    /**
     * 删除法律法规/航线安全
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String regulationSafeId) {
        ShiroUser user = ShiroKit.getUser();
        regulationSafeService.deleteRegulationSafe(regulationSafeId, user);
        return SUCCESS;
    }

    /**
     * 编辑法律法规/航线安全
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(RegulationSafeEntity regulationSafe) {
        ShiroUser user = ShiroKit.getUser();
        regulationSafeService.editRegulationSafe(regulationSafe, user);
        return SUCCESS;
    }

    /**
     * 法律法规/航线安全详情
     */
    @RequestMapping("/detail/{regulationSafeId}")
    @ResponseBody
    public RegulationSafeEntity detail(@PathVariable String regulationSafeId) {
        return regulationSafeService.getById(regulationSafeId);
    }

    /**
     * 文档名称验重
     */
    @RequestMapping("/selectOnlyByName")
    @ResponseBody
    public boolean selectOnlyByName(String name,String type) {
        return regulationSafeService.selectOnlyByName(name,type);
    }

}

