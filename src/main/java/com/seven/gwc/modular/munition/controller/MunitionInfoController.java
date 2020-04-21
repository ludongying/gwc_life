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
import com.seven.gwc.modular.munition.entity.MunitionInfoEntity;
import com.seven.gwc.modular.munition.entity.MunitionStoreEntity;
import com.seven.gwc.modular.munition.service.MunitionInfoService;
import com.seven.gwc.modular.munition.service.MunitionStoreService;
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
 * description : 物资信息控制器
 *
 * @author : LDY
 * @date : 2020-04-03
 */
@Controller
@RequestMapping("munitionInfo")
public class MunitionInfoController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/munition/munitionInfo/";

    @Autowired
    private MunitionInfoService munitionInfoService;
    @Autowired
    private MunitionStoreService munitionStoreService;

    /**
     * 跳转到物资信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "munitionInfo";
    }

    /**
     * 跳转到添加物资信息
     */
    @RequestMapping("/munitionInfo_add")
    public String munitionInfoAdd() {
        return PREFIX + "munitionInfo_add";
    }

    /**
     * 跳转到修改物资信息
     */
    @RequestMapping("/munitionInfo_edit")
    public String munitionInfoUpdate(String munitionInfoId) {
        return PREFIX + "munitionInfo_edit";
    }

    /**
     * 跳转到查看物资信息
     */
    @RequestMapping("/munitionInfo_detail")
    public String munitionInfoDetail(String munitionInfoId) {
        return PREFIX + "munitionInfo_warnSet";
    }

    /**
     * 获取物资信息列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<MunitionInfoEntity> list(MunitionInfoEntity munition) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MunitionInfoEntity> munitionInfos = munitionInfoService.selectMunitionInfo(munition,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(munitionInfos);
        Integer size = munitionInfoService.getListSize(munition);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加物资信息
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(MunitionInfoEntity munitionInfo) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionInfoService.addMunitionInfo(munitionInfo, user)){
            return new BaseResultPage().failure(ErrorEnum.ERROR_ONLY_MUNITION_CODE);
        }
        //添加库存表记录
        MunitionStoreEntity store = new MunitionStoreEntity();
        store.setMunitionId(munitionInfo.getId());
        store.setTotalNum(0);
        munitionStoreService.addMunitionStore(store,user);
        return SUCCESS;
    }

    /**
     * 删除物资信息
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String munitionInfoId) {
        ShiroUser user = ShiroKit.getUser();
        if( munitionStoreService.getMunitionStore(munitionInfoId).getTotalNum()!=0){
            return new BaseResultPage().failure(ErrorEnum.ERROR_ONLY_MUNITION_STORE);
        }
        munitionInfoService.deleteMunitionInfo(munitionInfoId, user);
        return SUCCESS;
    }

    /**
     * 编辑物资信息
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(MunitionInfoEntity munitionInfo) {
        ShiroUser user = ShiroKit.getUser();
        if(!munitionInfoService.editMunitionInfo(munitionInfo, user)){
            return new BaseResultPage().failure(ErrorEnum.ERROR_ONLY_MUNITION_CODE);
        }
        return SUCCESS;
    }

    /**
     * 物资信息详情
     */
    @RequestMapping("/detail/{munitionInfoId}")
    @ResponseBody
    public MunitionInfoEntity detail(@PathVariable String munitionInfoId) {
        return munitionInfoService.getById(munitionInfoId);
    }

    /**
     * 通过物资类型获取物资信息列表
     */
    @RequestMapping("/listByType")
    @ResponseBody
    public List<MunitionInfoEntity> getMunitionListByType(String type) {
        return munitionInfoService.getMunitionListByType(type);
    }

    /**
     * 通过物资名称获取物资信息列表
     */
    @RequestMapping("/listByName")
    @ResponseBody
    public List<MunitionInfoEntity> getMunitionListByName(String name) {
        return munitionInfoService.getMunitionListByName(name);
    }

}

