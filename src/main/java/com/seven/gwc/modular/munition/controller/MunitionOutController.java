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
import com.seven.gwc.modular.munition.entity.MunitionOutEntity;
import com.seven.gwc.modular.munition.munitionEnum.MunitionInOutStatesEnum;
import com.seven.gwc.modular.munition.service.MunitionOutService;
import com.seven.gwc.modular.sailor.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * description : 物资出库控制器
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Controller
@RequestMapping("munitionOut")
public class MunitionOutController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/munition/munitionOut/";

    @Autowired
    private MunitionOutService munitionOutService;
    @Autowired
    private PersonService personService;

    /**
     * 跳转到物资出库首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "munitionOut";
    }

    /**
     * 跳转到添加物资出库
     */
    @RequestMapping("/munitionOut_add")
    public String munitionOutAdd() {
        return PREFIX + "munitionOut_add";
    }

    /**
     * 跳转到修改物资出库
     */
    @RequestMapping("/munitionOut_edit")
    public String munitionOutUpdate(String munitionOutId) {
        return PREFIX + "munitionOut_edit";
    }

    /**
     * 跳转到查看物资出库
     */
    @RequestMapping("/munitionOut_detail")
    public String munitionOutDetail(String munitionOutId) {
        return PREFIX + "munitionOut_detail";
    }

    /**
     * 获取物资出库列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<MunitionOutEntity> list(MunitionOutEntity munitionOut) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MunitionOutEntity> munitionOuts = munitionOutService.selectMunitionOut(munitionOut,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(munitionOuts);
        Integer size = munitionOutService.getListSize(munitionOut);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加物资出库
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(MunitionOutEntity munitionOut) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionOutService.addMunitionOut(munitionOut, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_IN_CODE);
        }
        return SUCCESS;
    }

    /**
     * 删除物资出库
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String munitionOutId) {
        ShiroUser user = ShiroKit.getUser();
        munitionOutService.deleteMunitionOut(munitionOutId, user);
        return SUCCESS;
    }

    /**
     * 编辑物资出库
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(MunitionOutEntity munitionOut) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionOutService.editMunitionOut(munitionOut, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_IN_CODE);
        }
        return SUCCESS;
    }

    /**
     * 物资出库详情
     */
    @RequestMapping("/detail/{munitionOutId}")
    @ResponseBody
    public MunitionOutEntity detail(@PathVariable String munitionOutId) {
        return munitionOutService.getMunitionOutDetail(munitionOutId);
    }

    /**
     * 物资出库表单自动生成
     */
    @RequestMapping("/getInitInfo")
    @ResponseBody
    public MunitionOutEntity getInitInfo() {
        MunitionOutEntity munitionOut = new MunitionOutEntity();
        munitionOut.setCode(munitionOutService.getAutoCode());
        ShiroUser user = ShiroKit.getUser();
//        PersonEntity personEntity = personService.getOneByPersonId(user.getId());
        munitionOut.setApplyPerson(user.getId());
        munitionOut.setApplyTime(new Date());
        return munitionOut;
    }

    /**
     * 出库表单提交
     */
    @RequestMapping("/submit")
    @ResponseBody
    public BaseResult submit(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
       munitionOutService.setStatus(id, MunitionInOutStatesEnum.SUBMIT.getCode(),user);
       return SUCCESS;
    }

    /**
     * 出库通过
     */
    @RequestMapping("/outApprove")
    @ResponseBody
    public BaseResult outApprove(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionOutService.setStatus(id, MunitionInOutStatesEnum.MUNITION_IN_OUT_OK.getCode(),user);
        return SUCCESS;
    }

    /**
     * 出库驳回
     */
    @RequestMapping("/outRefused")
    @ResponseBody
    public BaseResult outRefused(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionOutService.setStatus(id, MunitionInOutStatesEnum.MUNITION_IN_OUT_REFUSED.getCode(),user);
        return SUCCESS;
    }

    /**
     * 出库表单审核通过
     */
    @RequestMapping("/approve")
    @ResponseBody
    public BaseResult approve(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionOutService.setStatus(id, MunitionInOutStatesEnum.APPROVE.getCode(),user);
        return SUCCESS;
    }

    /**
     * 出库表单审核驳回
     */
    @RequestMapping("/refused")
    @ResponseBody
    public BaseResult refused(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionOutService.setStatus(id, MunitionInOutStatesEnum.REFUSED.getCode(),user);
        return SUCCESS;
    }


}

