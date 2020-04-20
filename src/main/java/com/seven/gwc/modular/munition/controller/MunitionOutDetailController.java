package com.seven.gwc.modular.munition.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.modular.munition.entity.MunitionOutDetailEntity;
import com.seven.gwc.modular.munition.service.MunitionOutDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description : 物资出库详情控制器
 *
 * @author : LDY
 * @date : 2020-04-09
 */
@Controller
@RequestMapping("munitionOutDetail")
public class MunitionOutDetailController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/munition/munitionOutDetail/";

    @Autowired
    private MunitionOutDetailService munitionOutDetailService;

    /**
     * 跳转到物资出库详情首页
     */
    @RequestMapping("")
    public String index(@RequestParam("code") String code, @RequestParam("type") String type, @RequestParam("status") String status, Model model) {
        model.addAttribute("munitionOutCode", code);
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        return PREFIX + "munitionOutDetail";
    }

    /**
     * 跳转到添加物资出库详情
     */
    @RequestMapping("/munitionOutDetail_add")
    public String munitionOutDetailAdd() {
        return PREFIX + "munitionOutDetail_add";
    }

    /**
     * 跳转到修改物资出库详情
     */
    @RequestMapping("/munitionOutDetail_edit")
    public String munitionOutDetailUpdate(String munitionOutDetailId) {
        return PREFIX + "munitionOutDetail_edit";
    }

    /**
     * 跳转到查看物资出库详情
     */
    @RequestMapping("/munitionOutDetail_detail")
    public String munitionOutDetailDetail(String munitionOutDetailId) {
        return PREFIX + "munitionOutDetail_detail";
    }

    /**
     * 获取物资出库详情列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<MunitionOutDetailEntity> list(MunitionOutDetailEntity detailEntity, String munitionMainId) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MunitionOutDetailEntity> munitionOutDetails = munitionOutDetailService.selectMunitionOutDetail(detailEntity,munitionMainId,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(munitionOutDetails);
        Integer size = munitionOutDetailService.getListSize(detailEntity,munitionMainId);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加物资出库详情
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(MunitionOutDetailEntity munitionOutDetail) {
        ShiroUser user = ShiroKit.getUser();
        munitionOutDetailService.addMunitionOutDetail(munitionOutDetail, user);
        return SUCCESS;
    }

    /**
     * 删除物资出库详情
     */
    @RequestMapping("/delete")
    @ResponseBody
//    public BaseResult delete(@RequestParam String munitionOutDetailId, @RequestParam String munitionOutId ) {
    public BaseResult delete(String munitionOutDetailId, String munitionOutId ) {
        ShiroUser user = ShiroKit.getUser();
        munitionOutDetailService.deleteMunitionOutDetail(munitionOutDetailId,munitionOutId, user);
        return SUCCESS;
    }

    /**
     * 编辑物资出库详情
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(MunitionOutDetailEntity munitionOutDetail) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionOutDetailService.editMunitionOutDetail(munitionOutDetail, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_IN_MUNITION_CODE);
        }
        return SUCCESS;
    }

    /**
     * 物资出库详情详情
     */
    @RequestMapping("/detail/{munitionOutDetailId}")
    @ResponseBody
    public MunitionOutDetailEntity detail(@PathVariable String munitionOutDetailId) {
        return munitionOutDetailService.getById(munitionOutDetailId);
    }

}

