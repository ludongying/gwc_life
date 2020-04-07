package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.dto.ReasonDTO;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.ProduceReasonEnum;
import com.seven.gwc.modular.lawrecord.enums.SafeReasonEnum;
import com.seven.gwc.modular.lawrecord.service.ReasonService;
import com.seven.gwc.modular.lawrecord.vo.ReasonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zzl
 * @Date: 2020-03-05
 * @description :
 */
@Controller
@RequestMapping("lawRecord/reason")
public class ReasonController {

    private static String PREFIX = "/modular/lawRecord/";

    @Autowired
    private ReasonService reasonService;
    /**
     * 案由
     * @return
     */
    @RequestMapping("")
    public String reason(Integer lawType,String id, Model model) {
        model.addAttribute("lawType", lawType);
        model.addAttribute("id", id);
        if(lawType.equals(LawTypeEnum.PRODUCE.getCode())){
            model.addAttribute("reasons", ProduceReasonEnum.values());
        }else if(lawType.equals(LawTypeEnum.SAFE.getCode())){
            model.addAttribute("reasons", SafeReasonEnum.values());
        }
        return PREFIX + "reason_"+LawTypeEnum.findByCode(lawType).toString().toLowerCase();
    }


    /**
     * 创建 & 修改
     * @param reasonVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(ReasonVO reasonVO){
        reasonVO.setUserId(ShiroKit.getUser().getId());
        return  reasonService.updateReason(reasonVO);

    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public ReasonDTO detail(String id){
        return reasonService.detail(id);
    }


}
