package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.InquireSafeEntity;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.PlotSeverityEnum;
import com.seven.gwc.modular.lawrecord.enums.PunishmentTypeEnum;
import com.seven.gwc.modular.lawrecord.service.DecisionSafeService;
import com.seven.gwc.modular.lawrecord.service.DecisionService;
import com.seven.gwc.modular.lawrecord.service.InquireSafeService;
import com.seven.gwc.modular.lawrecord.service.InquireService;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * description : 决定控制器
 *
 * @author : ZZL
 * @date : 2020-03-06
 */
@Controller
@RequestMapping("lawRecord/decision")
@Slf4j
public class DecisionController extends BaseController {

    private static String PREFIX = "/modular/lawRecord/";

    @Autowired
    private DecisionService decisionService;
    @Autowired
    private DecisionSafeService decisionSafeService;
    @Autowired
    private InquireService inquireService;
    @Autowired
    private InquireSafeService inquireSafeService;

    /**
     * 跳转到决定首页
     */
    @RequestMapping("")
    public String decision(Integer lawType,String id, Model model) {
        model.addAttribute("lawType", lawType);
        model.addAttribute("id", id);
        //处罚人类型
        model.addAttribute("punishmentType", PunishmentTypeEnum.values());
        //情节严重性
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            model.addAttribute("plotSeverity", decisionService.listSeverity(id));
            if(Objects.isNull(decisionService.getById(id))){
                model.addAttribute("inquire", inquireService.getById(id));
            }else{
                model.addAttribute("inquire", new InquireEntity());
            }
        }else{
            model.addAttribute("plotSeverity", PlotSeverityEnum.values());
            if(Objects.isNull(decisionSafeService.getById(id))){
                model.addAttribute("inquire", inquireSafeService.getById(id));
            }else{
                model.addAttribute("inquire", new InquireSafeEntity());
            }
        }
        //渔船状态
        model.addAttribute("status",decisionService.shipStatusIsEscape(id,lawType));
        return PREFIX + "decision_"+LawTypeEnum.findByCode(lawType).toString().toLowerCase();
    }

    /**
     * 创建 & 修改
     * @param decisionVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(DecisionVO decisionVO){
        decisionVO.setUserId(ShiroKit.getUser().getId());
        return  decisionService.updateDecision(decisionVO);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public DecisionDTO detail(String id){
        return decisionService.detail(id);
    }




}

