package com.seven.gwc.modular.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("map")
public class MapController{
    @RequestMapping("")
    public String index(){
        return "/map";
    }
}
