package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.entity.EnclosureEntity;
import com.seven.gwc.modular.system.service.EnclosureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * description : 附件控制器
 *
 * @author : GD
 * @date : 2019-09-29
 */
@Controller
@RequestMapping("enclosure")
public class EnclosureController extends BaseController {

    private String PREFIX = "/modular/system/enclosure/";

    @Autowired
    private EnclosureService enclosureService;

    /**
     * 跳转到附件首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "enclosure";
    }

    /**
     * 跳转到添加附件
     */
    @RequestMapping("/enclosure_add")
    public String enclosureAdd() {
        return PREFIX + "enclosure_add";
    }

    /**
     * 跳转到修改附件
     */
    @RequestMapping("/enclosure_edit")
    public String enclosureUpdate(Long enclosureId) {
        return PREFIX + "enclosure_edit";
    }

    /**
     * 跳转到查看附件
     */
    @RequestMapping("/enclosure_detail")
    public String enclosureDetail(Long enclosureId) {
        return PREFIX + "enclosure_detail";
    }

    /**
     * 获取附件列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<EnclosureEntity> list(String enclosureName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<EnclosureEntity> enclosures = enclosureService.selectEnclosure(enclosureName);
        PageInfo pageInfo = new PageInfo<>(enclosures);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 新增附件
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(String fileList, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShiroUser user = ShiroKit.getUser();
        enclosureService.addEnclosureEntity(user, fileList.substring(0, fileList.length() - 1), null, null, request, response);
        return SUCCESS;
    }

    /**
     * 删除附件
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long enclosureId) {
        enclosureService.removeById(enclosureId);
        return SUCCESS;
    }

    /**
     * 修改附件
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(EnclosureEntity enclosure) {
        ShiroUser user = ShiroKit.getUser();
        enclosureService.updateById(enclosure);
        return SUCCESS;
    }

    /**
     * 附件详情
     */
    @RequestMapping("/detail/{enclosureId}")
    @ResponseBody
    public EnclosureEntity detail(@PathVariable Long enclosureId) {
        return enclosureService.getById(enclosureId);
    }


    /**
     * 文档预览PDF
     */
    @RequestMapping(value = "enclosurePreview")
    public String enclosurePreview(String file, Model model) {
        String filename = ToolUtil.getFileNameNoUUID(file);
        String suffix = ToolUtil.getFileSuffix(file);
        model.addAttribute("fileName", filename);
        if (SysConsts.STR_PDT.equals(suffix) || SysConsts.STR_PPT.equals(suffix)) {
            model.addAttribute("file", file);
        } else {
            model.addAttribute("file", ToolUtil.getFileUpSuffix(file, ".pdf"));
        }
        return PREFIX + "enclosure_preview";
    }

}

