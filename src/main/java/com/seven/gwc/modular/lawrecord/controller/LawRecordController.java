package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResultPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.ArrayList;
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
        return PREFIX + "lawRecord";
    }



    /**
     * 获取执法记录列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<LawRecordEntity> list(String lawRecordName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LawRecordEntity> lawRecords = new ArrayList<>();
        PageInfo pageInfo = new PageInfo<>(lawRecords);
        return new BaseResultPage().createPage(pageInfo);
    }


    @RequestMapping("/states")
    @ResponseBody
    public List<StateData> getStates(){
        return lawRecordService.getStates();
    }




}

