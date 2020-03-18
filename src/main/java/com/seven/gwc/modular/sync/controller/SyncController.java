package com.seven.gwc.modular.sync.controller;

import com.seven.gwc.modular.sync.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : zzl
 * @Date: 2020-03-13
 * @description :
 */
@Controller
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    private DataService dataService;

    @RequestMapping("")
    @ResponseBody
    public String sync(){
        dataService.read();
        return "success";
    }

}
