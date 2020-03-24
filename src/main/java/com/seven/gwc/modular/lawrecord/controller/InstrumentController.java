package com.seven.gwc.modular.lawrecord.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : zzl
 * @Date: 2020-03-23
 * @description : 文书控制器
 */
@Controller
@RequestMapping("lawRecord/instrument")
public class InstrumentController {

    private static String PREFIX = "/modular/lawRecord/";
    /**
     * 跳转到文书
     */
    @RequestMapping("")
    public String instrument(String id) {

        return PREFIX + "instrument";
    }

}
