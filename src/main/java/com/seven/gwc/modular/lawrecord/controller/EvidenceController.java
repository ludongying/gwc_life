package com.seven.gwc.modular.lawrecord.controller;


import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.service.EvidenceService;
import com.seven.gwc.modular.lawrecord.vo.LawEvidenceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * description : 证据控制器
 *
 * @author : ZZL
 * @date : 2020-03-03
 */
@Controller
@RequestMapping("lawRecord/evidence")
@Slf4j
public class EvidenceController extends BaseController {

    private static String PREFIX = "/modular/lawRecord/";

    @Autowired
    private EvidenceService evidenceService;

    /**
     * 证据
     * @return
     */
    @RequestMapping("")
    public String evidence(Integer lawType,String id, Model model) {
        model.addAttribute("lawType", lawType);
        model.addAttribute("id", id);
        return PREFIX + "evidence";
    }

    /**
     * 创建 & 修改
     * @param lawEvidenceVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(LawEvidenceVO lawEvidenceVO){
        lawEvidenceVO.setUserId(ShiroKit.getUser().getId());
        return evidenceService.updateEvidence(lawEvidenceVO);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public BaseResult detail(String id){
        return evidenceService.detail(id);
    }



}

