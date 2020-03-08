package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.dto.DecisionSafeDTO;
import com.seven.gwc.modular.lawrecord.enums.PlotSeverityEnum;
import com.seven.gwc.modular.lawrecord.enums.PunishmentTypeEnum;
import com.seven.gwc.modular.lawrecord.service.DecisionSafeService;
import com.seven.gwc.modular.lawrecord.service.DecisionService;
import com.seven.gwc.modular.lawrecord.vo.DecisionSafeVO;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description : 决定控制器
 *
 * @author : ZZL
 * @date : 2020-03-07
 */
@Controller
@RequestMapping("lawRecord/decision_safe")
public class DecisionSafeController extends BaseController {

    @Autowired
    private DecisionSafeService decisionSafeService;


    /**
     * 创建 & 修改
     * @param decisionSafeVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(DecisionSafeVO decisionSafeVO){
        decisionSafeVO.setUserId(ShiroKit.getUser().getId());
        return  decisionSafeService.updateDecisionSafe(decisionSafeVO);

    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public DecisionSafeDTO detail(String id){
        return decisionSafeService.detail(id);
    }





}

