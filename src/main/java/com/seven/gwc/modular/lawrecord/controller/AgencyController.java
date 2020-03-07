package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.enums.LawCaseSourceEnum;
import com.seven.gwc.modular.lawrecord.service.AgencyService;
import com.seven.gwc.modular.lawrecord.vo.AgencyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description : 办案控制器
 */
@Controller
@RequestMapping("lawRecord/agency")
public class AgencyController extends BaseController {

    private static String PREFIX = "/modular/lawRecord/";

    @Autowired
    private AgencyService agencyService;
    /**
     * 办案机关
     */
    @RequestMapping("")
    public String agency(String lawType,String id, Model model) {
        model.addAttribute("lawType", lawType);
        model.addAttribute("id", id);
        //案件来源
        model.addAttribute("lawCaseSource", LawCaseSourceEnum.values());
        return PREFIX + "agency";
    }

    /**
     * 创建 & 修改
     * @param agencyVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(AgencyVO agencyVO){
        agencyVO.setUserId(ShiroKit.getUser().getId());
        return agencyService.updateAgency(agencyVO);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public AgencyDTO detail(String id){
        return  agencyService.detail(id);
    }


}
