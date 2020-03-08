package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;
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
    public String index() {
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





}

