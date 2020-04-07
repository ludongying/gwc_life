package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.service.ElectronicFileService;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("electronic")
public class ElectronicController {
    private static String PREFIX = "/modular/electronicFile/";

    @Autowired
    private ElectronicFileService electronicFileService;
    @Autowired
    private LawRecordService lawRecordService;

    @RequestMapping("")
    public String index( Model model) {
        model.addAttribute("lawTypes", LawTypeEnum.values());
        return PREFIX + "electronicFile";
    }

    @RequestMapping("/detail")
    public String detail(String id, Model model) {
        lawRecordService.detail(id,model);
        return PREFIX + "electronicFile_detail";
    }

    /**
     * 获取执法记录列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<LawRecordDTO> list(LawRecordVO lawRecordVO) {
        return lawRecordService.listLawRecord(lawRecordVO);
    }

}
