package com.seven.gwc.modular.system.controller;

import com.seven.gwc.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("demo")
public class DemoController extends BaseController {

    private static String PREFIX = "/modular/system/demo/";

    /**
     * 跳转到部门首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "demo";
    }
}
