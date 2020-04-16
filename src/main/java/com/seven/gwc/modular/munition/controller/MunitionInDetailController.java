package com.seven.gwc.modular.munition.controller;

import com.seven.gwc.core.state.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.munition.entity.MunitionInDetailEntity;
import com.seven.gwc.modular.munition.service.MunitionInDetailService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 物资入库详情控制器
 *
 * @author : LDY
 * @date : 2020-04-09
 */
@Controller
@RequestMapping("munitionInDetail")
public class MunitionInDetailController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/munition/munitionInDetail/";

    @Autowired
    private MunitionInDetailService munitionInDetailService;

    /**
     * 跳转到物资入库详情首页
     */
    @RequestMapping("")
    public String index(@RequestParam("code") String code, @RequestParam("type") String type, @RequestParam("status") String status, Model model) {
        model.addAttribute("munitionInCode", code);
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        return PREFIX + "munitionInDetail";
    }

    /**
     * 跳转到添加物资入库详情
     */
    @RequestMapping("/munitionInDetail_add")
    public String munitionInDetailAdd() {
        return PREFIX + "munitionInDetail_add";
    }

    /**
     * 跳转到修改物资入库详情
     */
    @RequestMapping("/munitionInDetail_edit")
    public String munitionInDetailUpdate(String munitionInDetailId) {
        return PREFIX + "munitionInDetail_edit";
    }

    /**
     * 跳转到查看物资入库详情
     */
    @RequestMapping("/munitionInDetail_detail")
    public String munitionInDetailDetail(String munitionInDetailId) {
        return PREFIX + "munitionInDetail_detail";
    }

    /**
     * 获取物资入库详情列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<MunitionInDetailEntity> list(MunitionInDetailEntity detailEntity, String munitionMainId) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MunitionInDetailEntity> munitionInDetails = munitionInDetailService.selectMunitionInDetail(detailEntity,munitionMainId,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(munitionInDetails);
        Integer size = munitionInDetailService.getListSize(detailEntity,munitionMainId);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加物资入库详情
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(MunitionInDetailEntity munitionInDetail) {
        ShiroUser user = ShiroKit.getUser();
        munitionInDetailService.addMunitionInDetail(munitionInDetail, user);
        return SUCCESS;
    }

    /**
     * 删除物资入库详情
     */
    @RequestMapping("/delete")
    @ResponseBody
//    public BaseResult delete(@RequestParam String munitionInDetailId, @RequestParam String munitionInId ) {
    public BaseResult delete(String munitionInDetailId, String munitionInId ) {
        ShiroUser user = ShiroKit.getUser();
        munitionInDetailService.deleteMunitionInDetail(munitionInDetailId,munitionInId, user);
        return SUCCESS;
    }

    /**
     * 编辑物资入库详情
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(MunitionInDetailEntity munitionInDetail) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionInDetailService.editMunitionInDetail(munitionInDetail, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_IN_MUNITION_CODE);
        }
        return SUCCESS;
    }

    /**
     * 物资入库详情详情
     */
    @RequestMapping("/detail/{munitionInDetailId}")
    @ResponseBody
    public MunitionInDetailEntity detail(@PathVariable String munitionInDetailId) {
        return munitionInDetailService.getById(munitionInDetailId);
    }

}

