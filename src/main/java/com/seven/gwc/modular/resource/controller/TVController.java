package com.seven.gwc.modular.resource.controller;

import com.seven.gwc.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tv")
public class TVController extends BaseController {

    private static String PREFIX = "/modular/resource/";

    /**
     * 跳转到电影信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tv";
    }

    /**
     * 跳转到电视剧详情信息
     */
    @RequestMapping("/tv_detail")
    public String tvDetail(String id) {
        return PREFIX + "tv_detail";
    }
}
