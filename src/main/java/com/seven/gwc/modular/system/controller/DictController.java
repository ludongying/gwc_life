package com.seven.gwc.modular.system.controller;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.entity.DictTypeEntity;
import com.seven.gwc.modular.system.service.DictService;
import com.seven.gwc.modular.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * description : 字典控制器
 *
 * @author : LM
 * @date : 2019-10-10
 */

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    @Autowired
    private DictTypeService dictTypeService;
    @Autowired
    private DictService dictService;

    private String PREFIX = "/modular/system/dict/";

    @RequestMapping("")
    public String index(@RequestParam("id") Long id, Model model) {
        model.addAttribute("dictTypeId", id);
        return PREFIX + "dict";
    }

    /**
     * 跳转到添加字典类型
     */
    @RequestMapping("/dict_add")
    public String dictAdd(@RequestParam("dictTypeId") Long dictTypeId, Model model) {
        model.addAttribute("dictTypeId", dictTypeId);
        //获取type的名称
        DictTypeEntity dictType = dictTypeService.getById(dictTypeId);
        model.addAttribute("dictTypeName", dictType.getName());
        return PREFIX + "dict_add";
    }

    /**
     * 跳转到添加字典类型
     */
    @RequestMapping("/dict_edit")
    public String dictEdit(@RequestParam("dictId") Long dictId, Model model) {
        //获取type的id
        DictEntity dict = dictService.getById(dictId);
        //获取type的名称
        DictTypeEntity dictType = dictTypeService.getById(dict.getDictTypeId());
        model.addAttribute("dictTypeId", dict.getDictTypeId());
        model.addAttribute("dictTypeName", dictType.getName());
        return PREFIX + "dict_edit";
    }


    @RequestMapping("/list")
    @ResponseBody
    public Object list(String menuName, Long dictTypeId) {
        List<Map<String, Object>> menus = this.dictService.selectDictTree(menuName, dictTypeId);
        return new BaseResultPage().treeData(menus);
    }

    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(DictEntity dict) {
        ShiroUser user = ShiroKit.getUser();

        if (dict.getSort() == null) {
            dict.setSort(0);
        }
        dict.setCreateUser(user.getName());
        this.dictService.add(dict);
        return SUCCESS;
    }

    /**
     * 查看详情接口
     */
    @RequestMapping("/detail/{dictId}")
    @ResponseBody
    public DictEntity detail(@PathVariable Long dictId) {
        return this.dictService.dictDetail(dictId);
    }

    /**
     * 修改字典
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(DictEntity dict) {
        if (dict.getSort() == null) {
            dict.setSort(0);
        }
        dictService.update(dict);
        return SUCCESS;
    }

    /**
     * 删除字典类型
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long dictTypeId) {
        dictService.removeById(dictTypeId);
        return SUCCESS;
    }

    /**
     * 获取某个类型下字典树的列表，ztree格式
     */
    @RequestMapping(value = "/ztree")
    @ResponseBody
    public List<ZTreeNode> ztree(@RequestParam("dictTypeId") Long dictTypeId, @RequestParam(value = "dictId", required = false) Long dictId) {
        return this.dictService.dictTreeList(dictTypeId, dictId);
    }

}
