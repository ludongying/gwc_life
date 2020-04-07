package com.seven.gwc.modular.lawrecord.controller;


import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.dto.InquireDTO;
import com.seven.gwc.modular.lawrecord.enums.*;
import com.seven.gwc.modular.lawrecord.service.InquireService;
import com.seven.gwc.modular.lawrecord.vo.InquireVO;
import com.seven.gwc.modular.sailor.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zzl
 * @Date: 2020-02-29
 * @description : 询问控制器
 */
@Controller
@RequestMapping("lawRecord/inquire")
@Slf4j
public class  InquireController extends BaseController {

    @Autowired
    private InquireService inquireService;

    @Autowired
    private PersonService personService;

    private static String PREFIX = "/modular/lawRecord/";

    /**
     * 询问
     */
    @RequestMapping("")
    public String inquire(Integer lawType,String id, Model model) {
        model.addAttribute("lawType", lawType);
        model.addAttribute("id", id);
        if(lawType.equals(LawTypeEnum.PRODUCE.getCode())){
            model.addAttribute("powerUnit",PowerUnitEnum.values());
            //实际作业类型
            model.addAttribute("shipRealType", ShipRealTypeEnum.values());
            //核定作业类型
            model.addAttribute("shipRatedType", ShipRatedTypeEnum.values());
        }else if(lawType.equals(LawTypeEnum.SAFE.getCode())){
            //涂写情况
            model.addAttribute("shipCase", ShipCaseEnum.values());
            //悬挂情况
            model.addAttribute("shipCaseCard", ShipCaseCardEnum.values());
        }
        //询问人职位
        model.addAttribute("investigatePosition", InvestigatePositionEnum.values());
        //渔船状态
        model.addAttribute("shipStatus", ShipStatusEnum.values());
        //设置办案人员
        model.addAttribute("person",personService.listLawPersons());
        return PREFIX + "inquire_"+LawTypeEnum.findByCode(lawType).toString().toLowerCase();
    }

    /**
     * 创建 & 修改
     * @param inquireVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(InquireVO inquireVO){
        inquireVO.setUserId(ShiroKit.getUser().getId());
        return  inquireService.updateInquire(inquireVO);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public InquireDTO detail(String id){
        return inquireService.detail(id);
    }



}
