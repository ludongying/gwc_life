package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.DictTypeEntity;
import com.seven.gwc.modular.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * description : 字典类型控制器
 *
 * @author : LM
 * @date : 2019-10-10
 */
@Controller
@RequestMapping("dictType")
public class DictTypeController extends BaseController {

    private static String PREFIX = "/modular/system/dictType/";

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 跳转到字典类型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dictType";
    }

    /**
     * 跳转到添加字典类型
     */
    @RequestMapping("/dictType_add")
    public String dictTypeAdd() {
        return PREFIX + "dictType_add";
    }

    /**
     * 跳转到修改字典类型
     */
    @RequestMapping("/dictType_edit")
    public String dictTypeUpdate(String dictTypeId) {
        return PREFIX + "dictType_edit";
    }

    /**
     * 跳转到查看字典类型
     */
    @RequestMapping("/dictType_detail")
    public String dictTypeDetail(String dictTypeId) {
        return PREFIX + "dictType_detail";
    }

    /**
     * 获取字典类型列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<DictTypeEntity> list(String sysDictTypeName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<DictTypeEntity> sysDictTypes = dictTypeService.selectSysDictType(sysDictTypeName);
        PageInfo pageInfo = new PageInfo<>(sysDictTypes);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加字典类型
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(DictTypeEntity dictType) {
        ShiroUser user = ShiroKit.getUser();
        if (dictType.getSort() == null) {
            dictType.setSort(0);
        }
        dictType.setCreateTime(new Date());
        dictType.setCreateUser(user.getName());
        dictTypeService.save(dictType);
        return SUCCESS;
    }

    /**
     * 删除字典类型
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String sysDictTypeId) {
        dictTypeService.removeById(sysDictTypeId);
        return SUCCESS;
    }

    /**
     * 编辑字典类型
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(DictTypeEntity dictType) {
        ShiroUser user = ShiroKit.getUser();
        if (dictType.getSort() == null) {
            dictType.setSort(0);
        }
        dictType.setUpdateUser(user.getName());
        dictType.setUpdateTime(new Date());
        dictTypeService.updateById(dictType);
        return SUCCESS;
    }

    /**
     * 字典类型详情
     */
    @RequestMapping("/detail/{sysDictTypeId}")
    @ResponseBody
    public DictTypeEntity detail(@PathVariable String sysDictTypeId) {
        return dictTypeService.getById(sysDictTypeId);
    }
}

