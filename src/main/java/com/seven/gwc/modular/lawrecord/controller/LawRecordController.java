package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.data.instrument.dto.FilePathDTO;
import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description : 执法记录控制器
 *
 * @author : ZZL
 * @date : 2020-02-26
 */
@Controller
@RequestMapping("lawRecord")
public class LawRecordController extends BaseController {


    private static String PREFIX = "/modular/lawRecord/";

    @Autowired
    private LawRecordService lawRecordService;


    /**
     * 跳转到执法记录首页
     */
    @RequestMapping("")
    public String index( Model model) {
        model.addAttribute("lawTypes", LawTypeEnum.values());
        return PREFIX + "record";
    }

    @RequestMapping("/states")
    @ResponseBody
    public List<StateData> getStates(){
        return lawRecordService.getStates();
    }


    /**
     * 获取执法记录列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<LawRecordDTO> list(LawRecordVO lawRecordVO) {
        return lawRecordService.listLawRecord(lawRecordVO);
    }

    /**
     * 执法记录作废
     */
    @RequestMapping("/invalid")
    @ResponseBody
    public BaseResult invalid(String id) {
        return lawRecordService.invalidRecord(id);
    }

    /**
     * 执法记录结案
     */
    @RequestMapping("/finish")
    @ResponseBody
    public BaseResult finish(String id) {
        return lawRecordService.finishRecord(id);
    }

    /**
     * 详情页
     */
    @RequestMapping("/detail")
    public String detail(String id,Model model) {
        lawRecordService.detail(id,model);
        return PREFIX + "detail";
    }

    /**
     * 详情页
     */
    @RequestMapping("/document")
    @ResponseBody
    public BaseResultPage detail(String id) {
        BaseResultPage baseResultPage=new BaseResultPage();
        List<FilePathDTO> instrument = lawRecordService.instrument(id);
        baseResultPage.setData(instrument);
        baseResultPage.setCode(0);
        return baseResultPage;
    }


}

