package com.seven.gwc.modular.lawrecord.controller;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.modular.lawrecord.dto.InquireSafeDTO;
import com.seven.gwc.modular.lawrecord.service.InquireSafeService;
import com.seven.gwc.modular.lawrecord.vo.InquireSafeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zzl
 * @Date: 2020-02-29
 * @description : 询问控制器
 */
@Controller
@RequestMapping("lawRecord/inquire_safe")
@Slf4j
public class InquireSafeController extends BaseController {

    @Autowired
    private InquireSafeService inquireSafeService;

    /**
     * 创建 & 修改
     * @param inquireSafeVO
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public BaseResult update(InquireSafeVO inquireSafeVO){
        inquireSafeVO.setUserId(ShiroKit.getUser().getId());
        return  inquireSafeService.updateInquireSafe(inquireSafeVO);

    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public InquireSafeDTO detail(String id){
        return inquireSafeService.detail(id);
    }



}
