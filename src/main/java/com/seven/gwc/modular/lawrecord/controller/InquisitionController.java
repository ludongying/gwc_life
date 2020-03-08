package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.dto.InquisitionDTO;
import com.seven.gwc.modular.lawrecord.enums.ShipCaseCardEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipCaseEnum;
import com.seven.gwc.modular.lawrecord.service.InquisitionService;
import com.seven.gwc.modular.lawrecord.vo.InquisitionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description : 勘验笔录控制器
 *
 * @author : ZZL
 * @date : 2020-03-02
 */
@Controller
@RequestMapping("lawRecord/inquisition")
@Slf4j
public class InquisitionController extends BaseController {

    private static String PREFIX = "/modular/lawRecord/";

    @Autowired
    private InquisitionService inquisitionService;

    /**
     * 勘验
     */
    @RequestMapping("")
    public String inquisition(Integer lawType,String id, Model model) {
        model.addAttribute("lawType", lawType);
        model.addAttribute("id", id);
        //涂写情况
        model.addAttribute("shipCase", ShipCaseEnum.values());
        //悬挂情况
        model.addAttribute("shipCaseCard", ShipCaseCardEnum.values());
        return PREFIX + "inquisition";
    }

    /**
     * 创建 & 修改
     * @param inquisitionVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(InquisitionVO inquisitionVO){
        inquisitionVO.setUserId(ShiroKit.getUser().getId());
        return  inquisitionService.updateInquisition(inquisitionVO);

    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public InquisitionDTO detail(String id){
        return inquisitionService.detail(id);
    }


}

