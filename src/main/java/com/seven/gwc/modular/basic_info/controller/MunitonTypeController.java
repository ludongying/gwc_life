package com.seven.gwc.modular.basic_info.controller;

import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.state.TypeStatesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.basic_info.entity.MunitonTypeEntity;
import com.seven.gwc.modular.basic_info.service.MunitonTypeService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 物料类型控制器
 *
 * @author : LDY
 * @date : 2020-01-02
 */
@Controller
@RequestMapping("munitonType")
public class MunitonTypeController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String PREFIX = "/modular/basic_info/munitonType/";

    @Autowired
    private MunitonTypeService munitonTypeService;

    /**
     * 跳转到物料类型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "munitonType";
    }

    /**
     * 跳转到添加物料类型
     */
    @RequestMapping("/munitonType_add")
    public String munitonTypeAdd() {
        return PREFIX + "munitonType_add";
    }

    /**
     * 跳转到修改物料类型
     */
    @RequestMapping("/munitonType_edit")
    public String munitonTypeUpdate(Long munitonTypeId) {
        return PREFIX + "munitonType_edit";
    }

    /**
     * 跳转到查看物料类型
     */
    @RequestMapping("/munitonType_detail")
    public String munitonTypeDetail(Long munitonTypeId) {
        return PREFIX + "munitonType_detail";
    }

    /**
     * 获取物料类型列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<MunitonTypeEntity> list(String munitonTypeName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<MunitonTypeEntity> munitonTypes = munitonTypeService.selectMunitonType(munitonTypeName);
        PageInfo pageInfo = new PageInfo<>(munitonTypes);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 新增物料类型
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(MunitonTypeEntity munitonType) {
        if(!munitonTypeService.add(munitonType)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_MUNITION_TYPE);
        }
        return SUCCESS;
    }

    /**
     * 删除物料类型
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long munitonTypeId) {
        munitonTypeService.removeById(munitonTypeId);
        return SUCCESS;
    }

    /**
     * 修改物料类型
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(MunitonTypeEntity munitonType) {
        //ShiroUser user = ShiroKit.getUser();
        if (!munitonTypeService.edit(munitonType)) {
            return new BaseResult().failure(ErrorEnum.ERROR_NO_MUNITION_TYPE);
        }
        //munitonTypeService.updateById(munitonType);
        return SUCCESS;
    }

    /**
     * 物料类型详情
     */
    @RequestMapping("/detail/{munitonTypeId}")
    @ResponseBody
    public MunitonTypeEntity detail(@PathVariable Long munitonTypeId) {
        return munitonTypeService.getById(munitonTypeId);
    }

    /**
     * 启用
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public BaseResult unfreeze(@RequestParam Long id){
        this.munitonTypeService.setStatus(id,TypeStatesEnum.OK.getCode());
        return SUCCESS;
    }

    /**
     * 禁用
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public BaseResult freeze(@RequestParam Long id){
        this.munitonTypeService.setStatus(id,TypeStatesEnum.PROHIBIT.getCode());
        return SUCCESS;
    }


}

