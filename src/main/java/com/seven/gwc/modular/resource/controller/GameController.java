package com.seven.gwc.modular.resource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("game")
public class GameController {

    private static String PREFIX = "/modular/resource/";

    /**
     * 跳转到电影信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "game";
    }
}
