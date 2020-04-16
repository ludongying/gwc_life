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
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.munitionEnum.MunitionInStatesEnum;
import com.seven.gwc.modular.munition.service.MunitionInService;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
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
 * description : 物资入库控制器
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Controller
@RequestMapping("munitionIn")
public class MunitionInController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/munition/munitionIn/";

    @Autowired
    private MunitionInService munitionInService;
    @Autowired
    private PersonService personService;

    /**
     * 跳转到物资入库首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "munitionIn";
    }

    /**
     * 跳转到添加物资入库
     */
    @RequestMapping("/munitionIn_add")
    public String munitionInAdd() {
        return PREFIX + "munitionIn_add";
    }

    /**
     * 跳转到修改物资入库
     */
    @RequestMapping("/munitionIn_edit")
    public String munitionInUpdate(String munitionInId) {
        return PREFIX + "munitionIn_edit";
    }

    /**
     * 跳转到查看物资入库
     */
    @RequestMapping("/munitionIn_detail")
    public String munitionInDetail(String munitionInId) {
        return PREFIX + "munitionIn_detail";
    }

    /**
     * 获取物资入库列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<MunitionInEntity> list(MunitionInEntity munitionIn) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MunitionInEntity> munitionIns = munitionInService.selectMunitionIn(munitionIn,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(munitionIns);
        Integer size = munitionInService.getListSize(munitionIn);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加物资入库
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(MunitionInEntity munitionIn) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionInService.addMunitionIn(munitionIn, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_IN_CODE);
        }
        return SUCCESS;
    }

    /**
     * 删除物资入库
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String munitionInId) {
        ShiroUser user = ShiroKit.getUser();
        munitionInService.deleteMunitionIn(munitionInId, user);
        return SUCCESS;
    }

    /**
     * 编辑物资入库
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(MunitionInEntity munitionIn) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionInService.editMunitionIn(munitionIn, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_IN_CODE);
        }
        return SUCCESS;
    }

    /**
     * 物资入库详情
     */
    @RequestMapping("/detail/{munitionInId}")
    @ResponseBody
    public MunitionInEntity detail(@PathVariable String munitionInId) {
        return munitionInService.getById(munitionInId);
    }

    /**
     * 物资入库表单自动生成
     */
    @RequestMapping("/getInitInfo")
    @ResponseBody
    public MunitionInEntity getInitInfo() {
        MunitionInEntity munitionIn = new MunitionInEntity();
        munitionIn.setCode(munitionInService.getAutoCode());
        ShiroUser user = ShiroKit.getUser();
        PersonEntity personEntity = personService.getOneByPersonId(user.getId());
        munitionIn.setApplyPerson(personEntity.getId());
        munitionIn.setApplyTime(new Date());
        return munitionIn;
    }

    /**
     * 入库表单提交
     */
    @RequestMapping("/submit")
    @ResponseBody
    public BaseResult submit(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
       munitionInService.setStatus(id, MunitionInStatesEnum.SUBMIT.getCode(),user);
       return SUCCESS;
    }

    /**
     * 入库通过
     */
    @RequestMapping("/inApprove")
    @ResponseBody
    public BaseResult inApprove(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionInService.setStatus(id, MunitionInStatesEnum.MUNITION_IN_OK.getCode(),user);
        return SUCCESS;
    }

    /**
     * 入库驳回
     */
    @RequestMapping("/inRefused")
    @ResponseBody
    public BaseResult inRefused(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionInService.setStatus(id, MunitionInStatesEnum.MUNITION_IN_REFUSED.getCode(),user);
        return SUCCESS;
    }

    /**
     * 入库表单审核通过
     */
    @RequestMapping("/approve")
    @ResponseBody
    public BaseResult approve(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionInService.setStatus(id, MunitionInStatesEnum.APPROVE.getCode(),user);
        return SUCCESS;
    }

    /**
     * 入库表单审核驳回
     */
    @RequestMapping("/refused")
    @ResponseBody
    public BaseResult refused(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        munitionInService.setStatus(id, MunitionInStatesEnum.REFUSED.getCode(),user);
        return SUCCESS;
    }


}

