package com.seven.gwc.modular.lawrecord.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.service.ElectronicFileService;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : shq
 * @Date: 2020-03-18
 * @description : 电子档案
 */
@Controller
@RequestMapping("electronicFile")
public class ElectronicFileController {
    private static String PREFIX = "/modular/electronicFile";
    @Autowired
    private ElectronicFileService electronicFileService;

    /**
     * 跳转到电子档案首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "electronicFile";
    }

    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<FishShipEntity> list(LawRecordVO lawRecordVO) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LawRecordDTO> list = electronicFileService.getElectronicFileList(lawRecordVO);
        PageInfo pageInfo = new PageInfo<>(list);
        return new BaseResultPage().createPage(pageInfo);
    }
}
