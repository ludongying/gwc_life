package com.seven.gwc.modular.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 全局的控制器
 * @author : GD
 * @date : 2019-11-28
 */
@Controller
@RequestMapping("/global")
public class GlobalController {

    /**
     * 跳转到404页面
     */
    @RequestMapping(path = "/error")
    public String errorPage() {
        return "/404";
        //return "/map";
    }

    /**
     * 跳转到session超时页面
     */
    @RequestMapping(path = "/sessionError")
    public String errorPageInfo(Model model) {
        model.addAttribute("tips", "session超时");
        return "/login";
        //return "/map";
    }

//    /**
//     * 跳转到map页面
//     */
//    @RequestMapping(path = "/mymap")
//    public String mapPage() {
//        return "/map";
//    }
}
